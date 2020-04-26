package dme.sucaro.entity;

import java.util.List;
import java.util.Random;

import dme.sucaro.item.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGravity extends TileEntity {
	

	private int radius = 40;
	private World world;
	private Random r;
	private short counts = 0;
	boolean dmg_player = true;
	
	//p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
	
	private double chillFactor = 0; //.003; // .013
	private double multiplier = 1.0;

	/**
	 * 
	 * @param world
	 * @param posX
	 * @param r
	 */
	public EntityGravity(World world, Random r) {
		super();
		//System.out.println("  [" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + "] Entity constructor called!");
		this.worldObj = world;
		this.r = r;
		//System.out.println(" super");
		
		/*
		if (this.nameToClassMap.containsKey("EntityGravity")) {
			this.nameToClassMap.put("EntityGravity", EntityGravity.class);
		}
		
		if (this.classToNameMap.containsKey(EntityGravity.class)) {
			this.classToNameMap.put(EntityGravity.class, "EntityGravity");
		}
		*/
		
		//NBTTagCompound tag ;
		//this.writeToNBT();
		

		// this.setPosition( posX, posY, posZ );
		// this.setSize( 0, 0 );
	}

	/**
	 *
	 * @param number
	 * @return sign value
	 */
	private int getSign(double n) {
		if (n == 0) {
			return 0;
		} else if (n > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	private double magDist3D(double a, double b, double c, double x, double y, double z) {
		return Math.pow((Math.pow(a - x, 2) + Math.pow(b - y, 2) + Math.pow(c - z, 2)), .5);
	}
	
	private double magDistCubed(double a, double b, double c, double x, double y, double z) {
		return Math.pow((Math.abs(Math.pow(a - x, 3)) + Math.abs(Math.pow(b - y, 3)) + Math.abs(Math.pow(c - z, 3))), .33);
	}

	private double magDist3D__E(Entity a, Entity b) {
		return Math.pow((Math.pow(a.posX - b.posX, 2) + Math.pow(a.posY - b.posY, 2) + Math.pow(a.posZ - b.posZ, 2)), .5);
	}
	
	private double magVelE(Entity a) {
		return Math.pow((Math.pow(a.motionX, 2) + Math.pow(a.motionY, 2) + Math.pow(a.motionZ, 2)), .5);
	}
	
	private double magDist3D__E(EntityGravity a, Entity b) {
		return Math.pow((Math.pow(a.xCoord - b.posX, 2) + Math.pow(a.yCoord - b.posY, 2) + Math.pow(a.zCoord - b.posZ, 2)), .5);
	}

	
	private double mag(double motionX, double motionY, double motionZ) {
		return Math.pow((Math.pow(motionX, 2) + Math.pow(motionY, 2) + Math.pow(motionZ, 2)), .5);
	}
	
	/**
	 * Called to update the entity's position/logic.
	 */
	public void updateEntity() {
		this.multiplier = 1.0;
		this.chillFactor = 0.00114;
		// particle effects
		this.worldObj.spawnParticle("smoke", this.xCoord, this.yCoord + 0.5D, this.zCoord, r.nextDouble() * 0.1, r.nextDouble() * 0.1, r.nextDouble() * 0.1);
		// attraction bounds
		AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(this.xCoord - radius, this.yCoord - radius, this.zCoord - radius,
				this.xCoord + radius, this.yCoord + radius, this.zCoord + radius);
		
		// counts
		if (counts == 60) {
			counts = 0;
		} else {
			++counts;
		}
		
		//apply effects to each entity in range
		List boundingList = this.worldObj.getEntitiesWithinAABB(Entity.class, axis);
		int g = 0;
		int group = 0;
		
		for (g = 0; g < boundingList.size(); ++g) {
		
			if (boundingList.get(g) instanceof EntityGravity) {
				multiplier *= 0.6;
				++group;
			}
			
		}
		
		/*
		if (group > 7 && r.nextDouble() < .01) {
			int tmpRadius = 5;
			world.playSound(xCoord, yCoord, zCoord, "fireworks.largeBlast", 1.0f, 1.0f, false);
			EntityCreeper creeper = new EntityCreeper(world);
			creeper.posX = xCoord + r.nextInt(tmpRadius);
			creeper.posY = yCoord + r.nextInt(tmpRadius);
			creeper.posZ = xCoord + r.nextInt(tmpRadius);
			world.spawnEntityInWorld(creeper);
		}
		*/
		
		for (g = 0; g < boundingList.size(); ++g) {
			
			/*
			if (boundingList.get(g) instanceof EntityGravity ){
				if (r.nextDouble() < multiplier) {
					multiplier *= 0.6;
					continue;
				} else {
					multiplier *= 0.3;
				}
			}
			*/
			
			
			if (boundingList.get(g) instanceof TileEntity) {
				continue;
			}
			
			dmg_player = true;
			
			Entity elbf = (Entity) boundingList.get(g);
			elbf.motionY = elbf.motionY;
			
			if (magDist3D__E(this, elbf) > radius) {
				continue;
			}
			
			if (elbf instanceof EntityPlayer) {
				/*
				if (counts % 10 == 0) {
					System.out.println("-----  " + this.counts);
					System.out.println("  " + this.multiplier);
					System.out.println("  " + this.radius);
					System.out.println("  " + this.chillFactor);
					System.out.println("--------");
					//return;
				}
				*/
				EntityPlayer player = (EntityPlayer) elbf;
				if (player.inventory.hasItem(ItemRegistry.higgsStaff)) {
					continue;
				} else {
					if (player.isAirBorne) {
						chillFactor *= 1.05;
					} else if (player.isSneaking()) {
						chillFactor *= .95;
					}
				}
				// if creative mode, dont damage
				if (player.capabilities.isCreativeMode) {
					dmg_player = false;
				}

			}
			
			// damage entities
			if (counts >= 59) {
				if (elbf instanceof EntityLivingBase) {
					if (dmg_player) {
						if (r.nextDouble() < .3) {
							//System.out.println(" Tick Counts:" + counts);
						((EntityLivingBase) elbf).attackEntityFrom(DamageSource.magic, 1);
						}
					}
				}
			}
			
			double mult = (radius + 3 - magDist3D(elbf.posX, elbf.posY, elbf.posZ, this.xCoord, this.yCoord, this.zCoord)) / radius;

			// lift up falling blocks, boats, and minecarts
			if (this.magVelE(elbf) < 2 && ( elbf instanceof EntityMinecart || elbf instanceof EntityFallingBlock || elbf instanceof EntityBoat ) ) {
				if (elbf.isAirBorne && elbf.motionY < 0.0074) {
					elbf.addVelocity(0, 0.005, 0);
					elbf.fallDistance += 0;
				}
				mult *= 1.4;
			}
			
			// elbf.motionX += getSign(elbf.posX - this.xCoord) * -1 * mult * chillFactor;
			// elbf.motionY += getSign(elbf.posY - this.yCoord) * -1 * mult * chillFactor;
			// elbf.motionZ += getSign(elbf.posZ - this.zCoord) * -1 * mult * chillFactor;
			
			double _x = getSign(elbf.posX - this.xCoord) * -1 * mult * chillFactor * multiplier,
				   _y = getSign(elbf.posY - this.yCoord) * -1 * mult * chillFactor * multiplier,
				   _z = getSign(elbf.posZ - this.zCoord) * -1 * mult * chillFactor * multiplier;
			
			//elbf.setVelocity(elbf.motionX + _x,
			//		 elbf.motionY + _y,
			//		 elbf.motionZ + _z);
			
			// elbf.addVelocity(0,  0.005, 0);
			if (this.magVelE(elbf) < 2) {
				elbf.addVelocity(_x, 0, 0);
				elbf.fallDistance += 0;
				elbf.addVelocity(0, _y, 0);
				elbf.fallDistance += 0;
				elbf.addVelocity(0, 0, _z);
				elbf.fallDistance += 0;
			}
			// elbf.addVelocity(_x, _y, _z);	
			
			// elbf.velocityChanged = true;
			
			if (this.magVelE(elbf) < .6) {
				if (elbf.fallDistance > 0 )
					--elbf.fallDistance;
			}
		}
	}

}
