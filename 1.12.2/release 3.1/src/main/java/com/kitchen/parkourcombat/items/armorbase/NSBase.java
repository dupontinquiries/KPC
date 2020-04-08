package com.kitchen.parkourcombat.items.armorbase;

import java.util.List;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModBlocks;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class NSBase extends ItemArmor implements IHasModel {

	private static final int MAX_COUNTS = 120;
	private short counts = MAX_COUNTS;
	
	public NSBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
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
        tooltip.add("Shadow Absorption: this armor will repair in the darkness, but it will not fare well in bright places...");
        super.addInformation(stack, player, tooltip, advanced);
    }
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
		if (counts != 0) {
    		--counts;
    		return;
    	} else {
    		counts = MAX_COUNTS;
    	}
		super.onArmorTick(world, player, stack);
		World worldIn = player.world;
		if (worldIn.provider.hasSkyLight())
        {
            IBlockState iblockstate = worldIn.getBlockState(player.getPosition());
            int i = worldIn.getLightFor(EnumSkyBlock.SKY, player.getPosition()) - worldIn.getSkylightSubtracted();
            float f = worldIn.getCelestialAngleRadians(1.0F);
            
            if (i > 0)
            {
            	float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
                f = f + (f1 - f) * 0.2F;
                i = Math.round((float)i * MathHelper.cos(f));
            }
            i = MathHelper.clamp(i, 0, 15);
            //System.out.println("   i = " + i);
            if (i > 3) {
            	
            } else {
            	for (int k = 0; k < 10; ++k) {
            		if (stack.isItemDamaged()) {
                		stack.setItemDamage(stack.getItemDamage() - 1);
                	}
            	}
            }
        }
    }

}