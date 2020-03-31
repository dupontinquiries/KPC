package dme.sucaro.item;

import ibxm.Player;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import scala.reflect.internal.util.Set;

import java.util.Random;

public class ItemRipper extends ItemSword {
	
	private int maxNumBlinks;
	private int numBlinks = maxNumBlinks;
	
	private final int maxBlinkCooldown = 180;
	private int blinkCooldown = maxBlinkCooldown;
	
	// debounce
	private final byte maxDebounce = 1;
	private byte debounce = 0;
	
	private Random rand = new Random();

	public ItemRipper(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
		maxNumBlinks = 4;
	}
	
	public ItemRipper(ToolMaterial p_i45356_1_, int mnb) {
		super(p_i45356_1_);
		this.maxNumBlinks = mnb;
	}

	public ItemStack onItemRightClick(ItemStack s, World w, EntityPlayer p) {
		
		// debounce
		if (debounce != 0) {
			--debounce;
			return s;
		} else {
			debounce = maxDebounce;
		}
		// !debounce
		
		if (numBlinks == 0) {
			return s;
		}
		MovingObjectPosition mop = p.rayTrace(5, 1.0f);
		if (mop == null) {
			return s;
		}
		
		p.setLocationAndAngles(mop.blockX, mop.blockY + 1, mop.blockZ, p.getRotationYawHead(), p.rotationPitch);
		//p.velocityChanged = true;
		p.addVelocity(.05*getSign(p.motionX), .005, .05*getSign(p.motionZ));
		//p.velocityChanged = true;
		//blinkCooldown = maxBlinkCooldown;
		--numBlinks;
		System.out.println("~numBlinks: " + numBlinks);
		
		if (rand.nextDouble() < .05) {
			p.attackEntityFrom(DamageSource.drown, 8);
		}
			
		return s;
	}
	
	private double mag2D(double motionX, double motionZ) {
		return Math.pow((Math.pow(motionX, 2) + Math.pow(motionZ, 2)), .5);
	}
	
	private double mag3D(double motionX, double motionY, double motionZ) {
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
	
	public boolean hitEntity(ItemStack s, EntityLivingBase p1, EntityLivingBase p2) {
		if (s.getItemDamage() <= 1001) {
			p2.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 300, 1));
			//p2.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 400, 0));
			//p1.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 200, 0));
			p1.addPotionEffect(new PotionEffect(Potion.wither.getId(), 60, 0));
			s.setItemDamage(9990);
		}

		s.setItemDamage(s.getItemDamage() - 1000);
		return true;
	}

	public void onUpdate(ItemStack s, World w, Entity p, int i, boolean b) {
		//update blink cooldown
		if (numBlinks != maxNumBlinks && blinkCooldown > 0) {
			blinkCooldown--;
		}
		if (blinkCooldown == 0 && numBlinks < maxNumBlinks) {
			++numBlinks;
			System.out.println(" numBlinks: " + numBlinks);
			blinkCooldown = maxBlinkCooldown;
		}
		//charge function
		if (s.getItemDamage() <= 9990) {
			s.setItemDamage(s.getItemDamage() + 5);
		}
		//if(((EntityLivingBase) p).getActivePotionEffect(Potion.regeneration) != null){
		//	p.addVelocity(0, 0.02, 0);
		//}
		/*
		if (((EntityPlayer) p).getCurrentEquippedItem() == s && !p.isInWater() && p.motionY < -1) {
			p.addVelocity(0, 0.060, 0);
			if (p.fallDistance > 10) {
				p.fallDistance--;
			}
		}
		*/
		if (p.fallDistance > 2 && rand.nextDouble() < .10) {
			--p.fallDistance;
		}
		
	}
}
