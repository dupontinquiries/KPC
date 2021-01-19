package media.kitchen.kitchenparkour.itemtype;

import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class MineralBase extends ItemBase {

    public MineralBase(Item.Properties props) {
        super(props);
        addTab(ItemGroup.MATERIALS);
        addTab(Parkour.KPC_TAB);
    }

}
