package media.kitchen.parkour.compat;

//@JEIPlugin
public class JEIKPCPlugin {} /* implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {

        //registry.handleRecipes(IAddUpgradeRecipe.class, AddUpgradeRecipeWrapper::new, AddUpgradeRecipeCategory.NAME);

        //registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), AddUpgradeRecipeCategory.NAME, RemoveUpgradeRecipeCategory.NAME, IncreaseTierRecipeCategory.NAME);

        //registry.addRecipes(RecipeRegistry.UPGRADE_ADD, AddUpgradeRecipeCategory.NAME);

        //Add backpack descriptions
        //registry.addIngredientInfo(new ItemStack(ItemRegistry.basicBackpack), ItemStack.class, "jei.description.backpack.basic", "jei.description.backpack.generic");

        //Add upgrade descriptions
        //basic
        //registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.additionalUpgradePointsUpgrade)), ItemStack.class, "jei.description.upgrade.additionalUpgradePoints");

        //conflicting
        //registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)), ItemStack.class, "jei.description.upgrade.nesting", "jei.description.upgrade.conflicting");

        //configurable
        //registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingUpgrade)), ItemStack.class, "jei.description.upgrade.crafting", "jei.description.upgrade.configurable");

        //        IItemBlacklist itemBlacklist = registry.getJeiHelpers().getItemBlacklist();
        //        itemBlacklist.addItemToBlacklist(); //ToDo
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper gui = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new AddUpgradeRecipeCategory(gui), new RemoveUpgradeRecipeCategory(gui), new IncreaseTierRecipeCategory(gui));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime run) {
    }

}
*/