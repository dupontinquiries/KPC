/*
package media.kitchen.kitchenparkour.entity;

import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.serializers.BlockStateSerializer;
import media.kitchen.kitchenparkour.util.Coord;
import media.kitchen.kitchenparkour.util.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class LavaAOEEntityNBT extends AreaEffectCloudEntity {

    BlockStateSerializer serializer = new BlockStateSerializer();

    protected int radius = 2;

    public LavaAOEEntityNBT(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        //states = new Hashtable<>(); //4 * radius * radius, 0.8F
    }

    @Override
    public void tick() {

        this.setParticleData(ParticleTypes.LAVA);
        super.tick();
        if ( this.ticksExisted == 1 ) {
            this.world.playSound(null, this.getPosition(), Parkour.EARTH_WIELD_WINDUP.get(), SoundCategory.AMBIENT, 3F, 1F);
        }
        if ( this.ticksExisted % 15 == 0 ) {
            this.world.playSound(null, this.getPosition(), Parkour.EARTH_WIELD_WINDUP.get(), SoundCategory.AMBIENT, 0.7F, 1F);
        }
        if ( this.ticksExisted == 20 ) {
            //store blockdata
            Vector<Coord> sphere = Shapes.makeSphereVector(this.world, this.getPosition(), radius, true);
            ArrayList<EntityDataManager.DataEntry<?>> dataEntries = new ArrayList<>();

            for ( Coord c : Shapes.makeSphere(this.world, this.getPosition().down(), radius, true)) {
                System.out.println("writing -> " + world.getBlockState(c.getPosition()).hashCode() + ", " + world.getBlockState(c.getPosition()) + " (" + Block.getStateById(world.getBlockState(c.getPosition()).hashCode()).getBlockState() + ")");
                dataEntries.add(new EntityDataManager.DataEntry<>(new DataParameter<BlockState>((c.getX() * 100) + (c.getY() * 10) + c.getZ(), serializer), world.getBlockState(c.getPosition())));
            }

            this.getDataManager().setEntryValues(dataEntries);


            // create lava
            this.world.playSound(null, this.getPosition(), Parkour.EARTH_WIELD_WINDUP.get(), SoundCategory.AMBIENT, 3F, 1F);
            for ( Coord c : sphere ) {
                BlockState state = this.world.getBlockState(c.getPosition().down());
                if ( ( state.getBlock().getTranslationKey().contains("dirt") || state.getBlock().getTranslationKey().contains("ore")
                        || state.getBlock().getTranslationKey().contains("grass") || state.getBlock().getTranslationKey().contains("stone")
                        || state.getBlock().getTranslationKey().contains("diorite") || state.getBlock().getTranslationKey().contains("andesite")
                        || state.getBlock().getTranslationKey().contains("netherrack") || state.getBlock().getTranslationKey().contains("nylium")
                        || state.getBlock().getTranslationKey().contains("brick") || state.getBlock().getTranslationKey().contains("basalt")
                        || state.getBlock().getTranslationKey().contains("charred") || state.getBlock().getTranslationKey().contains("blackstone")
                        || state.getBlock().getTranslationKey().contains("magma") || state.getBlock().getTranslationKey().contains("concrete")
                        || state.getBlock().getTranslationKey().contains("obsidian") )
                        && state.isSolid()) {
                    this.world.setBlockState(c.getPosition().down(), Blocks.LAVA.getDefaultState());
                }
            }
        }
        //this.setCustomName(new StringTextComponent(""));
        if ( this.ticksExisted == getDuration() - 1 ) {
            //read blocks


            // modify blocks
            this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT, 2.5F, 1F);
            for ( Coord c : Shapes.makeSphere(this.world, this.getPosition().down(), radius, true)) {
                BlockState state = this.world.getBlockState(c.getPosition());
                if ( state == Blocks.LAVA.getDefaultState() ) {
                    BlockState newState = this.getDataManager().get(new DataParameter<BlockState>(c.getX() * 100 + (c.getY() * 10) + c.getZ(), serializer));
                    this.world.setBlockState(c.getPosition(), newState); // Blocks.OBSIDIAN.getDefaultState() // states.get("kblock" + c.getX() + "-" + c.getY() + "-" + c.getZ())
                }
            }
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

        radius = compound.getInt("kradius");

        if ( this.ticksExisted == getDuration() - 1 ) {

            Hashtable<String, BlockState> states = new Hashtable<>();
            for ( Coord c : Shapes.makeSphere(this.world, this.getPosition(), radius, true)) {
                System.out.println("reading -> " + compound.getInt(c.getX() + "-" + c.getY() + "-" + c.getZ()) + ", " + Block.getStateById(compound.getInt(c.getX() + "-" + c.getY() + "-" + c.getZ())).getBlockState());
                states.put("kblock" + c.getX() + "-" + c.getY() + "-" + c.getZ(), Block.getStateById(compound.getInt(c.getX() + "-" + c.getY() + "-" + c.getZ())).getBlockState());
            }

            this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT, 2.5F, 1F);

            for ( Coord c : Shapes.makeSphere(this.world, this.getPosition(), radius, true)) {
                BlockState state = this.world.getBlockState(c.getPosition().down());
                if ( state == Blocks.LAVA.getDefaultState() ) {
                    this.world.setBlockState(c.getPosition().down(), states.get("kblock" + c.getX() + "-" + c.getY() + "-" + c.getZ())); // Blocks.OBSIDIAN.getDefaultState()
                }
            }

        }

        System.out.println("super read()");
        super.readAdditional(compound);

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

        if (this.ticksExisted == 19) {
            for ( Coord c : Shapes.makeSphere(this.world, this.getPosition(), radius, true)) {
                System.out.println("writing -> " + world.getBlockState(c.getPosition().down()).hashCode() + ", " + world.getBlockState(c.getPosition().down()) + " (" + Block.getStateById(world.getBlockState(c.getPosition().down()).hashCode()).getBlockState() + ")");
                compound.putInt("kblock" + c.getX() + "-" + c.getY() + "-" + c.getZ(), world.getBlockState(c.getPosition().down()).hashCode());
            }
        }

        compound.putInt("kradius", this.radius);
        System.out.println("super write()");
        super.writeAdditional(compound);
    }

}
*/