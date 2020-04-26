package com.kitchen.parkourcombat.items.food;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FoodEffectBase extends FoodBase
{

	PotionEffect[] effect;
	
	public FoodEffectBase(String name, int amount, float saturation, boolean isAnimalFood, PotionEffect[] effect)
	{
		super(name, amount, saturation, isAnimalFood);
		setAlwaysEdible();
		
		this.effect = effect;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		if(!worldIn.isRemote) {
			for (PotionEffect p : effect) {
				player.addPotionEffect(new PotionEffect(p.getPotion(), p.getDuration(), p.getAmplifier(), p.getIsAmbient(), p.doesShowParticles()));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
