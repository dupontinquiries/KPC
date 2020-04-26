package com.kitchen.parkourcombat.entity.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TrapBlockTile extends TileEntity implements ITickable {
	
	private World worldObj;
	private int triggerRadius;
	
	private short counts = 0;
	
	public TrapBlockTile(World worldObj, int triggerRadius) {
		super();
		this.worldObj = world;
		this.triggerRadius = triggerRadius;
	}
	
	// math functions
	
	private double magVelE(Entity a) {
		return Math.pow((Math.pow(a.motionX, 2) + Math.pow(a.motionY, 2) + Math.pow(a.motionZ, 2)), .5);
	}
	
	private double magDist3DT(TrapBlockTile a, Entity b) {
		return Math.pow((Math.pow(a.getPos().getX() - b.posX, 2) + Math.pow(a.getPos().getY() - b.posY, 2) + Math.pow(a.getPos().getZ() - b.posZ, 2)), .5);
	}
	
	private double magDist3DE(Entity a, Entity b) {
		return Math.pow((Math.pow(a.posX - b.posX, 2) + Math.pow(a.posY - b.posY, 2) + Math.pow(a.posZ - b.posZ, 2)), .5);
	}

	// trap trigger
	
	@Override
	public void update() {
		if (counts != 10) {
			++counts;
			return;
		} else {
			counts = 0;
		}
		
		int x = pos.getX(), y = pos.getY(), z = pos.getZ();
		
		AxisAlignedBB bb = new AxisAlignedBB(new BlockPos(x - triggerRadius, y - triggerRadius, z - triggerRadius),
											 new BlockPos(x + triggerRadius, y + triggerRadius, z + triggerRadius));
		List entities = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, bb);
		
		for (int i = 0; i < entities.size(); ++i) {
			if (entities.get(i) == null) {
				continue;
			}
			if (entities.get(i) instanceof EntityPlayer) {
				continue;
			}
			if (entities.get(i) instanceof TileEntity) {
				continue;
			}
			if (entities.get(i) instanceof EntityLivingBase) {
				EntityLivingBase b = (EntityLivingBase) entities.get(i);
				if (magDist3DT(this, b) > triggerRadius) {
					continue;
				}
				this.trapTriggered(this.world, b, x, y, z);
				break;
			}
		}
	}

	protected void trapTriggered(World world, EntityLivingBase b, int x, int y, int z) {
		world.destroyBlock(new BlockPos(x, y, z), false);
	}
	
}
