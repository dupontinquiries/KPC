package com.kitchen.parkourcombat.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FoodEffectTime extends FoodBase {
	
	private long ticks;

	public FoodEffectTime(String name, int amount, float saturation, boolean isAnimalFood, long ticks) {
		super(name, amount, saturation, isAnimalFood);
		this.ticks = ticks;
	}
	

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		if(!worldIn.isRemote) {
			for(int i = 0; i < ticks; i += 5)
				worldIn.setWorldTime(worldIn.getWorldTime() + 5);
		}
	}

}
