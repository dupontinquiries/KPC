package media.kitchen.kitchenparkour.itemtype.tools;

import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.itemtype.ItemBase;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;

public class SwordBase extends SwordItem {

    private final double attackDamage;
    protected ArrayList<ItemGroup> tabs;

/** Modifiers applied when the item is in the mainhand of a user. */
    private Multimap<Attribute, AttributeModifier> attributeModifiers; ////// may need to make final again

    public SwordBase(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = attackDamageIn;
        setup();
    }

    private void setup() {
        tabs = new ArrayList<ItemGroup>();
        addTab(Parkour.KPC_TAB);
    }

    // creative tabs

    @Override
    protected boolean isInGroup(ItemGroup group) {
        if (tabs.contains(group)) return true;
        if (getCreativeTabs().stream().anyMatch(tab -> tab == group)) return true;
        ItemGroup itemgroup = this.getGroup();
        return itemgroup != null && (group == ItemGroup.SEARCH || group == itemgroup);
    }

    /**
     * Adds this item to a creative tab
     * @param t = tab to add item to
     * @return the item type instance ( only if needed )
     */
    public SwordBase addTab(ItemGroup t) {
        tabs.add(t);
        return this;
    }

    // !creative tabs

    /*
    @Override
    public int getItemEnchantability() {
        return (int) (5 + (0.7 * attackDamage));
    }
     */
}