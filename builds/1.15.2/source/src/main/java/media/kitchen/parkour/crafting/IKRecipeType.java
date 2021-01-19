package media.kitchen.parkour.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Optional;

public interface IKRecipeType<T extends IRecipe<?>> {
    IRecipeType<ICraftingRecipe> KPC_CRAFTING_SHAPED = register("kpc_crafting");
    IRecipeType<ICraftingRecipe> KPC_CRAFTING_SHAPELESS = register("kpc_crafting_shapeless");
    //IRecipeType<FurnaceRecipe> SMELTING = register("smelting");

    static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>() {
            public String toString() {
                return key;
            }
        });
    }

    default <C extends IInventory> Optional<T> matches(IRecipe<C> recipe, World worldIn, C inv) {
        return recipe.matches(inv, worldIn) ? Optional.of((T)recipe) : Optional.empty();
    }
}