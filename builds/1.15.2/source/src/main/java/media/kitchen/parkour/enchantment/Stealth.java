package media.kitchen.parkour.enchantment;

import media.kitchen.parkour.Parkour;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Stealth extends Enchantment {
    public Stealth(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots) {
        super(rarity, type, slots);

    }

    @Override
    public int getMaxLevel() { return 3; }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment b) {
        return ( ! ( b.equals(Enchantments.FROST_WALKER) || b.equals(Enchantments.DEPTH_STRIDER) || b.equals(Parkour.STEALTH.get() ) ) );
    }

    @Mod.EventBusSubscriber(modid = Parkour.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class HasStealth {

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void doStuff(Event event) {

            /*
            if (event instanceof RenderPlayerEvent.Pre) {
                RenderPlayerEvent.Pre preRenderEvent = ( RenderPlayerEvent.Pre ) event;
                PlayerEntity player = preRenderEvent.getPlayer();
                //player.setInvisible(true);
                if ( player.isPotionActive( Effects.INVISIBILITY )) {
                    preRenderEvent.setCanceled(true);
                }
            }
            */
            if ( event instanceof LivingHurtEvent) {
                LivingHurtEvent le = (LivingHurtEvent) event;
                LivingEntity entity = le.getEntityLiving();
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    boolean isArmorWorn = player.inventory.armorItemInSlot(EquipmentSlotType.FEET.getIndex())
                            .getItem() == Parkour.TAYDON_BOOTS.get();
                    if (isArmorWorn) {
                        boolean flag = entity.isPotionActive( Effects.RESISTANCE );
                        if ( !flag || ( entity.getActivePotionEffect( Effects.RESISTANCE ).getDuration() < 20 ) ) {
                            if (player.getHealth() < .2 * player.getMaxHealth()) {
                                entity.addPotionEffect( new EffectInstance( Effects.RESISTANCE, 65, 4) );
                                entity.heal(le.getAmount() / 2);
                            }
                        }
                        flag = entity.isPotionActive( Effects.SPEED );
                        if ( !flag || ( entity.getActivePotionEffect( Effects.SPEED ).getDuration() < 20 ) ) {
                            if (player.getHealth() < .2 * player.getMaxHealth()) {
                                entity.addPotionEffect( new EffectInstance( Effects.SPEED, 25, 2) );
                            }
                        }
                    }
                }
                /*
                boolean flag = entity.isPotionActive( Effects.INVISIBILITY );
                        if ( !flag || ( flag && entity.getActivePotionEffect( Effects.INVISIBILITY ).getDuration() < 20 ) ) {
                            entity.addPotionEffect( new EffectInstance( Effects.INVISIBILITY, 25, 1) );
                            entity.heal(2);
                        }
                 */
            }

        }

    }
}
