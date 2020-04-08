package com.kitchen.parkourcombat.blocks;

import java.util.Random;

import com.kitchen.parkourcombat.entity.tileentity.GravityTile;
import com.kitchen.parkourcombat.entity.tileentity.RubyBlockTile;
import com.kitchen.parkourcombat.init.ModItems;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GravityOre extends BlockBase implements ITileEntityProvider {
	
	Random r = new Random();
	
	private int charge = 0;
	private static final int MAX_CHARGE = 20;
	
	public GravityOre(String name, Material material) {
		super(name, material);
		
		setHardness(3.0F);
		setResistance(3.0F);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(0.0F);
		//this.setTickRandomly(true);
	}
	
	/**
     * Called throughout the code as a replacement for block instanceof BlockContainer
     * Moving this to the Block base class allows for mods that wish to extend vanilla
     * blocks, and also want to have a tile entity on that block, may.
     *
     * Return true from this function to specify this block has a tile entity.
     *
     * @param state State of the current block
     * @return True if block has a tile entity, false otherwise
     */
	@Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
	

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		GravityTile tile = new GravityTile(.003F, 40); //.002
		return tile;
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
		
		/*
		byte[] bytes = new byte[1];
		r.nextBytes(bytes);
		System.out.println(bytes[0]);
		if (bytes[0] < -20) {
			return 1;
		} else {
			return 0;
		}
		*/
    }
	
	
}
