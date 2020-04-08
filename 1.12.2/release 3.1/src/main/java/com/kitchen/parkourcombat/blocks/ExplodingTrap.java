package com.kitchen.parkourcombat.blocks;

import com.kitchen.parkourcombat.entity.tileentity.ExplodingTrapTile;
import com.kitchen.parkourcombat.entity.tileentity.TrapBlockTile;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ExplodingTrap extends TrapBlock {
	
	private int triggerRadius;
	
	private float damage, ex;

	public ExplodingTrap(String name, Material material, int triggerRadius, float ex, float damage) {
		super(name, material, triggerRadius);
		setSoundType(SoundType.WOOD);
		setHardness(1.0F);
		setResistance(3.0F);
		setHarvestLevel("axe", 1);
		setLightLevel(0.0F);
		
		this.triggerRadius = triggerRadius;
		this.ex = ex;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		ExplodingTrapTile tile = new ExplodingTrapTile(worldIn, triggerRadius, ex, damage);
		return tile;
	}

}
