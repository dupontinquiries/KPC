package media.kitchen.parkour.blocktype;

import media.kitchen.parkour.blocktype.tileentity.ChargableTile;
import media.kitchen.parkour.blocktype.tileentity.ChargableTileTaydon;
import net.minecraft.block.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ChargableBlockSunlight extends BlockBase implements ITileEntityProvider {

    protected int lmin, lmax;

    protected BlockState blockStateIn = Blocks.DIRT.getDefaultState();

    //public static final IntegerProperty CHARGE_COUNT = IntegerProperty.create("CHARGE_COUNT", 0, 100);

    public ChargableBlockSunlight(Properties props) {
        super(props);
    }

    public ChargableBlockSunlight(Properties props, BlockState blockStateIn) {
        this(props);
        this.blockStateIn = blockStateIn;
    }

    public ChargableBlockSunlight(Properties props, BlockState blockStateIn, int lmin, int lmax) {
        this(props, blockStateIn);
        this.lmin = lmin;
        this.lmax = lmax;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        DaylightDetectorBlock block;
        if (lmax == 6) return new ChargableTile(blockStateIn, lmin, lmax);
        else return new ChargableTileTaydon(blockStateIn, lmin, lmax);
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    /*
    @Override
    protected void updateCharge(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        BlockState bs = world.getBlockState(pos);
        int curr = world.getLight(pos), val = bs.get(CHARGE_COUNT);
        if ( val < 100 && curr > lmin && curr < lmax ) {
            world.setBlockState(pos, bs.with(CHARGE_COUNT, val + 1));
        }
        System.out.println(val);
    }
    */

}
