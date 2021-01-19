package media.kitchen.parkour.enchantment;


import media.kitchen.parkour.Parkour;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.entity.player.PlayerEntity;

public class SunBlessing extends Enchantment {
    public SunBlessing(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots) {
        super(rarity, type, slots);

    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment b) {
        return ( ! ( b.equals(Enchantments.PROTECTION) || b.equals(Parkour.SUN_BLESSING.get()) ) );
    }

    @Mod.EventBusSubscriber(modid = Parkour.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class HasSunBlessing {

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void doStuff(Event event) {


            if ( event instanceof LivingHurtEvent) {
                LivingHurtEvent le = (LivingHurtEvent) event;
                LivingEntity entity = le.getEntityLiving();
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    boolean isArmorWorn = player.inventory.armorItemInSlot(EquipmentSlotType.FEET.getIndex())
                            .getItem() == Parkour.TAYDON_BOOTS.get();
                    if (isArmorWorn) {
                        if ( le.getSource() == DamageSource.HOT_FLOOR || le.getSource() == DamageSource.IN_FIRE || le.getSource() == DamageSource.LAVA ) {
                            le.setCanceled(true);
                        }
                    }
                }
            }


            /*
            if ( event instanceof ArrowLooseEvent) {
                ArrowLooseEvent le = (ArrowLooseEvent) event;
                LivingEntity entity = le.getEntityLiving();
                le.get
                EntityArrow arrow = le.getEntity();
            }


            // extra xp

            if ( event instanceof PlayerXpEvent.PickupXp ) {
                PlayerXpEvent.PickupXp le = (PlayerXpEvent.PickupXp) event;
                PlayerEntity player = le.getPlayer();
                player.giveExperiencePoints(20);
            }


            // lightning

            if ( event instanceof EntityStruckByLightningEvent ) {
                EntityStruckByLightningEvent le = (EntityStruckByLightningEvent) event;
                Entity entity = le.getEntity();
                if ( entity instanceof LivingEntity ) {
                    LivingEntity living = (LivingEntity) entity;
                    boolean flag = living.isPotionActive( Effects.RESISTANCE );

                    if ( !flag ) {
                        living.addPotionEffect( new EffectInstance( Effects.RESISTANCE, 1800, 2) );
                    }

                    flag = living.isPotionActive( Effects.STRENGTH );

                    if ( !flag ) {
                        living.addPotionEffect( new EffectInstance( Effects.STRENGTH, 1800, 2) );
                    }

                    flag = living.isPotionActive( Effects.SPEED );

                    if ( !flag ) {
                        living.addPotionEffect( new EffectInstance( Effects.SPEED, 1800, 2) );
                    }

                    flag = living.isPotionActive( Effects.HASTE );

                    if ( !flag ) {
                        living.addPotionEffect( new EffectInstance( Effects.HASTE, 1800, 2) );
                    }

                    if ( living instanceof PlayerEntity ) {
                        PlayerEntity player = (PlayerEntity) living;
                        player.giveExperiencePoints(250);
                    }
                }
            }
            */


        }
    }
}
