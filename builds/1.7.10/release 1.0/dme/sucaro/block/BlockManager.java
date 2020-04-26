// 
// Decompiled by Procyon v0.5.36
// 

package dme.sucaro.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class BlockManager
{
    //public static Block CompressedEridium;
	public static Block gravOre;
	
	public static Block spaceship;
	public static Block brokenSpaceship;
	
	public static Block aquaRail;
    
    public static void mainRegistry() {
        initializeBlocks();
        registerBlocks();
    }
    
    public static void initializeBlocks() {
        //BlockManager.CompressedEridium = (Block) new CompressedEridium(Material.);
    	BlockManager.gravOre = new GravityOre(Material.iron).setBlockTextureName("sucaro:compEridium");
    	
    	BlockManager.spaceship = new SpaceshipBlock(Material.rock).setBlockTextureName("sucaro:polishedMetal");
    	BlockManager.brokenSpaceship = new BrokenSpaceshipBlock(Material.rock).setBlockTextureName("sucaro:crackedMetal");
    	
    	BlockManager.aquaRail = (Block) new AquaRail().setBlockTextureName("minecraft:rail");
    }
    
    public static void registerBlocks() {
        //GameRegistry.registerBlock(BlockManager.CompressedEridium, BlockManager.CompressedEridium.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockManager.gravOre, BlockManager.gravOre.getUnlocalizedName());
    	
    	GameRegistry.registerBlock(BlockManager.spaceship, BlockManager.spaceship.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockManager.brokenSpaceship, BlockManager.brokenSpaceship.getUnlocalizedName());
    	
    	//GameRegistry.registerBlock(BlockManager.aquaRail, BlockManager.aquaRail.getUnlocalizedName());
    }

}
   