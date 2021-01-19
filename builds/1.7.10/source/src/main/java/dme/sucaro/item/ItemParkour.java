package dme.sucaro.item;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemParkour extends ItemSword {
	
	private boolean ripped;
	
	private double d = .6;
	
	public static Random rand = new Random();
	
	public ItemParkour(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
		ripped = false;
	}

	public ItemStack onItemRightClick(ItemStack s, World w, EntityPlayer p) {
		MovingObjectPosition mop = ((EntityLivingBase) p).rayTrace(20, 1.0f);
		if (p.getActivePotionEffect(Potion.confusion) == null) {
			p.addVelocity((mop.blockX - p.posX) / 50, .42,
					(mop.blockZ - p.posZ) / 50); //35, .4
			p.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 20, 0));
		}
		return s;
	}

	public void onUpdate(ItemStack s, World w, Entity p, int i, boolean b) {
		// MovingObjectPosition mop = ((EntityLivingBase) p).rayTrace(20, 1.0f);
		// p.addVelocity((mop.blockX-p.posX )/100, ((mop.blockY-p.posY )/1000)+
		// 0.034, (mop.blockZ-p.posZ )/100);
		if (w.getBlock((int) (p.posX + d), (int) (p.posY), (int) (p.posZ)) != Blocks.air) {
			//if (p.isSneaking()) {
				//if (p.isSneaking()) {
					op1(s, w, p, i, b);
				//}
				//p.addVelocity(p.motionX * 0.1, -(p.motionY), p.motionZ * 0.1);
				//Vec3 vec31 = Vec3.createVectorHelper(((double)rand.nextFloat() - 0.5D) * 0.3D, (double)(-rand.nextFloat()) * 0.6D - 0.3D, 0.6D);
				//vec31.rotateAroundZ(3 * (float)Math.PI / 180.0F);
			//}
		}
		else if (w.getBlock((int) (p.posX - d), (int) (p.posY), (int) (p.posZ)) != Blocks.air) {
			//if (p.isSneaking()) {
			op1(s, w, p, i, b);				
		//}
		}
		else if (w.getBlock((int) (p.posX), (int) (p.posY), (int) (p.posZ + d)) != Blocks.air) {
			//if (p.isSneaking()) {
				op1(s, w, p, i, b);
			//}
		}
		else if (w.getBlock((int) (p.posX), (int) (p.posY), (int) (p.posZ - d)) != Blocks.air) {
			//if (p.isSneaking()) {
				op1(s, w, p, i, b);				
			//}
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
		if (p.isInWater() || mag(p.motionX, p.motionY, p.motionZ) > 1.4 || player.capabilities.isFlying) {
			
		}
		else {
			
			//MovingObjectPosition mop = ((EntityLivingBase) p).rayTrace(5, 1.0f);
			if (player.getItemInUse() == s) {
				p.addVelocity(0, .033, 0); //.03, .036
			}
			
			if (p.fallDistance > 3) {
				float x = p.fallDistance;
				x--;
				p.fallDistance = x;
			}
			//p.addVelocity(p.motionX * 0.1, -(p.motionY), p.motionZ * 0.1);
		
		}
	}
}
