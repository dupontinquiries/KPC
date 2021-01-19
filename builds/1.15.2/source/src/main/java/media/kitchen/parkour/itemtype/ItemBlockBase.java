package media.kitchen.parkour.itemtype;

import media.kitchen.parkour.Parkour;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;

public class ItemBlockBase extends BlockItem {

    protected ArrayList<ItemGroup> tabs;

    public ItemBlockBase( Block block ) {
        super(block, new Item.Properties().maxStackSize(64));
        setup();
    }

    public void setup() {
        tabs = new ArrayList<ItemGroup>();
        addTab(Parkour.KPC_TAB);
        addTab(ItemGroup.BUILDING_BLOCKS);
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
    public ItemBlockBase addTab(ItemGroup t) {
        tabs.add(t);
        return this;
    }

    // !creative tabs

}
