package media.kitchen.kitchenparkour.enchantment;


import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AquaBlessing extends Enchantment {
    public AquaBlessing(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots) {
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

    @Mod.EventBusSubscriber(modid = Parkour.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class HasAquaBlessing {

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void doStuff(Event event) {
            if ( event instanceof LivingDamageEvent) {
                LivingDamageEvent le = (LivingDamageEvent) event;
                LivingEntity entity = le.getEntityLiving();
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    if ( player.isInWater() ) {
                        event.setCanceled(true);
                        player.setAir(player.getMaxAir());
                    }
                }
            }
            if ( event instanceof LivingEvent.LivingUpdateEvent) {
                    LivingEvent.LivingUpdateEvent le = (LivingEvent.LivingUpdateEvent) event;
                    LivingEntity entity = le.getEntityLiving();
                    if (entity instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity) entity;
                        if ( ( player.getHeldItem(Hand.MAIN_HAND).getItem() == Parkour.BLUE_FINS.get() || player.getHeldItem(Hand.OFF_HAND).getItem() == Parkour.BLUE_FINS.get() ) && player.isInWater() && player.ticksExisted % 10 == 0 && player.getAir() != player.getMaxAir() ) {
                            player.setAir(player.getMaxAir());
                            if ( player.isSneaking() ) {
                                
                            }
                            if ( player.isWet() ) {

                            }
                        }
                    }
            }
        }
    }
}
