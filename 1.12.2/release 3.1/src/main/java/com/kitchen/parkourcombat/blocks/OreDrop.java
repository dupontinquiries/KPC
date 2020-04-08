package com.kitchen.parkourcombat.blocks;

import java.util.Random;

import com.kitchen.parkourcombat.init.ModItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class OreDrop extends BlockBase {

	private Item drop;

	public OreDrop(String name, Material material, Item drop) {
		super(name, material);
		this.drop = drop;
		setSoundType(SoundType.METAL);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.drop;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		int max = 2;
		int min = 1;
		return rand.nextInt(max) + min;
	}
}
