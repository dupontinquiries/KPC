package com.kitchen.parkourcombat.items.tools;

import java.util.Random;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ToolSword extends ItemSword implements IHasModel {
	
	Random random = new Random();

	public ToolSword(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.COMBAT);
		
		ModItems.ITEMS.add(this);
	}
	//
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (random.nextDouble() < .1) {
        	target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 80, 1));
        	attacker.heal(3);
        } else {
        	stack.damageItem(1, attacker);
        }
        return true;
    }
	
	/**
     * Called when the equipped item is right clicked.
     */
	/*
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode)
        {
        	playerIn.getActiveItemStack().damageItem(30, playerIn);
        }

        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
        	RayTraceResult mop = playerIn.rayTrace(16, 1.0f);
        	int x = playerIn.getPosition().getX(), y = playerIn.getPosition().getY(), z = playerIn.getPosition().getZ();
        	EntitySmallFireball fb = new EntitySmallFireball(worldIn, x, y + playerIn.getYOffset(), z, 
        			((mop.hitVec.x - x) / 25) + 0,
        			((mop.hitVec.y - y) / 25) + 0,
        			((mop.hitVec.z - z) / 25) + 0);
        	worldIn.spawnEntity(fb);
        }
    	
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
    */
	
	public ItemStack getRepairItemStack()
    {
		Item ret = ModItems.RUBY;
        ItemStack repairMaterial = new ItemStack(ret,1);
        return repairMaterial;
    }
}
