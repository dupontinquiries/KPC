package media.kitchen.kitchenparkour.enchantment;

import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.util.PotionHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
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

            if (event instanceof RenderPlayerEvent.Pre) {
                RenderPlayerEvent.Pre preRenderEvent = ( RenderPlayerEvent.Pre ) event;
                PlayerEntity player = preRenderEvent.getPlayer();
                if ( player.isPotionActive( Effects.INVISIBILITY )) {
                    preRenderEvent.setCanceled(true);
                }
            }

            if (event instanceof LivingHurtEvent) {
                LivingHurtEvent le = (LivingHurtEvent) event;
                Entity attackerentity = le.getSource().getTrueSource();
                Entity defenderentity = le.getEntityLiving();
                if (defenderentity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) defenderentity;
                    boolean isArmorWorn = player.inventory.armorItemInSlot(EquipmentSlotType.FEET.getIndex())
                            .getItem() == Parkour.TAYDON_BOOTS.get();
                    if (isArmorWorn && player.world.rand.nextFloat() < .33) {
                        if (attackerentity instanceof LivingEntity){
                            PotionHelper.applyPotion((LivingEntity) attackerentity, Effects.BLINDNESS, 25, 1);
                            PotionHelper.applyPotion(player, Effects.INVISIBILITY, 45, 1);
                        }
                    }
                }
            }

        }

    }
}
