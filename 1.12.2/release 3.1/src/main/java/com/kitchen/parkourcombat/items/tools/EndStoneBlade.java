package com.kitchen.parkourcombat.items.tools;

import java.util.Random;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EndStoneBlade extends LegendaryBladeBase implements IHasModel {
	
	private short fallCounts = 5;
	
	private short jumpCounts = 0;
	private short jumpTimer;

	public EndStoneBlade(String name, ToolMaterial material, String type, short mobLife, short mobTimer, short jumpTimer)
	{
		super(name, material, type, mobLife, mobTimer, ModItems.CHARGED_LIFELESS_END_STONE_BLADE);
		
		this.jumpTimer = jumpTimer;
		
		ModItems.ITEMS.add(this);
	}
	//
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
    	super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    	
		entityIn.fallDistance = 0;
		
		if (jumpCounts > 1) {
			--jumpCounts;
			RayTraceResult mop = ((EntityLivingBase) entityIn).rayTrace(16, 1.0f);
			entityIn.addVelocity((mop.hitVec.x - entityIn.posX) / 700, 0,
					(mop.hitVec.z - entityIn.posZ) / 700);
		}
		if (jumpCounts > 0) {
			--jumpCounts;
			entityIn.addVelocity(0, 0.076, 0);
			if (fallCounts > 0) {
				--fallCounts;
			} else {
				fallCounts = 5;
			}
			
		}
		if (jumpCounts == 1 && entityIn.onGround) {
			--jumpCounts;
		}
    }
	
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, EntityLivingBase t, EntityLivingBase e)
    {
    	super.hitEntity(stack, t, e);
        stack.damageItem(1, e);
        
        return true;
    }
	
	/**
     * Called by the default implemetation of EntityItem's onUpdate method, allowing for cleaner
     * control over the update of the item without having to write a subclass.
     *
     * @param entityItem The entity Item
     * @return Return true to skip any further update code.
     */
	@Override
    public boolean onEntityItemUpdate(net.minecraft.entity.item.EntityItem entityItem)
    {
        return false;
    }
	
	/**
     * Called when the player Left Clicks (attacks) an entity.
     * Processed before damage is done, if return value is true further processing is canceled
     * and the entity is not attacked.
     *
     * @param stack The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer playerIn, Entity t)
    {
		super.onLeftClickEntity(stack, playerIn, t);
		return false;
    }
	
	/**
     * Called when the equipped item is right clicked.
     */
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		super.onItemRightClick(worldIn, playerIn, handIn);
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
    	if (jumpCounts != -1 && jumpCounts == 0 && playerIn.isAirBorne) {
    		jumpCounts = jumpTimer;
    	}
    	
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
