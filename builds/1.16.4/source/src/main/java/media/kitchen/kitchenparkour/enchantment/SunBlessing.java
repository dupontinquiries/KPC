package media.kitchen.kitchenparkour.enchantment;


import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
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
        return 5;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment b) {
        return !b.equals(Parkour.SUN_BLESSING.get());
    }

    @Mod.EventBusSubscriber(modid = Parkour.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class HasSunBlessing {

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void doStuff(Event event) {}

    }
}
