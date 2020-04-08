package com.kitchen.parkourcombat.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

	public static void init() {
		GameRegistry.addSmelting(ModItems.RUBY, new ItemStack(ModBlocks.RUBY_BLOCK, 1), 1.5f);
		GameRegistry.addSmelting(ModBlocks.RUBY_BLOCK, new ItemStack(Blocks.DIAMOND_BLOCK, 2), 3.0f);
		
		GameRegistry.addSmelting(ModItems.CHARGED_MAGICAL_BONE_CAST, new ItemStack(ModItems.BONE_BLADE, 1), 100.0f);
		GameRegistry.addSmelting(ModItems.CHARGED_DEAD_BLADE_CAST, new ItemStack(ModItems.LEGENDARY_BLADE, 1), 200.0f);
		GameRegistry.addSmelting(ModItems.CHARGED_LIFELESS_END_STONE_BLADE, new ItemStack(ModItems.END_STONE_BLADE, 1), 600.0f);
		GameRegistry.addSmelting(ModItems.CHARGED_MAGIC_GUNPOWDER_CELL, new ItemStack(ModItems.CREEPER_STAFF, 1), 3000.0f);
	}
}
