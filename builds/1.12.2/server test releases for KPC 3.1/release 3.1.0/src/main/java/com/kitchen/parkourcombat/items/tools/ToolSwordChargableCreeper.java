package com.kitchen.parkourcombat.items.tools;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ToolSwordChargableCreeper extends ToolSwordChargable {

	private boolean next;
	
	public ToolSwordChargableCreeper(String name, ToolMaterial material, boolean next) {
		super(name, material);
		this.next = next;
	}
	
	@Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		String tt = "Attack creepers with this item to return it to full strength";
		if (next) {
			tt += ", but be wary because this item will become highly unstable";
		}
		tt += "!";
        tooltip.add(tt);
    }
	
	@Override
	public void repair(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target instanceof EntityCreeper) {
			stack.damageItem(-4, attacker);
		} else {
			attacker.attackEntityFrom(DamageSource.MAGIC, 1);
		}
	}

}
