package dme.sucaro.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class beamer extends Item {
	
	public beamer(){
		this.maxStackSize = 1;
	    this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	private int ticks = 15;
	private float groundHealth = 20;
	
	public void onUpdate(ItemStack s, World w, Entity p, int i, boolean bool) {
		//((EntityLivingBase)p).addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20, 1));
		((EntityLivingBase)p).addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 20, 4));
		((EntityLivingBase)p).addPotionEffect(new PotionEffect(Potion.resistance.getId(), 20, 2));
		EntityPlayer player = ((EntityPlayer) p);
		groundHealth = player.getMaxHealth();
		if (player.getHeldItem() == s) {
			player.setInvisible(true);
			if (player.onGround) {
				ticks = 15;
				//IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
				//attribute.applyModifier(new AttributeModifier("Flight Buff", -1D * ticks, 0).setSaved(true));
			} else {
				if (ticks > 1) {
					player.addVelocity(0, 0.09 * Math.pow(2.72, ticks / 35.0) / 3.0 + .02, 0);
					//IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
					//attribute.applyModifier(new AttributeModifier("Flight Buff", (double) 1, 0).setSaved(true));
					--ticks;
					
				}
				player.fallDistance = 0;
			}
		} else {
			player.setInvisible(false);
		}
	}
	
}
