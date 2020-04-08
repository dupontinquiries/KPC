package com.kitchen.parkourcombat.entity.tileentity;

import com.kitchen.parkourcombat.entity.CustomTNT;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

public class ExplodingTrapTile extends TrapBlockTile {
	
	private float damage, ex;
	
	public ExplodingTrapTile(World worldObj, int triggerRadius, float ex, float damage) {
		super(worldObj, triggerRadius);
		this.ex = ex;
		this.damage = damage;
	}

	@Override
	public void trapTriggered(World world, EntityLivingBase b, int x, int y, int z) {
		
		if (!world.isRemote) {
			//Explosion e = new Explosion(world, b, x, y, z, damage, true, true);
			//e.doExplosionA();
			//e.doExplosionB(true);
			//b.attackEntityFrom(DamageSource.causeExplosionDamage(e), damage);
			//b.onLivingUpdate();
			CustomTNT tnt = new CustomTNT(world, x, y, z, b, 8.0F);
			tnt.setFuse(20);
			world.spawnEntity(tnt);
			super.trapTriggered(world, b, x, y, z);
		}
		//super.trapTriggered(world, b, x, y, z);
		//world.destroyBlock(new BlockPos(x, y, z), false);
		//Explosion e = new Explosion(world, b, x, y, z, damage, false, true);
		//world.createExplosion(b, x, y, z, ex, true);
		//b.setFire(10);
		//e.doExplosionA();
		//e.doExplosionB(true);
		//e.clearAffectedBlockPositions();
		//b.attackEntityFrom(DamageSource.causeExplosionDamage(e), damage);
	}

}
