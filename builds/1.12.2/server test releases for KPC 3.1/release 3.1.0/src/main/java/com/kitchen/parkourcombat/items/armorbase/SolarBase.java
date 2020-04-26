package com.kitchen.parkourcombat.items.armorbase;

import java.util.List;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModBlocks;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolarBase extends ItemArmor implements IHasModel {

	public SolarBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.COMBAT);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return toRepair.isItemDamaged();
    }

	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	public ItemStack getRepairItemStack()
    {
        Item ret = Item.getItemFromBlock(ModBlocks.RUBY_BLOCK);
        ItemStack repairMaterial = new ItemStack(ret,2);
        return repairMaterial;
    }
	
	@Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Photosynthesis: this armor will repair in the sunlight, but it will not fare well in dark places...");
        super.addInformation(stack, player, tooltip, advanced);
    }

}