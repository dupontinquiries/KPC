package com.kitchen.parkourcombat.items.tools;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ToolSwordChargableInWater extends ToolSwordChargable {

	public ToolSwordChargableInWater(String name, ToolMaterial material) {
		super(name, material);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Attack swimming mobs to return this item to full strength!");
    }
	
	@Override
	public void repair(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target.isInWater()) {
			stack.damageItem(-4, attacker);
		} else {
			attacker.attackEntityFrom(DamageSource.MAGIC, 1);
		}
	}

}
