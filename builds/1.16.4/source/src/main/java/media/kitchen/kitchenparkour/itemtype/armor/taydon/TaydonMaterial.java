package media.kitchen.kitchenparkour.itemtype.armor.taydon;

import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

public class TaydonMaterial implements IArmorMaterial {

    private static HashMap<EquipmentSlotType, Float> multipliers = new HashMap<EquipmentSlotType, Float>();

    public static void doInitStuff() {
        multipliers.put(EquipmentSlotType.HEAD, 0.5F);
        multipliers.put(EquipmentSlotType.CHEST, 1F);
        multipliers.put(EquipmentSlotType.LEGS, 0.7F);
        multipliers.put(EquipmentSlotType.FEET, 0.5F);
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return 420;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) { return (int) ( 12 * multipliers.get(slotIn) ); }

    @Override
    public int getEnchantability() { return ItemTier.DIAMOND.getEnchantability() + 7; }

    @Override
    public SoundEvent getSoundEvent() {
        return Parkour.PARKOUR_GRIPPER_READY.get();
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems( Parkour.CHARGED_RUBY_GEM.get().asItem() );
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return new ResourceLocation(Parkour.MOD_ID, "taydon").toString();
    }

    @Override
    public float getToughness() {
        return 5;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.8F;
    }

}
