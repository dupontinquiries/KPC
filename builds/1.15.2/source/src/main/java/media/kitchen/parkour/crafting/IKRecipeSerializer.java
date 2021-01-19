package media.kitchen.parkour.crafting;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IKRecipeSerializer<T extends IRecipe<?>> extends net.minecraftforge.registries.IForgeRegistryEntry<IRecipeSerializer<?>> {
    //IRecipeSerializer<KPCShapedRecipe> KPC_CRAFTING_SHAPED = register("kpc_crafting_shaped", new KPCShapedRecipe.Serializer());
    //IRecipeSerializer<ShapelessRecipe> CRAFTING_SHAPELESS = register("crafting_shapeless", new ShapelessRecipe.Serializer());
    //CookingRecipeSerializer<FurnaceRecipe> SMELTING = register("smelting", new CookingRecipeSerializer<>(FurnaceRecipe::new, 200));

    T read(ResourceLocation recipeId, JsonObject json);

    @javax.annotation.Nullable
    T read(ResourceLocation recipeId, PacketBuffer buffer);

    void write(PacketBuffer buffer, T recipe);

    static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register(String key, S recipeSerializer) {
        return (S)(Registry.<IRecipeSerializer<?>>register(Registry.RECIPE_SERIALIZER, key, recipeSerializer));
    }
}