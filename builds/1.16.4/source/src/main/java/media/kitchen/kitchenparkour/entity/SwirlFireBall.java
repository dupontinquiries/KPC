package media.kitchen.kitchenparkour.entity;

import media.kitchen.kitchenparkour.util.Coord;
import media.kitchen.kitchenparkour.util.Shapes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class SwirlFireBall extends SmallFireballEntity {
    public SwirlFireBall(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }

    private int newTicks = 0;

    @Override
    public void tick() {
        //double fac = 0.01;
        //this.addVelocity(getMotion().getX() * fac, getMotion().getY() * fac, getMotion().getZ() * fac);
        if ( newTicks % 5 == 0 ) {
            for ( Coord c : Shapes.makeSphere(world, getPosition(), 3, true) ) {
                for ( char a = 0; a < world.rand.nextInt(7); ++a ) {
                    world.addParticle(ParticleTypes.LAVA, c.getX() + offset(world.getRandom()), c.getY() + offset(world.getRandom()), c.getZ() + offset(world.getRandom()),
                            randMotionMag(world.getRandom()), .7 + randMotionMag(world.getRandom()), randMotionMag(world.getRandom()));
                }
                if ( newTicks > 20 && world.getBlockState(c.getPosition().down()).isSolid() && world.getBlockState(c.getPosition()).isAir() && world.rand.nextInt(8) == 1 ) {
                    world.setBlockState(c.getPosition(), Blocks.FIRE.getDefaultState());
                }
            }
        }
        ++newTicks;
        super.tick();
    }

    private double randMotionMag(Random r) {
        return (r.nextDouble() - .5 ) * 0.4;
    }

    private double offset(Random r) {
        return (r.nextDouble() - .5 ) * 1.2;
    }
}
