package com.kitchen.parkourcombat.items.tools;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.entity.CustomTNT;
import com.kitchen.parkourcombat.items.ItemBase;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBaseDetonator extends ItemBase implements IHasModel {

	private int fuse;

	private static final int MAX_COUNTS = 20;
	private short counts = MAX_COUNTS;

	public ItemBaseDetonator(String name, int fuse) {
		super(name);
		this.fuse = fuse;
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	private int getFuse(ItemStack stack) {
    	String key = "fuse";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setInteger(key, this.fuse);
    	}
    	return nbt.getInteger(key);
    }
    
    /** 
     * should only be called after init
     * **/
    private void setFuse(ItemStack stack, int n) {
    	String key = "fuse";
    	NBTTagCompound nbt = stack.getTagCompound();
    	nbt.setInteger(key, n);
    	stack.setTagCompound(nbt);
    }

	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to
	 * check if is on a player hand and update it's contents.
	 */
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.getTagCompound() == null) { //stack.getTagCompound().hasNoTags())
			NBTTagCompound nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		this.setFuse(stack, this.getFuse(stack) - 1);
		if (this.getFuse(stack) == 1) {
			// detonate
			CustomTNT tnt = new CustomTNT(worldIn, entityIn.posX, entityIn.posY, entityIn.posZ,
					(EntityLivingBase) entityIn, 8.0F);
			tnt.setFuse(10);
			worldIn.spawnEntity(tnt);
			stack.setCount(0);
		} else {
			if (counts != 0) {
				--counts;
				return;
			} else {
				counts = MAX_COUNTS;
			}
			if (this.getFuse(stack) < 20) {
				// final warning
				worldIn.playSound((EntityPlayer) null, entityIn.posX, entityIn.posY, entityIn.posZ,
						SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.PLAYERS, 1.0F,
						1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
			} else {
				// let the player know it will explode
				worldIn.playSound((EntityPlayer) null, entityIn.posX, entityIn.posY, entityIn.posZ,
						SoundEvents.ENTITY_CREEPER_HURT, SoundCategory.PLAYERS, 1.0F,
						1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
			}

		}

	}
}