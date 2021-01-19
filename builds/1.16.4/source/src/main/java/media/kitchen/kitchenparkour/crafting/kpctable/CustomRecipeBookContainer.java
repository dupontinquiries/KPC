package media.kitchen.kitchenparkour.crafting.kpctable;

import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class CustomRecipeBookContainer<C extends IInventory> extends Container {
    public CustomRecipeBookContainer(ContainerType<?> type, int id) {
        super(type, id);
    }

    public void func_217056_a(boolean p_217056_1_, IRecipe<?> p_217056_2_, ServerPlayerEntity player) {
        (new CustomServerRecipePlacer<>(this)).place(player, (IRecipe<C>)p_217056_2_, p_217056_1_);
    }

    public abstract void fillStackedContents(RecipeItemHelper itemHelperIn);

    public abstract void clear();

    public abstract boolean matches(IRecipe<? super C> recipeIn);

    public abstract int getOutputSlot();

    public abstract int getWidth();

    public abstract int getHeight();

    @OnlyIn(Dist.CLIENT)
    public abstract int getSize();

    public List<CustomRecipeBookCategories> getRecipeBookCategories() {
        return CustomRecipeBookCategories.func_243236_a(this.func_241850_m());
    }

    @OnlyIn(Dist.CLIENT)
    public abstract CustomRecipeBookCategory func_241850_m();
}
