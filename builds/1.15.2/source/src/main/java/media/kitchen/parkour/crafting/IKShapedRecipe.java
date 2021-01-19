package media.kitchen.parkour.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;

public interface IKShapedRecipe<T extends IInventory> extends IRecipe<T> {
    int getRecipeWidth();
    int getRecipeHeight();
}