package media.kitchen.parkour.itemtype.armor;

import media.kitchen.parkour.Parkour;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;

public class ArmorBase extends ArmorItem {

    // constructors

    protected ArrayList<ItemGroup> tabs;

    public ArmorBase(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
        setup();
    }

    private void setup() {
        tabs = new ArrayList<ItemGroup>();
        addTab(Parkour.KPC_TAB);
        addTab(Parkour.KPC_ARMOR);
    }

    // !constructors

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
    public ArmorBase addTab(ItemGroup t) {
        tabs.add(t);
        return this;
    }

    // !creative tabs

}
