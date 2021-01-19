package media.kitchen.parkour.blocktype.tileentity;

import media.kitchen.parkour.Parkour;
import media.kitchen.parkour.itemtype.parkour.AmbigSoundType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Random;

public class ChargableTile extends TileEntity implements ITickableTileEntity {

    protected int x, y, z, counter, lmin, lmax;
    protected BlockState blockStateIn;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
    public int getLMin() {
        return lmin;
    }
    public int getLMax() {
        return lmax;
    }

    public int getCounter() {
        return counter;
    }

    public ChargableTile() {
        super(Parkour.CHARGABLE_TE.get());
        setup();
    }

    protected void setup() {
        this.blockStateIn = Parkour.CHARGED_RUBY_BLOCK.get().getDefaultState();
        this.lmin = 6;
        this.lmax = 50;
    }

    public ChargableTile(BlockState blockStateIn, int lmin, int lmax) {
        super(Parkour.CHARGABLE_TE.get());
        this.blockStateIn = blockStateIn;
        this.lmin = lmin;
        this.lmax = lmax;
    }

    @Override
    public void tick() {
        this.updateCharge( getBlockState(), world, pos, world.rand );
    }

    protected void updateCharge(BlockState state, World world, BlockPos pos, Random rand) {
        if (world.dimension.hasSkyLight() && ( !world.getBlockState(pos.up()).isSolid() || world.isAirBlock(pos.up()) ) ) {
            int lightValue = world.getLightFor(LightType.SKY, pos.up()) - world.getSkylightSubtracted();
            float f = world.getCelestialAngleRadians(1.0F);
            float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
            f = f + (f1 - f) * 0.2F;
            lightValue = Math.round((float)lightValue * MathHelper.cos(f));

            lightValue = MathHelper.clamp(lightValue, 0, 15);

            lightValue += world.getLightFor(LightType.BLOCK, pos.up()) / 2;
            //boolean solarEffect = ( lightValue > 6 );
            //System.out.println(lightValue);
            if (lmin <= lightValue && lightValue <= lmax) {
                if ( ++counter > 500) { //5500
                    world.setBlockState(pos, blockStateIn);
                    AmbigSoundType sound = new AmbigSoundType(Parkour.BLOCK_CHARGE_SOUND);
                    world.playSound(null, pos, sound.getSound(), SoundCategory.AMBIENT, 0.4F, 1F );
                } else if (counter % 65 == 0) {
                    AmbigSoundType sound = new AmbigSoundType(Parkour.BLOCK_CHARGE_SOUND);
                    world.playSound(null, pos, sound.getSound(), SoundCategory.AMBIENT, 0.4F, 1F );
                }
            }


        }

        markDirty();

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("initvalues", NBTHelper.toNBT(this));
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        CompoundNBT initValues = compound.getCompound("initvalues");
        if (initValues != null) {
            this.x = initValues.getInt("x");
            this.y = initValues.getInt("y");
            this.z = initValues.getInt("z");
            this.lmin = initValues.getInt("lmin");
            this.lmax = initValues.getInt("lmax");
            this.counter = initValues.getInt("counter");
            return;
        }
        postRead();
    }

    private void postRead() {

    }

}
