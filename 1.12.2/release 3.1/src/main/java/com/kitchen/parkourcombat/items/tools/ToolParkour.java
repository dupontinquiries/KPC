package com.kitchen.parkourcombat.items.tools;

import java.util.Random;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ToolParkour extends ItemSword implements IHasModel {

	public ToolParkour(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		
		ModItems.ITEMS.add(this);
		ripped = false;
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	private boolean ripped;
	
	private double d = 0.5;

	private byte glide;
	
	private byte maxCooldown = 35;
	private byte cooldown = maxCooldown;
	
	private byte maxCharge = 25;
	private byte charge = maxCharge;
	
	private byte maxWallLeap = 35;
	private byte wallLeap = maxWallLeap;
	
	public static Random rand = new Random();


	public ActionResult<ItemStack> onItemRightClick(World e, EntityPlayer p, EnumHand handIn) {
		RayTraceResult mop = ((EntityLivingBase) p).rayTrace(16, 1.0f);
		if (cooldown == 0 && !(p.isInWater() || p.capabilities.isFlying)) {
			if (p.isAirBorne && charge == 0 && glide > 1) {
				p.addVelocity((mop.hitVec.x - p.posX) / 25, /* ((mop.blockY - p.posY) / 35) + */ 0.8,
							(mop.hitVec.z - p.posZ) / 25);
				wallLeap = maxWallLeap;
				charge = maxCharge;
			} else {
				p.addVelocity((mop.hitVec.x - p.posX) / 35, .31,
							(mop.hitVec.z - p.posZ) / 35);
			}
			cooldown = maxCooldown;
			ItemStack itemStack = p.getHeldItem(handIn);
			itemStack.setItemDamage(itemStack.getItemDamage() + 4);
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, p.getHeldItem(handIn));
	}

	public void onUpdate(ItemStack s, World w, Entity p, int _i, boolean b) {
		if (cooldown > 0) {
			--cooldown;
		} else {
			p.fallDistance = 0;
		}
		if (wallLeap > 0) {
			--wallLeap;
		}
		glide = 0;
		int i = MathHelper.floor(p.posX);
        int j = MathHelper.floor(p.posY - 0.20000000298023224D - (double)p.getYOffset());
        int k = MathHelper.floor(p.posZ);
        if (w.getBlockState(new BlockPos(i + 1, j + 1, k)).isBlockNormalCube()) {
			++glide;
		}
      //2nd level
        	if (w.getBlockState(new BlockPos(i + 1, j + 2, k)).isBlockNormalCube()) {// w.getBlock(i + 1, j + 2, k) != Blocks.AIR) {
        		++glide;
        	}
		if (w.getBlockState(new BlockPos(i - 1, j + 1, k)).isBlockNormalCube()) {
			++glide;
		}
		//2nd level
			if (w.getBlockState(new BlockPos(i - 1, j + 2, k)).isBlockNormalCube()) {
	    		++glide;
	    	}
		if (w.getBlockState(new BlockPos(i, j + 1, k + 1)).isBlockNormalCube()) {
			++glide;
		}
		//2nd level
			if (w.getBlockState(new BlockPos(i, j + 2, k + 1)).isBlockNormalCube()) {
				++glide;
			}
		if (w.getBlockState(new BlockPos(i, j + 1, k - 1)).isBlockNormalCube()) {
			++glide;
		}
		//2nd level
			if (w.getBlockState(new BlockPos(i, j + 2, k - 1)).isBlockNormalCube()) {
				++glide;
			}
		if (glide > 0 && p.isAirBorne && !w.getBlockState(new BlockPos(i, j, k)).isBlockNormalCube()) {
			op1(s, w, p, i, b);
			if (charge > 0) {
				if (mag(p.motionX, p.motionY, p.motionZ) < 0.3) {
					--charge;
				} else {
					charge = maxCharge;
				}
			}
		} else {
			if (wallLeap > 0 && p.isAirBorne) {
				RayTraceResult mop = ((EntityLivingBase) p).rayTrace(16, 1.0f);
				p.addVelocity(0, .02, 0);
			}
		}
	}

	private double mag(double motionX, double motionY, double motionZ) {
		return Math.pow((Math.pow(motionX, 2) + Math.pow(motionY, 2) + Math.pow(motionZ, 2)), .5);
	}
	
	private int getSign(double n) {
		if (n == 0){
			return 0;
		} else if (n > 0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private void op1(ItemStack s, World w, Entity p, int i, boolean b) {
		EntityPlayer player = (EntityPlayer) p;
		if (p.isInWater() || player.capabilities.isFlying) { // || mag(p.motionX, p.motionY, p.motionZ) > 1.4
			
		}
		else {
			
			if (player.getHeldItemMainhand() == s || player.getHeldItemOffhand() == s) {
				p.addVelocity(0, .04, 0); //.03, .036
				if (p.isSneaking()) {
					p.addVelocity(0, .02, 0);
					if (p.motionY > .1) {
						p.addVelocity(0, -.02, 0);
					}
				}
				if (mag(p.motionX, p.motionY, p.motionZ) < 0.5) {
					p.addVelocity(0, .01, 0);
				}
				
				if (p.fallDistance > 3) {
					--p.fallDistance;
				}
			}
		}
	}
	
	public ItemStack getRepairItemStack()
    {
		Item ret = ModItems.RUBY;
        ItemStack repairMaterial = new ItemStack(ret,1);
        return repairMaterial;
    }
	
	
}
