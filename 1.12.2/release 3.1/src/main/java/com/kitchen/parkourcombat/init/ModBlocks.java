package com.kitchen.parkourcombat.init;

import java.util.ArrayList;
import java.util.List;

import com.kitchen.parkourcombat.blocks.ExplodingTrap;
import com.kitchen.parkourcombat.blocks.GravityOre;
import com.kitchen.parkourcombat.blocks.RubyBlock;
import com.kitchen.parkourcombat.blocks.OreDrop;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	// Ores
	
	// Ruby
	public static final Block RUBY_BLOCK = new RubyBlock("ruby_block", Material.IRON);
	public static final Block CHARGED_RUBY_BLOCK = new RubyBlock("charged_ruby_block", Material.IRON);
	public static final Block RUBY_ORE = new OreDrop("ruby_ore", Material.ROCK, ModItems.RUBY);
	
	public static final Block GRAVITY_ORE = new GravityOre("gravity_ore", Material.IRON);
	
	//NS
	public static final Block NS_BLOCK = new RubyBlock("ns_block", Material.IRON);
	public static final Block NS_ORE = new OreDrop("ns_ore", Material.ROCK, ModItems.NS_GEM);
	
	// Trap Blocks
	
	// Exploding Trap
	public static final ExplodingTrap EXPLODING_TRAP = new ExplodingTrap("exploding_trap", Material.WOOD, 3, 3.0f, 120.0f);
	public static final ExplodingTrap MEGA_EXPLODING_TRAP = new ExplodingTrap("mega_exploding_trap", Material.WOOD, 5, 20.0f, 240.0f);
	
}
