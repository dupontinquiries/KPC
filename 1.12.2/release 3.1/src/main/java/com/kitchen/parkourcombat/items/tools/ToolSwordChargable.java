package com.kitchen.parkourcombat.items.tools;

import java.util.List;
import java.util.Random;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ToolSwordChargable extends ItemSword implements IHasModel {
	
	Random random = new Random();

	public ToolSwordChargable(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Attack mobs with this item to return it to full strength!");
        super.addInformation(stack, player, tooltip, advanced);
    }
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	/**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
	@Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		stack.setItemDamage((int) ((stack.getMaxDamage() * .9) - .5));
		if (stack.getItemDamage() < 5) {
			stack.setItemDamage(0);
		}
    }
	
	public void repair(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(-4, attacker);
	}
	
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        this.repair(stack, target, attacker);
        return true;
    }
}
