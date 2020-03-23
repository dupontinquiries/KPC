package dme.sucaro.main;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dme.sucaro.block.BlockManager;
import dme.sucaro.entity.EntityGravity;
import dme.sucaro.gen.SpaceshipGen;
import dme.sucaro.gen.GravityWellGen;
import dme.sucaro.item.ItemRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class MainRegistry {

	@Mod.Instance(Reference.MOD_ID)
	public static MainRegistry instance;
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		BlockManager.mainRegistry();
		ItemRegistry.init();
		RecipeHandler.registerRecipes();
		ResourceRegister.registerResources();
		//SoundHandler.registerSounds();
		proxy.registerRender();
	}

	@Mod.EventHandler
    public static void Load(final FMLInitializationEvent Event) {
		GameRegistry.registerTileEntity(EntityGravity.class, "kEntityGravity");
		GameRegistry.registerWorldGenerator((IWorldGenerator) new SpaceshipGen(), 2);
		GameRegistry.registerWorldGenerator((IWorldGenerator) new GravityWellGen(), 2);
		
    }
    
    @Mod.EventHandler
    public static void PostLoad(final FMLPostInitializationEvent PostEvent) {
    }
}
