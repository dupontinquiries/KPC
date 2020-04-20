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
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class LegendaryBladeA extends ItemSword implements IHasModel {
	
	Random random = new Random();
	private EntityLivingBase mob;
	private EntityLivingBase target;
	private short counts = 0;
	private short timer;
	private String type;
	//private short counts = 0;

	public LegendaryBladeA(String name, ToolMaterial material, String type, short timer)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		this.timer = timer;
		this.type = type;
		
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
		if (counts > 0) {
			--counts;
			/*
			if (mob != null && !mob.isDead) {
				EntityPlayer player = (EntityPlayer) entityIn;
				if (!player.getLastAttackedEntity().isDead && player.getLastAttackedEntityTime() < timer) {
					((EntityLiving) mob).setAttackTarget(player.getLastAttackedEntity());
				}
	    	}
	    	*/
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
        if (counts == 0) {
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
			} else if (type == "spider") {
				mob = new EntitySpider(worldIn);
			} else if (type == "iron_golem") {
				mob = new EntityIronGolem(worldIn);
			} else {
				mob = new EntityWolf(worldIn);
			}
        	mob.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, (10*20), 2, false, true));
			mob.addPotionEffect(new PotionEffect(MobEffects.SPEED, (10*20), 1, false, true));
			mob.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (10*20), 2, false, true));
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
			counts = 400;
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
    	if (!playerIn.isInWater()) {
    		playerIn.addVelocity(0, .12, 0);
    	}
    	/*
        if (!playerIn.capabilities.isCreativeMode)
        {
        	playerIn.getActiveItemStack().damageItem(10, playerIn);
        }

        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote)
        {
        	
        	RayTraceResult mop = playerIn.rayTrace(16, 1.0f);
        		Entity target = mop.entityHit;
        		if (true || target instanceof EntityLivingBase) {
        			EntityLivingBase t = (EntityLivingBase) target;
        			EntityCreeper cp = new EntityCreeper(worldIn);
        			cp.setLocationAndAngles(playerIn.posX, playerIn.posY, playerIn.posZ, 0, 0);
        			cp.addVelocity(((mop.hitVec.x - playerIn.posX) / 35) + 0,
	        			((mop.hitVec.y - playerIn.posY) / 35) + 0,
	        			((mop.hitVec.z - playerIn.posZ) / 35) + 0);
        			cp.setAttackTarget(t);
            		System.out.println("Spawning\n\n");
            		worldIn.spawnEntity(cp);
            		t.attackEntityAsMob(cp);
            		cp.attackEntityAsMob(t);
            		if (!playerIn.capabilities.isCreativeMode)
    	        		itemstack.damageItem(130, playerIn);
        		}
	        	EntitySmallFireball fb = new EntitySmallFireball(worldIn, playerIn.posX, playerIn.posY + playerIn.getYOffset() + 1.4, playerIn.posZ, 
	        			((mop.hitVec.x - playerIn.posX) / 35) + 0,
	        			((mop.hitVec.y - playerIn.posY) / 35) + 0,
	        			((mop.hitVec.z - playerIn.posZ) / 35) + 0);
	        	worldIn.spawnEntity(fb);
	        	
        }
    */
    	
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
