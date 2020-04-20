package com.kitchen.parkourcombat.items.tools;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.items.ItemBase;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class ToolNSBlade extends ItemSword implements IHasModel {
	
	private static final int MAX_COUNTS = 20;
	private short counts = MAX_COUNTS;
	
	public ToolNSBlade(String name, ToolMaterial tool) {
		super(tool);
		setCreativeTab(CreativeTabs.COMBAT);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	/**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if (counts != 0) {
    		--counts;
    		return;
    	} else {
    		counts = MAX_COUNTS;
    	}
    	EntityPlayer player = (EntityPlayer) entityIn;
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
            if (i > 3) {
            	
            } else {
            	for (int k = 0; k < 5; ++k) {
            		if (stack.isItemDamaged()) {
                		stack.setItemDamage(stack.getItemDamage() - 1);
                	}
            	}
            }
        }
    }

}
