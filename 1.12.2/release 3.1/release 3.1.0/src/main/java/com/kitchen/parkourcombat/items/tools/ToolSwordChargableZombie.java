package com.kitchen.parkourcombat.items.tools;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ToolSwordChargableZombie extends ToolSwordChargable {

	public ToolSwordChargableZombie(String name, ToolMaterial material) {
		super(name, material);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Attack mobs in the ocean to return this item to full strength!");
    }
	
	@Override
	public void repair(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target instanceof EntityZombie) {
			stack.damageItem(-4, attacker);
		} else {
			attacker.attackEntityFrom(DamageSource.MAGIC, 1);
		}
	}

}
