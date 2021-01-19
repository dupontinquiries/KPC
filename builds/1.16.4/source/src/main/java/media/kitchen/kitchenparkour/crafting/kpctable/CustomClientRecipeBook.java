package media.kitchen.kitchenparkour.crafting.kpctable;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class CustomClientRecipeBook extends RecipeBook {
    private static final Logger field_241555_k_ = LogManager.getLogger();
    private Map<CustomRecipeBookCategories, List<RecipeList>> recipesByCategory = ImmutableMap.of();
    private List<RecipeList> allRecipes = ImmutableList.of();

    public void func_243196_a(Iterable<IRecipe<?>> p_243196_1_) {
        Map<CustomRecipeBookCategories, List<List<IRecipe<?>>>> map = func_243201_b(p_243196_1_);
        Map<CustomRecipeBookCategories, List<RecipeList>> map1 = Maps.newHashMap();
        Builder<RecipeList> builder = ImmutableList.builder();
        map.forEach((p_243197_2_, p_243197_3_) -> {
            List list = map1.put(p_243197_2_, p_243197_3_.stream().map(RecipeList::new).peek(builder::add).collect(ImmutableList.toImmutableList()));
        });
        CustomRecipeBookCategories.field_243235_w.forEach((p_243199_1_, p_243199_2_) -> {
            List list = map1.put(p_243199_1_, p_243199_2_.stream().flatMap((p_243198_1_) -> {
                return map1.getOrDefault(p_243198_1_, ImmutableList.of()).stream();
            }).collect(ImmutableList.toImmutableList()));
        });
        this.recipesByCategory = ImmutableMap.copyOf(map1);
        this.allRecipes = builder.build();
    }

    private static Map<CustomRecipeBookCategories, List<List<IRecipe<?>>>> func_243201_b(Iterable<IRecipe<?>> p_243201_0_) {
        Map<CustomRecipeBookCategories, List<List<IRecipe<?>>>> map = Maps.newHashMap();
        Table<CustomRecipeBookCategories, String, List<IRecipe<?>>> table = HashBasedTable.create();

        for(IRecipe<?> irecipe : p_243201_0_) {
            if (!irecipe.isDynamic()) {
                CustomRecipeBookCategories recipebookcategories = getCategory(irecipe);
                String s = irecipe.getGroup();
                if (s.isEmpty()) {
                    map.computeIfAbsent(recipebookcategories, (p_243202_0_) -> {
                        return Lists.newArrayList();
                    }).add(ImmutableList.of(irecipe));
                } else {
                    List<IRecipe<?>> list = table.get(recipebookcategories, s);
                    if (list == null) {
                        list = Lists.newArrayList();
                        table.put(recipebookcategories, s, list);
                        map.computeIfAbsent(recipebookcategories, (p_202890_0_) -> {
                            return Lists.newArrayList();
                        }).add(list);
                    }

                    list.add(irecipe);
                }
            }
        }

        return map;
    }

    private static CustomRecipeBookCategories getCategory(IRecipe<?> recipe) {
        IRecipeType<?> irecipetype = recipe.getType();
        if (irecipetype == IRecipeType.CRAFTING) {
            ItemStack itemstack = recipe.getRecipeOutput();
            ItemGroup itemgroup = itemstack.getItem().getGroup();
            if (itemgroup == Parkour.KPC_ARMOR) {
                return CustomRecipeBookCategories.KPC_COMBAT;
            } else if (itemgroup == Parkour.KPC_TOKENS) {
                return CustomRecipeBookCategories.KPC_TOKENS;
            } else {
                return CustomRecipeBookCategories.KPC_CRAFTING_SEARCH;
            }
        } else {
            field_241555_k_.warn("Unknown recipe category: {}/{}", () -> {
                return Registry.RECIPE_TYPE.getKey(recipe.getType());
            }, recipe::getId);
            return CustomRecipeBookCategories.UNKNOWN;
        }
    }

    public List<RecipeList> getRecipes() {
        return this.allRecipes;
    }

    public List<RecipeList> getRecipes(CustomRecipeBookCategories p_202891_1_) {
        return this.recipesByCategory.getOrDefault(p_202891_1_, Collections.emptyList());
    }
}
