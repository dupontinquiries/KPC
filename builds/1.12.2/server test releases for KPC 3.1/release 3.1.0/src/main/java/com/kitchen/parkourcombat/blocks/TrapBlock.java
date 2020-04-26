package com.kitchen.parkourcombat.blocks;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.entity.tileentity.TrapBlockTile;
import com.kitchen.parkourcombat.init.ModBlocks;
import com.kitchen.parkourcombat.init.ModItems;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TrapBlock extends BlockBase implements ITileEntityProvider {
	
	private int triggerRadius = 0;

	public TrapBlock(String name, Material material, int triggerRadius) {
		super(name, material);
		this.triggerRadius = triggerRadius;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		TrapBlockTile tile = new TrapBlockTile(worldIn, triggerRadius);
		return tile;
	}
	
	
}
