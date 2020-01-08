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
	
	private short maxBlinkCooldown = 60;
	private short blinkCooldown = 0;

	public ItemRipper(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}

	public ItemStack onItemRightClick(ItemStack s, World w, EntityPlayer p) {
		if (blinkCooldown > 1) {
			return s;
		}
		MovingObjectPosition mop = p.rayTrace(5, 1.0f);
		if (mop != null) {
			final Random rand = new Random();
			if (rand.nextDouble() < .3) {
				p.attackEntityFrom(DamageSource.drown, 1);
			}
			p.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 30, 0));
			//p.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 30, 0));
			//p.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 30, 0));
			//p.addPotionEffect(new PotionEffect(Potion.weakness.getId(), 30, 0));
			p.setLocationAndAngles(mop.blockX, mop.blockY + 1, mop.blockZ, p.getRotationYawHead(), p.rotationPitch);
			p.addVelocity(.03*getSign(p.motionX), .01, .03*getSign(p.motionZ));
			p.fallDistance = 2;
			blinkCooldown = maxBlinkCooldown;
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
			//p1.addPotionEffect(new PotionEffect(Potion.poison.getId(), 20, 0));
			s.setItemDamage(9990);
		}

		s.setItemDamage(s.getItemDamage() - 1000);
		return true;
	}

	public void onUpdate(ItemStack s, World w, Entity p, int i, boolean b) {
		//update blink cooldown
		if (blinkCooldown > 0) {
			blinkCooldown--;
		}
		//charge function
		if (s.getItemDamage() <= 9990) {
			s.setItemDamage(s.getItemDamage() + 5);
		}
		if(((EntityLivingBase) p).getActivePotionEffect(Potion.regeneration) != null){
			p.addVelocity(0, 0.02, 0);
		}
		if (((EntityPlayer) p).getCurrentEquippedItem() == s && p.motionY < -1) {
			p.addVelocity(0, 0.060, 0);
			if (p.fallDistance > 10) {
				p.fallDistance--;
			}
		}
	}
}
