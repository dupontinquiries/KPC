package media.kitchen.parkour.compat;

/**
 * Class for registering all compatibility modules for interacting with other mods
 */
public class InterModSupport {
    /*

    //as of writing this, it's basically non-existent
    public static boolean isEnderStorageLoaded = false;
    public static boolean isJEILoaded = false;
    public static boolean isExtraUtilsLoaded = false;
    public static boolean isOpenBlocksLoaded = false;

    //Initializes each mod in the pre-init phase
    public static void preinit() {
        initVersionChecker();
    }

    //Initializes each mod in the init phase
    public static void init() {
        initEnderStorage();
        initJEI();
        initExUtils();
//        initOpenBlocks(); //not updated
    }

    //Initializes each mod in the post-init phase
    public static void postinit() {

    }

    public static void initVersionChecker(){
        if (Loader.isModLoaded("VersionChecker")) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("curseProjectName", "227049-iron-backpacks");
            tagCompound.setString("curseFilenameParser", "IronBackpacks-1.10.2-[].jar");
            FMLInterModComms.sendRuntimeMessage(Constants.MODID, "VersionChecker", "addCurseCheck", tagCompound);
        }
    }

    public static void initJEI(){
        if (Loader.isModLoaded("JEI")) {
            isJEILoaded = true;
        }
    }

    @Optional.Method(modid = "JEI")
    public static boolean isGuiContainerInstanceOfIGuiHelper(GuiContainer container){
        return container instanceof IGuiHelper;
    }


    public static void initEnderStorage(){
        if (Loader.isModLoaded("EnderStorage")) {
            isEnderStorageLoaded = true;
        }
    }

    static final ResourceLocation pouch = new ResourceLocation("enderstorage:ender_pouch");

    @Optional.Method(modid = "enderstorage")
    public static boolean isEnderPouch(Item item) {
        return item.getRegistryName().equals(pouch);
    }

    public static void initExUtils(){
        if (Loader.isModLoaded("extrautils2")) {
            isExtraUtilsLoaded = true;
        }
    }

    @Optional.Method(modid = "extrautils2")
    public static boolean isExUtilsBuildersWand(Item item) {
        return false;//item instanceof ItemBuildersWand; TODO: go find wand regname
    }
    */
}