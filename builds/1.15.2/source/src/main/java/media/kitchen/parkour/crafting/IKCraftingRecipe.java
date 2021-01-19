package media.kitchen.parkour.crafting;

import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface IKCraftingRecipe extends ICraftingRecipe {
    @Override
    default IRecipeType<?> getType() {
            return IKRecipeType.KPC_CRAFTING_SHAPED;
        }
}