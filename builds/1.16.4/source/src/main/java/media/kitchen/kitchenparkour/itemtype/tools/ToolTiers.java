package media.kitchen.kitchenparkour.itemtype.tools;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ToolTiers implements IItemTier {
    EARTH_WIELDER(3, 561, 8.0F, 7.0F, 15, () -> {
        return Ingredient.fromItems(Items.DIRT.getItem());
    }),
    LAVA_WIELDER(EARTH_WIELDER, () -> {
        return Ingredient.fromItems(Items.DIRT.getItem());
    }),
    FIRE_STAFF(EARTH_WIELDER, () -> {
        return Ingredient.fromItems(Items.BLAZE_POWDER.getItem());
    });
    /*
    LAVA_WIELDER(3, 561, 8.0F, 7.0F, 15, () -> {
        return Ingredient.fromItems(Items.DIRT.getItem());
    });
    EARTH_WIELDER(3, 561, 8.0F, 7.0F, 15, () -> {
        return Ingredient.fromItems(Items.BLAZE_POWDER.getItem());
    });
     */

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    private ToolTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    private ToolTiers(IItemTier tier, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = tier.getHarvestLevel();
        this.maxUses = tier.getMaxUses();
        this.efficiency = tier.getEfficiency();
        this.attackDamage = tier.getAttackDamage();
        this.enchantability = tier.getEnchantability();
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}