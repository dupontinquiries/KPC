package com.kitchen.parkourcombat.items.gun;

import javax.annotation.Nullable;

import com.kitchen.parkourcombat.entity.projectile.EntityBullet;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.items.ItemBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Trees.This;

public class GunBase3 extends ItemBase
{
	
	//properties
	private Item ammo = ModItems.BULLET;
	private final float bulletVelocity;
	private final int bulletPeriod, magazineSize;
	private final float bulletForce;
	private final boolean isFlamethrower;
	
	//states
	//private boolean isFiring = false, isReloading = false;
		//maxes
	private static final int RELOAD_TICKS = 40; //GUN_TICKS = 20,
		//tickables
	private int reloadTicks = RELOAD_TICKS; //, bulletPeriodTicks; //gunTicks = GUN_TICKS,
		private int currentBullets;
		private int maxUse;
	
	
    public GunBase3(String string, float bulletVelocity, 
    			   int bulletPeriod, int magazineSize,
    			   float bulletForce, boolean isFlamethrower)
    {
    	super(string);
    	
    	this.bulletVelocity = bulletVelocity;
    	this.bulletPeriod = bulletPeriod;
    		//this.bulletPeriodTicks = bulletPeriod;
    		
    	this.magazineSize = magazineSize;
    	//this.currentMagazine = magazineSize;
    	this.bulletForce = bulletForce;
    	this.isFlamethrower = isFlamethrower;
    	
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return !(entityIn.getActiveItemStack().getItem() instanceof GunBase3) ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    protected ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isAmmo(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isAmmo(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isAmmo(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    // modified from bow
    protected boolean isAmmo(ItemStack stack)
    {
        return stack.getItem() == this.ammo;
    }
    
    private void fire(ItemStack stack, EntityLivingBase entityLiving) {
    	World worldIn = entityLiving.world;
    	if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo(entityplayer);

            //int i = this.getMaxItemUseDuration(stack) - timeLeft;
            //i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
            //if (i < 0) return;

            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(this.ammo);
                }

                float f = getArrowVelocity(itemstack);
                //float f = this.bulletVelocity;

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() == this.ammo && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    if (!worldIn.isRemote)
                    {
                        Item itemammo = (Item)(itemstack.getItem() == this.ammo ? itemstack.getItem() : this.ammo);
                        EntityBullet bullet = new EntityBullet(worldIn, itemstack, entityplayer);//itemarrow.createArrow(worldIn, itemstack, entityplayer);
                        bullet = this.customizeBullet(bullet);
                        bullet.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                        if (f == 1.0F)
                        {
                            bullet.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

                        if (j > 0)
                        {
                            bullet.setDamage(bullet.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        bullet.setKnockbackStrength(this.bulletForce);

                        if (this.isFlamethrower)
                        {
                            bullet.setFire(100);
                        }

                        stack.damageItem(1, entityplayer);

                        if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
                        {
                        	//bullet.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        	bullet.pickupStatus = EntityBullet.PickupStatus.CREATIVE_ONLY;
                        	//bullet.setPickupStatus(EntityBullet.PickupStatus.CREATIVE_ONLY);
                        }

                        worldIn.spawnEntity(bullet);
                    }

                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_FIREWORK_BLAST, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }

                    entityplayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }
    
    /**
     * Called each tick while using an item.
     * @param stack The Item being used
     * @param entityLiving The Player using the item
     * @param count The amount of time in tick the item has been used for continuously
     */
    public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count)
    {
    	this.setMag(stack, this.getMag(stack) - 1);
    	this.fire(stack, entityLiving);    	
    }
    
    private int getMag(ItemStack stack) {
    	String key = "mag";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setInteger(key, this.magazineSize);
    	}
    	return nbt.getInteger(key);
    }
    
    /** 
     * should only be called after init
     * **/
    private void setMag(ItemStack stack, int n) {
    	String key = "mag";
    	NBTTagCompound nbt = stack.getTagCompound();
    	nbt.setInteger(key, n);
    	stack.setTagCompound(nbt);
    }
    
    private int getCap(ItemStack stack) {
    	String key = "cap";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setInteger(key, this.magazineSize);
    	}
    	return nbt.getInteger(key);
    }
    
    private boolean getFiring(ItemStack stack) {
    	String key = "isFir";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setBoolean(key, false);
    	}
    	return nbt.getBoolean(key);
    }
    
    // fire rate
    private int getPeriod(ItemStack stack) {
    	String key = "per";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setInteger(key, this.bulletPeriod);
    	}
    	return nbt.getInteger(key);
    }
    
