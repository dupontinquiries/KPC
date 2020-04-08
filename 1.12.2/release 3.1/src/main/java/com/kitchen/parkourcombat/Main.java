package com.kitchen.parkourcombat;

import org.apache.logging.log4j.Logger;

import com.kitchen.parkourcombat.entity.tileentity.ExplodingTrapTile;
import com.kitchen.parkourcombat.entity.tileentity.GravityTile;
import com.kitchen.parkourcombat.entity.tileentity.RubyBlockTile;
import com.kitchen.parkourcombat.init.ModRecipes;
import com.kitchen.parkourcombat.proxy.CommonProxy;
import com.kitchen.parkourcombat.util.Reference;
import com.kitchen.parkourcombat.world.GenGravityOre;
import com.kitchen.parkourcombat.world.GenNS;
import com.kitchen.parkourcombat.world.GenRubyOre;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main
{
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        
        // World Generators
        GameRegistry.registerWorldGenerator(new GenRubyOre(), 3);
        GameRegistry.registerWorldGenerator(new GenNS(), 3);
        //GameRegistry.registerWorldGenerator(new GenGravityOre(), 3);
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    	ModRecipes.init();
    	
    }
	
	@EventHandler
	public static void Postinit(FMLPostInitializationEvent event)
	{
		// Tile Enitites
        GameRegistry.registerTileEntity(ExplodingTrapTile.class, new ResourceLocation(Reference.MODID, "kExplodingTrap"));
		GameRegistry.registerTileEntity(RubyBlockTile.class, new ResourceLocation(Reference.MODID, "kRubyLightSensor"));
		GameRegistry.registerTileEntity(GravityTile.class, new ResourceLocation(Reference.MODID, "kGravityTile"));
	}
}
