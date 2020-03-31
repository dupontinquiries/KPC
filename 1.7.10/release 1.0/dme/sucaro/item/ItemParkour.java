package dme.sucaro.item;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemParkour extends ItemSword {
	
	private boolean ripped;
	
	private double d = 0.5;

	private byte glide;
	
	private byte maxCooldown = 15;
	private byte cooldown = maxCooldown;
	
	private byte maxCharge = 25;
	private byte charge = maxCharge;
	
	private byte maxWallLeap = 35;
	private byte wallLeap = maxWallLeap;
	
	public static Random rand = new Random();
	
	public ItemParkour(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
		ripped = false;
	}

	public ItemStack onItemRightClick(ItemStack s, World w, EntityPlayer p) {
		MovingObjectPosition mop = ((EntityLivingBase) p).rayTrace(16, 1.0f);
		if (cooldown == 0 && !(p.isInWater() || p.capabilities.isFlying)) {
			if (p.isAirBorne && charge == 0 && glide > 1) {
				p.addVelocity((mop.blockX - p.posX) / 25, /* ((mop.blockY - p.posY) / 35) + */ 0.8,
						(mop.blockZ - p.posZ) / 25);
				wallLeap = maxWallLeap;
				charge = maxCharge;
			} else {
				p.addVelocity((mop.blockX - p.posX) / 55, .21,
						(mop.blockZ - p.posZ) / 55);
			}
			//p.addVelocity((mop.blockX - p.posX) / 55, .21,
			//		(mop.blockZ - p.posZ) / 55); //35, .4
			cooldown = maxCooldown;
		}
        
        
        
        /*
		glide = false;
		if (w.getBlock(i + 1, j + 1, k) != Blocks.air) {
			++glide;
			System.out.println("G...");
			w.setBlock(i + 1, j + 1, k, Blocks.stone);
		}
		else if (w.getBlock(i - 1, j + 1, k) != Blocks.air) {
			++glide;
			System.out.println("G...");
			w.setBlock(i - 1, j + 1, k, Blocks.cobblestone);
		}
		else if (w.getBlock(i, j + 1, k + 1) != Blocks.air) {
			++glide;
			System.out.println("I...");
			w.setBlock(i, j + 1, k + 1, Blocks.obsidian);
		}
		else if (w.getBlock(i, j + 1, k - 1) != Blocks.air) {
			++glide;
			System.out.println("D...");
			w.setBlock(i, j + 1, k - 1, Blocks.bedrock);
		}
		if (glide) {
			op1(s, w, p, i); //), b);
		}
		*/
		return s;
	}

	public void onUpdate(ItemStack s, World w, Entity p, int _i, boolean b) {
		if (cooldown > 0) {
			--cooldown;
		}
		if (wallLeap > 0) {
			--wallLeap;
		}
		glide = 0;
		int i = MathHelper.floor_double(p.posX);
        int j = MathHelper.floor_double(p.posY - 0.20000000298023224D - (double)p.yOffset);
        int k = MathHelper.floor_double(p.posZ);
        if (w.getBlock(i + 1, j + 1, k) != Blocks.air) {
			++glide;
		}
      //2nd level
        	if (w.getBlock(i + 1, j + 2, k) != Blocks.air) {
        		++glide;
        	}
		if (w.getBlock(i - 1, j + 1, k) != Blocks.air) {
			++glide;
		}
		//2nd level
			if (w.getBlock(i - 1, j + 2, k) != Blocks.air) {
	    		++glide;
	    	}
		if (w.getBlock(i, j + 1, k + 1) != Blocks.air) {
			++glide;
		}
		//2nd level
			if (w.getBlock(i, j + 2, k + 1) != Blocks.air) {
				++glide;
			}
		if (w.getBlock(i, j + 1, k - 1) != Blocks.air) {
			++glide;
		}
		//2nd level
			if (w.getBlock(i, j + 2, k - 1) != Blocks.air) {
				++glide;
			}
		if (glide > 0 && p.isAirBorne && w.getBlock(i, j, k) == Blocks.air) {
			op1(s, w, p, i, b);
			if (charge > 0) {
				if (mag(p.motionX, p.motionY, p.motionZ) < 0.3) {
					--charge;
				} else {
					charge = maxCharge;
				}
			}
		} else {
			if (wallLeap > 0 && p.isAirBorne) {
				MovingObjectPosition mop = ((EntityLivingBase) p).rayTrace(16, 1.0f);
				//p.addVelocity((mop.blockX - p.posX) / 205, ((mop.blockY - p.posY) / 205) + .05, (mop.blockZ - p.posZ) / 205);
				p.addVelocity(0, .02, 0);
			}
		}
	}

	private double mag(double motionX, double motionY, double motionZ) {
		return Math.pow((Math.pow(motionX, 2) + Math.pow(motionY, 2) + Math.pow(motionZ, 2)), .5);
	}
	
	private int getSign(double n) {
		if (n == 0){
			return 0;
		} else if (n > 0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private void op1(ItemStack s, World w, Entity p, int i, boolean b) {
		EntityPlayer player = (EntityPlayer) p;
		if (p.isInWater() || player.capabilities.isFlying) { // || mag(p.motionX, p.motionY, p.motionZ) > 1.4
			
		}
		else {
			
			if (player.getHeldItem() == s) {
				p.addVelocity(0, .04, 0); //.03, .036
				if (p.isSneaking()) {
					p.addVelocity(0, .02, 0);
				}
				if (mag(p.motionX, p.motionY, p.motionZ) < 0.5) {
					p.addVelocity(0, .01, 0);
				}
				
				if (p.fallDistance > 3) {
					--p.fallDistance;
				}
			}
			
			//p.addVelocity(p.motionX * 0.1, -(p.motionY), p.motionZ * 0.1);
		
		}
	}
}
