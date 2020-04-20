package com.kitchen.parkourcombat.world;

import java.util.ArrayList;
import java.util.Random;

import com.kitchen.parkourcombat.init.ModBlocks;
import com.kitchen.parkourcombat.util.Coord;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.IWorldGenerator;

public class GenGravityOre implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		if (world.provider.getDimension() == 0) {
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
	
	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.GRAVITY_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 100, 200, 15);
	}
	
	//helper for sphere
	private double lengthSq(int x, int y, int z) {
		return x*x + y*y + z*z;
	}
	
	//gen a list of coords for sphere
	private ArrayList<Coord> makeSphere(World world, int _x, int _y, int _z, double radius, boolean filled) {
		ArrayList<Coord> list = new ArrayList<Coord>();
		radius += 0.5D; //I think they do this so the radius is measured from the center of the block
	    double radiusSq = radius * radius; //Square of the radius, so we don't need to use square roots for distance calcs
	    double radius1Sq = (radius - 1.0D) * (radius - 1.0D); //Square of the radius of a circle 1 block smaller, for making a hollow sphere

	    int ceilRadius = (int)Math.ceil(radius); //Round the radius up
	    //Loop through x,y,z up to the rounded radius
	    for (int x = 0; x <= ceilRadius; x++) {
		  for (int y = 0; y <= ceilRadius; y++) {
		    for (int z = 0; z <= ceilRadius; z++) {
			  double dSq = lengthSq(x, y, z); //Gets the square of the distance from the center (x*x + y*y + z*z). Again using squares so we don't need to square root

	          //If the distance to this point is greater than the radius, skip it (this is what makes this whole thing make a sphere, instead of a cube)
			  if (dSq > radiusSq) {
			    continue;
			  }
			  //If sphere should be hollow, and the point is within the sphere, but also within the 1-smaller sphere, skip it
			  if ((!filled) && (
			    (dSq < radius1Sq) || ((lengthSq(x + 1, y, z) <= radiusSq) && (lengthSq(x, y + 1, z) <= radiusSq) && (lengthSq(x, y, z + 1) <= radiusSq))))
			  {
			    continue;
			  }
	          
	          //Place the block in every +/- direction around the center
			  list.add(new Coord(x + _x, y + _y, z + _z));

			  list.add(new Coord(-x + _x, y + _y, z + _z));

			  list.add(new Coord(x + _x, -y + _y, z + _z));

			  list.add(new Coord(x + _x, y + _y, -z + _z));
			  

			  list.add(new Coord(-x + _x, -y + _y, z + _z));

			  list.add(new Coord(x + _x, -y + _y, -z + _z));
			  
			  list.add(new Coord(-x + _x, y + _y, -z + _z));

			  list.add(new Coord(-x + _x, -y + _y, -z + _z));
		    }
		  }
	    }
		return list;
	}
	
	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int chances) 
	{
		int deltaY = maxY - minY;
		
		for (int i = 0; i < chances; i++) {
			int cx = x + random.nextInt(16), cy = minY + random.nextInt(deltaY), cz = z + random.nextInt(16);
			
			for (Coord c : makeSphere(world, cx, cy, cz, random.nextInt(8) + 2, false)) {
				BlockPos bp = new BlockPos(c.getX(), c.getY(), c.getZ());
				if (random.nextDouble() < .7) {
					world.setBlockState(bp, ModBlocks.GRAVITY_ORE.getDefaultState(), 2);
				} else if (random.nextDouble() < .8) {
					world.setBlockState(bp, Blocks.DIAMOND_ORE.getDefaultState(), 2);
				} else if (random.nextDouble() < .9) {
					world.setBlockState(bp, ModBlocks.NS_ORE.getDefaultState(), 2);
				} else {
					world.setBlockState(bp, ModBlocks.RUBY_ORE.getDefaultState(), 2);
				}
			}
			BlockPos bp = new BlockPos(cx, cy, cz);
			
			world.setBlockState(bp, Blocks.CHEST.getDefaultState(), 2);
			
			TileEntity tileentity = world.getTileEntity(bp);
			
			LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation("kitchenparkourcombat:gravity_ore_table"));

            if (tileentity instanceof TileEntityChest)
            {
                ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_END_CITY_TREASURE, random.nextLong());
            }
            
            
			
            /*
			ArrayList<WeightedRandomChestContent> contents = new ArrayList<WeightedRandomChestContent>();
			
			contents.add(new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3));
			contents.add(new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10));
			contents.add(new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5));
			contents.add(new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5));
			contents.add(new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5));
			contents.add(new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5));
			contents.add(new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5));
			contents.add(new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5));
			contents.add(new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5));
			contents.add(new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5));
			contents.add(new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5));
			contents.add(new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3));
			contents.add(new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1));
			contents.add(new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1));
			contents.add(new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1));
			contents.add(new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 1));
			contents.add(new WeightedRandomChestContent(Items.lead, 0, 1, 1, 1));
			contents.add(new WeightedRandomChestContent(Items.leather, 0, 1, 5, 10));
			contents.add(new WeightedRandomChestContent(Items.bow, 0, 1, 1, 1));
			contents.add(new WeightedRandomChestContent(Items.experience_bottle, 0, 1, 1, 1));
    		contents.add(new WeightedRandomChestContent(Items.arrow, 0, 1, 5, 10));
    		*/
			
			
			//BlockPos center = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			
			
			
			//WorldGenMinable generator = new WorldGenMinable(ore, 1);
			//generator.generate(world, random, pos);
		}
	}
}
