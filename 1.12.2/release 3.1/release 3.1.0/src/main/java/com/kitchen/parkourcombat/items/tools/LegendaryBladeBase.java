package com.kitchen.parkourcombat.items.tools;

import java.util.Random;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
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

public class LegendaryBladeBase extends ItemSword implements IHasModel {
	
	Random random = new Random();
	private EntityLivingBase mob;
	private EntityLivingBase target;
	
	private short mobTimerCounts = 0;
	private short mobTimer;
	
	private short mobLifeCounts = 0;
	private short mobLife;
	
	private String type;
	private Item repairItem;

	public LegendaryBladeBase(String name, ToolMaterial material, String type, short mobLife, short mobTimer, Item repairItem)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.COMBAT);
		
		this.type = type;
		
		this.mobTimer = mobTimer;
		
		this.mobLife = mobLife;
		
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
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		
		if (mob != null && !mob.isDead && mob.getRevengeTarget() == (EntityPlayer) entityIn) {
			mob.setDead();
    	}
		
		if (mobTimerCounts > 0) {
			--mobTimerCounts;
		}
		
		if (mobLifeCounts > 0) {
			--mobLifeCounts;
		} else {
			if (mob != null && !mob.isDead) {
				mob.setDead();
	    	}
		}
    }
	
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase t, EntityLivingBase e)
    {
        stack.damageItem(1, e);
        if (type != "none" && mobTimerCounts == 0) {
	        e.heal(2);
	        EntityPlayer playerIn = (EntityPlayer) e;
	        if (t instanceof EntityLivingBase == false) {
				return true;
			}
			World worldIn = playerIn.world;
			//EntityCreeper cp = new EntityCreeper(worldIn);
			if (mob != null && !mob.isDead) {
				mob.setDead();
        	}
			if (type == "enderman") {
				mob = new EntityEnderman(worldIn);
			} else if (type == "skeleton") {
				mob = new EntitySkeleton(worldIn);
			} else if (type == "creeper") {
				mob = new EntityCreeper(worldIn);
			} else if (type == "blaze") {
				mob = new EntityBlaze(worldIn);
			} else if (type == "spider") {
				mob = new EntitySpider(worldIn);
			} else if (type == "iron_golem") {
				mob = new EntityIronGolem(worldIn);
			} else {
				mob = new EntityWolf(worldIn);
			}
        	mob.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, (14*20), 2, false, true));
			mob.addPotionEffect(new PotionEffect(MobEffects.SPEED, (14*20), 1, false, true));
			mob.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (14*20), 2, false, true));
			mob.setLocationAndAngles(playerIn.posX, playerIn.posY, playerIn.posZ, 0, 0);
			mob.addVelocity(((t.posX - playerIn.posX) / 15) + 0,
				((t.posY - playerIn.posY) / 15) + 0,
				((t.posZ - playerIn.posZ) / 15) + 0);
			target = (EntityLivingBase) t;
			((EntityLiving) mob).setAttackTarget(target);
			System.out.println("Spawning\n\n");
			worldIn.spawnEntity(mob);
			target.attackEntityAsMob(mob);
			mob.attackEntityAsMob(t);
			mobTimerCounts = mobTimer;
			mobLifeCounts = mobLife;
    	}
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
		
		return false;
    }
	
	/**
     * Called when the equipped item is right clicked.
     */
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);    	
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
	
	public ItemStack getRepairItemStack()
    {
    	Item ret = this.repairItem;
        ItemStack repairMaterial = new ItemStack(ret,1);
        return repairMaterial;
    }
}
