package com.kitchen.parkourcombat.entity.tileentity;

import java.util.List;

import com.kitchen.parkourcombat.init.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class GravityTile extends TileEntity implements ITickable {
	
	private float strength;
	private int reach;
	
	private short counts = 0;
	
	public GravityTile(float strength, int reach) {
		super();
		this.strength = strength;
		this.reach = reach;
		this.markDirty();
	}
	
	// math functions
	
	private double magVelE(Entity a) {
		return Math.pow((Math.pow(a.motionX, 2) + Math.pow(a.motionY, 2) + Math.pow(a.motionZ, 2)), .5);
	}
	
	private double magDist3DT(GravityTile a, Entity b) {
		return Math.pow((Math.pow(a.getPos().getX() - b.posX, 2) + Math.pow(a.getPos().getY() - b.posY, 2) + Math.pow(a.getPos().getZ() - b.posZ, 2)), .5);
	}
	
	private double magDist3DE(Entity a, Entity b) {
		return Math.pow((Math.pow(a.posX - b.posX, 2) + Math.pow(a.posY - b.posY, 2) + Math.pow(a.posZ - b.posZ, 2)), .5);
	}

	// trap trigger
	
	@Override
	public void update() {
		if (counts != 4) {
			++counts;
			return;
		} else {
			counts = 0;
		}
		
		int x = pos.getX(), y = pos.getY(), z = pos.getZ();
		
		AxisAlignedBB bb = new AxisAlignedBB(new BlockPos(x - reach, y - reach, z - reach),
											 new BlockPos(x + reach, y + reach, z + reach));
		List entities = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, bb);
		
		for (int i = 0; i < entities.size(); ++i) {
			if (entities.get(i) == null) {
				continue;
			}
			if (entities.get(i) instanceof TileEntity) {
				continue;
			}
			if (entities.get(i) instanceof EntityLivingBase) {
				EntityLivingBase b = (EntityLivingBase) entities.get(i);
				if (entities.get(i) instanceof EntityPlayer) {
					if (	((EntityPlayer) b).inventory.hasItemStack(new ItemStack(ModItems.HIGGS_STAFF))) {
						continue;
					}
				}
				if (magDist3DT(this, b) > reach) {
					continue;
				}
				double mag = magDist3DT(this, b);
				int x1 = b.getPosition().getX(), y1 = b.getPosition().getY(), z1 = b.getPosition().getZ();
				b.addVelocity(strength * MathHelper.clamp((x - x1) / mag, -3, 3),
							  strength * MathHelper.clamp((y - y1) / mag, -3, 3),
							  strength * MathHelper.clamp((z - z1) / mag, -3, 3));
				b.fallDistance += 0;
			}
		}
	}
	
}
