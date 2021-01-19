package media.kitchen.kitchenparkour.enchantment;


import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class Reaper extends Enchantment {
    public Reaper(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots) {
        super(rarity, type, slots);

    }

    @Override
    public int getMaxLevel() {
        return 7;
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
    public static class HasReaper {

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void doStuff(Event event) {
            if ( event instanceof AttackEntityEvent ) {
                AttackEntityEvent ae = (AttackEntityEvent) event;
                LivingEntity entity = ae.getEntityLiving();
                if (entity.world.rand.nextFloat() < .3) return;
                Entity e2 = ae.getTarget();
                if ( e2 instanceof LivingEntity && entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    LivingEntity victim = (LivingEntity) e2;
                    if ( player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == Parkour.TAYDON_HELM.get() ) {
                        victim.attackEntityFrom(DamageSource.MAGIC, 2 * EnchantmentHelper.getEnchantmentLevel(Parkour.REAPER.get(), player.getItemStackFromSlot(EquipmentSlotType.HEAD)));
                        victim.hurtTime = 0;
                        ExperienceOrbEntity xp = new ExperienceOrbEntity(player.world, victim.getPosX(), victim.getPosY(), victim.getPosZ(), EnchantmentHelper.getEnchantmentLevel(Parkour.REAPER.get(), player.getItemStackFromSlot(EquipmentSlotType.HEAD)));
                        player.world.addEntity(xp);
                    }
                }
            }
        }

    }
}
