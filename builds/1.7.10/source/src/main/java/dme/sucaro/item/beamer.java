package dme.sucaro.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
	
	public void onUpdate(ItemStack s, World w, Entity p, int i, boolean bool) {
		//((EntityLivingBase)p).addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20, 1));
		((EntityLivingBase)p).addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 20, 1));
		((EntityLivingBase)p).addPotionEffect(new PotionEffect(Potion.resistance.getId(), 20, 1));
	}
	
}
