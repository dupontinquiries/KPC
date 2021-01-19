package media.kitchen.kitchenparkour.enchantment;

import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SchorchedEarthEnchantment extends Enchantment {
    public SchorchedEarthEnchantment(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots) {
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
    public static class HasSchorchedEarthEnchantment {

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void doStuff(Event event) {}

    }
}
