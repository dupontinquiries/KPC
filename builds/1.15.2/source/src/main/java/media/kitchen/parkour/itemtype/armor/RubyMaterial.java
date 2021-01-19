package media.kitchen.parkour.itemtype.armor;

import media.kitchen.parkour.Parkour;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

public class RubyMaterial implements IArmorMaterial {

    private static HashMap<EquipmentSlotType, Float> multipliers = new HashMap<EquipmentSlotType, Float>();

    public static void doInitStuff() {
        multipliers.put(EquipmentSlotType.HEAD, 0.5F);
        multipliers.put(EquipmentSlotType.CHEST, 1F);
        multipliers.put(EquipmentSlotType.LEGS, 0.7F);
        multipliers.put(EquipmentSlotType.FEET, 0.5F);
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return (int) ( 100 * multipliers.get(slotIn) );
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) { return (int) ( 7 * multipliers.get(slotIn) ); }

    @Override
    public int getEnchantability() { return 13; }

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
        return new ResourceLocation(Parkour.MOD_ID, "ruby").toString();
    }

    @Override
    public float getToughness() {
        return 1;
    }

}
