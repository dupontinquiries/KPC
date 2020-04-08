package com.kitchen.parkourcombat.blocks;

import java.util.Random;

import com.kitchen.parkourcombat.init.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class ChargedRubyBlock extends BlockBase {
	
	public ChargedRubyBlock(String name, Material material) {
		super(name, material);
		
		setHardness(7.0F);
		setResistance(3.0F);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(12.0F);
	}
	
	/**
     * Get the Item that this Block should drop when harvested.
     */
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ModItems.CHARGED_RUBY;
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
	@Override
    public int quantityDropped(Random random)
    {
		return 5;
    }
	
	
}
