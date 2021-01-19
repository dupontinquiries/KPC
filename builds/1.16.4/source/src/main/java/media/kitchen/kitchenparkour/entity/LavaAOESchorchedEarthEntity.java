package media.kitchen.kitchenparkour.entity;

import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.rule.IBendingRule;
import media.kitchen.kitchenparkour.serializers.BlockStateSerializer;
import media.kitchen.kitchenparkour.util.Coord;
import media.kitchen.kitchenparkour.util.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Vector;

public class LavaAOESchorchedEarthEntity extends AreaEffectCloudEntity {

    BlockStateSerializer serializer = new BlockStateSerializer();

    protected int radius = 3;

    public LavaAOESchorchedEarthEntity(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        //states = new Hashtable<>(); //4 * radius * radius, 0.8F
    }

    @Override
    public void tick() {

        this.setParticleData(ParticleTypes.LAVA);
        super.tick();
        if ( this.ticksExisted == 1 ) {
            this.world.playSound(null, this.getPosition(), Parkour.EARTH_WIELD_WINDUP.get(), SoundCategory.AMBIENT, 1.5F, 1F);
        }
        if ( this.ticksExisted % 15 == 0 ) {
            //this.world.playSound(null, this.getPosition(), Parkour.EARTH_WIELD_WINDUP.get(), SoundCategory.AMBIENT, 0.7F, 1F);
        }
        if ( this.ticksExisted == 20 ) {
            Vector<Coord> sphere = Shapes.makeSphereVector(this.world, this.getPosition().down(), radius, true);
            this.world.playSound(null, this.getPosition(), Parkour.EARTH_WIELD_LIFT.get(), SoundCategory.AMBIENT, 1.5F, 1F);
            String name = "";
            for ( int i = 0; i < sphere.size(); ++i ) {
                Coord c = sphere.get(i);
                BlockState state = this.world.getBlockState(c.getPosition());
                if ( IBendingRule.EARTH_BENDING_RULE.criteriaMet(state) ) {
                    //name += c.getX() + " " + c.getY() + " " + c.getZ() + " " + state.getBlock().getRegistryName() + ",";
                    name += i + " " + state.getBlock().getRegistryName() + ",";
                    this.world.setBlockState(c.getPosition(), Blocks.LAVA.getDefaultState());
                }
            }
            name = name.substring(0, name.length() - 1);
            this.setCustomName(new StringTextComponent(name));
            this.setCustomNameVisible(false);
        }
        if ( this.ticksExisted == getDuration() - 1 ) {
            Vector<Coord> sphere = Shapes.makeSphereVector(this.world, this.getPosition().down(), radius, true);
            String name = this.getCustomName().getString();
            String[] sublets = name.split(",");
            for ( String s : sublets ) {
                String[] parts = s.split(" ");
                int index = Integer.parseInt(parts[0]);
                Block block = Registry.BLOCK.getOrDefault(new ResourceLocation(parts[1]));
                BlockState blockstate = block.getDefaultState();
                world.setBlockState(sphere.get(index).getPosition(), Blocks.OBSIDIAN.getDefaultState());
            }
            this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT, 1.5F, 1F);
        }
    }

}