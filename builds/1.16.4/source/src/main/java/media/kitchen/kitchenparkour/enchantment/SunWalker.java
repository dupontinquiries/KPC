package media.kitchen.kitchenparkour.enchantment;


import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.util.Coord;
import media.kitchen.kitchenparkour.util.PotionHelper;
import media.kitchen.kitchenparkour.util.Shapes;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

public class SunWalker extends Enchantment {
    public SunWalker(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots) {
        super(rarity, type, slots);

    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment b) {
        return true;
    }

    public static double randMotionMag(Random r) {
        return (r.nextDouble() - .5 ) * 0.4;
    }

    public static double offset(Random r) {
        return (r.nextDouble() - .5 ) * 1.2;
    }

    @Mod.EventBusSubscriber(modid = Parkour.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class HasSunWalker {

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void doStuff(Event event) {
            if ( event instanceof LivingDamageEvent ) {
                LivingEntity le = ((LivingDamageEvent) event).getEntityLiving();
                if ( le instanceof PlayerEntity ) {
                    PlayerEntity p = (PlayerEntity) le;
                    ItemStack s = p.getItemStackFromSlot(EquipmentSlotType.FEET);
                    if ( EnchantmentHelper.getEnchantments(s).containsKey(Parkour.SUN_WALKER.getId()) ) {
                        event.setCanceled(true);
                        PotionHelper.applyPotion(p, Effects.FIRE_RESISTANCE, 45, 1);
                    }
                }
            }
            if ( event instanceof LivingEvent.LivingUpdateEvent) {
                LivingEvent.LivingUpdateEvent le = (LivingEvent.LivingUpdateEvent) event;
                LivingEntity entity = le.getEntityLiving();
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    if ( player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == Parkour.SOLAR_BOOTS.get() ) {
                        if ( ( player.isInLava() || player.isBurning() ) ) {
                            PotionHelper.applyPotion(player, Effects.FIRE_RESISTANCE, 45, 1);
                            if ( player.ticksExisted % 10 == 0 ) {
                                if ( player.world.getBlockState(player.getPosition()) == Blocks.SOUL_FIRE.getDefaultState() ) {
                                    player.world.setBlockState(player.getPosition(), Blocks.FIRE.getDefaultState());
                                }
                                PotionHelper.applyPotion(player, Effects.RESISTANCE, 45, 1);
                                PotionHelper.applyPotion(player, Effects.STRENGTH, 45, 1);
                            }
                            if ( player.ticksExisted % 10 == 0 ) {
                                if ( player.world.rand.nextFloat() < .6 ) {
                                    int count = 0;
                                    while (count < 12) {
                                        ++count;
                                        BlockPos pos = player.getPosition().add(player.world.rand.nextInt(4) - 2, player.world.rand.nextInt(4) - 2, player.world.rand.nextInt(4) - 2);
                                        if ( player.world.getBlockState(pos).isAir() && player.world.getBlockState(pos.down()).isSolid() && player.world.getBlockState(pos.down()) != Blocks.FIRE.getDefaultState() && player.world.getBlockState(pos.down()) != Blocks.SOUL_FIRE.getDefaultState() ) {
                                            player.world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                                            break;
                                        }
                                    }
                                }
                            }
                            if ( player.ticksExisted % 4 == 0 && player.isSneaking() ) {
                                PotionHelper.applyPotion(player, Effects.INVISIBILITY, 90, 1);
                                for ( Coord c : Shapes.makeSphere(player.world, player.getPosition(), 3, true) ) {
                                    if ( player.world.rand.nextFloat() < .8 && !player.world.getBlockState(new BlockPos(c.getX(), c.getY(), c.getZ())).isSolid() ) {
                                        for ( char a = 0; a < player.world.rand.nextInt(36); ++a ) {
                                            player.world.addParticle(ParticleTypes.ASH, c.getX() + offset(player.world.getRandom()), c.getY() + offset(player.world.getRandom()), c.getZ() + offset(player.world.getRandom()),
                                                    randMotionMag(player.world.getRandom()), .7 + randMotionMag(player.world.getRandom()), randMotionMag(player.world.getRandom()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

    }
}