    private int getFireTicks(ItemStack stack) {
    	String key = "fTicks";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setInteger(key, 0);
    	}
    	return nbt.getInteger(key);
    }
    
    /** 
     * should only be called after init
     * **/
    private void setFireTicks(ItemStack stack, int n) {
    	String key = "fTicks";
    	NBTTagCompound nbt = stack.getTagCompound();
    	nbt.setInteger(key, n);
    	stack.setTagCompound(nbt);
    }
    
    /** 
     * should only be called after init
     * **/
    private void setFiring(ItemStack stack, boolean f) {
    	String key = "isFir";
    	NBTTagCompound nbt = stack.getTagCompound();
    	nbt.setBoolean(key, f);
    	stack.setTagCompound(nbt);
    }
    
    private int getReloadTime(ItemStack stack) {
    	String key = "relTime";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setInteger(key, this.RELOAD_TICKS);
    	}
    	return nbt.getInteger(key);
    }
    
    private boolean getReload(ItemStack stack) {
    	String key = "isRel";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setBoolean(key, false);
    	}
    	return nbt.getBoolean(key);
    }
    
    /** 
     * should only be called after init
     * **/
    private void setReload(ItemStack stack, boolean r) {
    	String key = "isRel";
    	NBTTagCompound nbt = stack.getTagCompound();
    	nbt.setBoolean(key, r);
    	stack.setTagCompound(nbt);
    }
    
    private int getReloadTick(ItemStack stack) {
    	String key = "relTicks";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setInteger(key, this.RELOAD_TICKS);
    	}
    	return nbt.getInteger(key);
    }
    
    /** 
     * should only be called after init
     * **/
    private void setReloadTick(ItemStack stack, int n) {
    	String key = "relTicks";
    	NBTTagCompound nbt = stack.getTagCompound();
    	nbt.setInteger(key, n);
    	stack.setTagCompound(nbt);
    }
    
    private boolean getFlame(ItemStack stack) {
    	String key = "isFlame";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setBoolean(key, this.isFlamethrower);
    	}
    	return nbt.getBoolean(key);
    }
    
    
    
    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if (stack.getTagCompound() == null) { //stack.getTagCompound().hasNoTags())
    		NBTTagCompound nbt = new NBTTagCompound();
    		stack.setTagCompound(nbt);
    	}
    	
    	this.currentBullets = this.getMag(stack);
    	this.maxUse = 7200; //((this.getFireTicks(stack) - 1)* this.getCap(stack)) + 1;
    }
    
    /**
     * Allow the item one last chance to modify its name used for the
     * tool highlight useful for adding something extra that can't be removed
     * by a user in the displayed name, such as a mode of operation.
     *
     * @param item the ItemStack for the item.
     * @param displayName the name that will be displayed unless it is changed in this method.
     */
    public String getHighlightTip( ItemStack stack, String displayName )
    {
    	return "[" + this.currentBullets + "] " + displayName;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    // modified from bow
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
    	this.setFiring(stack, false);
    	System.out.println("\nStoppedUsing \nStoppedUsing \nStoppedUsing \nStoppedUsing");
    }
    
    /*
    private float getVelocity(ItemStack stack) {
    	String key = "vel";
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null || !nbt.hasKey(key)) {
    		nbt.setFloat(key, this.bulletVelocity);
    	}
    	return nbt.getFloat(key);
    }
    */
    
    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    // modified from bow
    public float getArrowVelocity(ItemStack stack)
    {
        return this.bulletVelocity;
    }

    /**
     * How long it takes to use or consume an item
     */
    // modified from bow
    public int getMaxItemUseDuration(ItemStack stack)
    {
        //return 72000;
        return this.maxUse;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    // modified from bow
    public EnumAction getItemUseAction(ItemStack stack, EntityPlayer p)
    {
    	return EnumAction.BOW;
    	/*
    	if ( p.isSneaking() && p.onGround ) { // || playerVelocity(p) < 1) {
    		return EnumAction.BOW;
    	} else {
    		return EnumAction.NONE;
    	}
    	*/
    }

    // get motion of shooter
    private float playerVelocity(EntityPlayer p) {
		return MathHelper.sqrt(Math.pow(p.motionX, 2) + Math.pow(p.motionY, 2) + Math.pow(p.motionZ, 2));
	}

	/**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if (!worldIn.isRemote) {
        	ItemStack stack = playerIn.getHeldItem(handIn);
        	if (this.getReloadTick(stack) == this.RELOAD_TICKS && this.getMag(stack) > 0) {
        		this.setFiring(stack, true);
        		this.setFireTicks(stack, this.getFireTicks(stack));
        	} else {
        		this.setFiring(stack, false);
        	}
        }
    	
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !this.findAmmo(playerIn).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.capabilities.isCreativeMode && !flag)
        {
            return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        else
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 1;
    }

    public EntityBullet customizeBullet(EntityBullet bullet)
    {
        return bullet;
    }
}
