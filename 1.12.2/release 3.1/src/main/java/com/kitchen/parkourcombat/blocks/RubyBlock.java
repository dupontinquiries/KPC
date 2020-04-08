package com.kitchen.parkourcombat.blocks;

import java.util.Random;

import com.kitchen.parkourcombat.entity.tileentity.RubyBlockTile;
import com.kitchen.parkourcombat.init.ModItems;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RubyBlock extends BlockBase implements ITileEntityProvider {
	
	private int charge = 0;
	private static final int MAX_CHARGE = 100;
	
	public static final PropertyBool TRIGGERED = PropertyBool.create("charged");
	
	public RubyBlock(String name, Material material) {
		super(name, material);
		
		setHardness(3.0F);
		setResistance(3.0F);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(0.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		RubyBlockTile tile = new RubyBlockTile();
		return tile;
	}
	
	public void setCharge(int charge) {
		this.charge = charge;
		if (charge == MAX_CHARGE) {
			
		}
	}
	
	/**
     * Get the Item that this Block should drop when harvested.
     */
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
	@Override
    public int quantityDropped(Random random)
    {
		return 1;
    }
	
	
}
