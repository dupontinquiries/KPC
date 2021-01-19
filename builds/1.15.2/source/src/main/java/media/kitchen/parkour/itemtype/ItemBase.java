package media.kitchen.parkour.itemtype;

import media.kitchen.parkour.Parkour;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;

public class ItemBase extends Item {

    protected ArrayList<ItemGroup> tabs;

    public ItemBase(int n) {
        super(new Item.Properties().maxStackSize(n));
        setup();
    }

    public ItemBase() {
        super(new Item.Properties().maxStackSize(64));
        setup();
    }
    public ItemBase(Item.Properties props) {
        super(props);
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
    public ItemBase addTab(ItemGroup t) {
        tabs.add(t);
        return this;
    }

    // !creative tabs

}