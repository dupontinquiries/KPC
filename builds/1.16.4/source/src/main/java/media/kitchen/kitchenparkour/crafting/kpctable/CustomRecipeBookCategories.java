package media.kitchen.kitchenparkour.crafting.kpctable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum CustomRecipeBookCategories {
    KPC_CRAFTING_SEARCH(new ItemStack(Parkour.KPC_TABLE.get())),
    KPC_COMBAT(new ItemStack(Parkour.PARKOUR_GRIPPER.get())),
    KPC_TOKENS(new ItemStack(Parkour.TOKEN_WARRIOR.get())),
    UNKNOWN(new ItemStack(Items.BARRIER));

    public static final List<CustomRecipeBookCategories> field_243234_v = ImmutableList.of(KPC_CRAFTING_SEARCH, KPC_COMBAT, KPC_TOKENS);
    public static final Map<CustomRecipeBookCategories, List<CustomRecipeBookCategories>> field_243235_w = ImmutableMap.of(KPC_CRAFTING_SEARCH, ImmutableList.of(KPC_COMBAT, KPC_TOKENS));
    private final List<ItemStack> icons;

    private CustomRecipeBookCategories(ItemStack... p_i48836_3_) {
        this.icons = ImmutableList.copyOf(p_i48836_3_);
    }

    public static List<CustomRecipeBookCategories> func_243236_a(CustomRecipeBookCategory p_243236_0_) {
        switch(p_243236_0_) {
            case KPC_CRAFTING:
                return field_243234_v;
            default:
                return ImmutableList.of();
        }
    }

    public List<ItemStack> getIcons() {
        return this.icons;
    }
}