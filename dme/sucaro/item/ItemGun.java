// 
// Decompiled by Procyon v0.5.36
// 

package dme.sucaro.item;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dme.sucaro.entity.EntityBullet;
import dme.sucaro.main.BulletEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemGun extends Item {
	private static final double pi = 3.1415927f;
	private static final double kVal = 0.10000000149011612;

	// spinup
	private double spinupTime = -1;
	private double spinupFireRate = -1;
	private int spinupDecrement;
	private boolean isInUse = false;
	private int useTicks = 40;
	
	//magazine
	private int capacity;
	private int mag;

	private double damage;
	private double kickback;
	private double recoil;
	private double accuracy;
	private float power;
	private int knockback;
	private int number;
	private int fireTime;
	private int fireRate;
	private Item ammo;
	private String particle;
	private boolean explodes;
	private boolean exGracity;
	private double exSize;

	public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
		if (this.isInUse) return EnumAction.bow;
		else return EnumAction.none;
	}

	public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
		return p_77654_1_;
	}

	public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
		return 72000;
	}

	/**
	 * firerate, damage, recoil, accuracy, power, knockback, number, firetime, ammo,
	 * particle, explode, exsize, exgravity
	 */
	public ItemGun(final int firerate, final double damage, final double recoil, final double kickback,
			final double accuracy, final float power, final int knockback, final int numberbullet, final int firetime,
			final Item ammo, final String particle, final Boolean explodes, final Boolean xg, final double xs) {
		this.fireRate = firerate;
		this.damage = damage;
		this.kickback = kickback;
		this.recoil = recoil;
		this.accuracy = accuracy;
		this.power = power;
		this.knockback = knockback;
		this.number = numberbullet;
		this.fireTime = firetime;
		this.ammo = ammo;
		this.particle = particle;
		this.explodes = explodes;
		this.exSize = xs;
		this.exGracity = xg;
		this.setMaxDamage(this.fireRate + useTicks + 1);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.capacity = -1;
		this.mag = 0;
		// this.func_77664_n();
	}
	
	/**
	 * firerate, damage, recoil, accuracy, power, knockback, number, firetime, magsize,  ammo,
	 * particle, explode, exsize, exgravity, capacity
	 */
	public ItemGun(int firerate, double damage, double recoil, double kickback,
			double accuracy, float power, int knockback, int numberbullet, int firetime, int capacity,
			Item ammo, String particle, Boolean explodes, Boolean xg, double xs) {
		this.fireRate = firerate;
		this.damage = damage;
		this.kickback = kickback;
		this.recoil = recoil;
		this.accuracy = accuracy;
		this.power = power;
		this.knockback = knockback;
		this.number = numberbullet;
		this.fireTime = firetime;
		this.ammo = ammo;
		this.particle = particle;
		this.explodes = explodes;
		this.exSize = xs;
		this.exGracity = xg;
		this.setMaxDamage(this.fireRate + useTicks + 1);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.capacity = capacity;
		this.mag = capacity;
		// this.func_77664_n();
	}

	/**
	 * firerate, damage, recoil, accuracy, power, knockback, number, firetime, ammo,
	 * particle, explode, exsize, exgravity
	 */
	public ItemGun(final int sut, final int suf, final int firerate, final double damage, final double recoil,
			final double kickback, final double accuracy, final float power, final int knockback,
			final int numberbullet, final int firetime, final Item ammo, final String particle, final Boolean explodes,
			final Boolean xg, final double xs) {
		this.spinupTime = sut;
		this.spinupFireRate = suf;
		this.spinupDecrement = (int) ((this.fireRate - this.spinupFireRate) / this.spinupTime);
		this.fireRate = firerate;
		this.damage = damage;
		this.kickback = kickback;
		this.recoil = recoil;
		this.accuracy = accuracy;
		this.power = power;
		this.knockback = knockback;
		this.number = numberbullet;
		this.fireTime = firetime;
		this.ammo = ammo;
		this.particle = particle;
		this.explodes = explodes;
		this.exSize = xs;
		this.exGracity = xg;
		this.setMaxDamage(this.fireRate + useTicks + 1);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setMaxStackSize(1);
		this.setNoRepair();
		// this.func_77664_n();
	}

	/**
	 * Queries the percentage of the 'Durability' bar that should be drawn.
	 *
	 * @param stack The current ItemStack
	 * @return 1.0 for 100% 0 for 0%
	 */
	/*
	 * public double getDurabilityForDisplay(ItemStack stack) { return (double)
	 * (Math.min((stack.getItemDamageForDisplay() - this.useTicks) / (double)
	 * (stack.getMaxDamage() - this.useTicks), 1)); }
	 */

	public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_,
			final boolean p_77663_5_) {
		EntityPlayer player = (EntityPlayer) entity;
		if (this.mag == -1) {
			//do nothing
		} else {
			if (this.mag < -2) {
				this.mag++;
				this.isInUse = false;
				return;
			} else if (this.mag == -2) {
				this.mag = this.capacity;
				return;
			}
		}
		if (this.isInUse && this.mag > 0) {
			if (player.getItemInUse() == stack) {
				this.onPlayerStoppedUsing(stack, world, player, stack.stackSize);
			} else {
				player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
			}
		} else {
			//do nothing
		}
		if (stack.getItemDamage() > 0) {
			stack.setItemDamage(stack.getItemDamage() - 1);
		} else if (stack.getItemDamage() <= 0) {
		}
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List,
			final boolean par4) {
	}

	public void registerIcons(final IIconRegister p_94581_1_) {
		this.itemIcon = p_94581_1_.registerIcon(this.getIconString() + "");
	}

	public ItemStack onItemRightClick(final ItemStack stack, final World w, final EntityPlayer player) {
		// p.worldObj.playSoundAtEntity(p, "random.bow", 1.0F, 1.0F);
		// p.worldObj.getLightBrightness(p_72801_1_, p_72801_2_, p_72801_3_)
		if (stack.getItemDamage() <= this.useTicks) {
			this.isInUse = true;
			player.setItemInUse(stack, 1000);
		} else {
			this.isInUse = false;
		}
		return stack;
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world,
	 * entityplayer, itemInUseCount
	 */
	public void onPlayerStoppedUsing(ItemStack s, World w, EntityPlayer p, int o) {
		if (this.spinupFireRate != -1) {
			this.setMaxDamage(this.fireRate + useTicks + 1);
		}
	}

	float rad(float angle) {
		return angle * (float) Math.PI / 180;
	}

	/**
	 * 
	 * @param motionX
	 * @param motionY
	 * @param motionZ
	 * @return vec magnitude
	 */
	private double mag3D(double motionX, double motionY, double motionZ) {
		return Math.pow((Math.pow(motionX, 2) + Math.pow(motionY, 2) + Math.pow(motionZ, 2)), .5);
	}

	public void onUsingTick(final ItemStack stack, final EntityPlayer player, final int count) {
		System.out.println(this.mag);
		if (!this.isInUse) {
			return;
		}
		final Random rand = new Random();
		final BulletEvent event = new BulletEvent(player, stack, count, this.ammo);
		MinecraftForge.EVENT_BUS.post((Event) event);
		if (event.isCanceled()) {
			return;
		}
		if (stack.getItemDamage() <= this.useTicks) {
			if (!this.explodes) {
				// loop to iterate all bullets
				for (int c = 0; c < this.number; c++) {
					final boolean flag = player.capabilities.isCreativeMode
							|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0
							|| this.ammo == null;
					if (flag || player.inventory.hasItem(this.ammo)) {
						//magazine
						if (this.mag == 0) {
							this.reload();
							return;
						} else if (this.mag != -1) {
							this.mag--;
						}
						EntityBullet entityarrow = new EntityBullet(player.worldObj, (EntityLivingBase) player,
								this.power, this.ammo);
						entityarrow.setDamage(this.damage);
						entityarrow.setKnockbackStrength(this.knockback);
						entityarrow.setIsCritical(true);
						entityarrow.setFire(this.fireTime);
						if (flag) {
							entityarrow.canBePickedUp = 2;
						} else {
							player.inventory.consumeInventoryItem(this.ammo);
						}
						final double f1 = (rand.nextDouble() - 0.5) * this.accuracy;
						final double f2 = (rand.nextDouble() - 0.5) * this.accuracy;
						final double f3 = (rand.nextDouble() - 0.5) * this.accuracy;
						// entityarrow.setVelocity(entityarrow.motionX*this.Power,
						// entityarrow.motionY*this.Power, entityarrow.motionZ*this.Power);
						entityarrow.addVelocity(f1, f2, f3);
						stack.setItemDamage(this.fireRate + useTicks);
						player.worldObj.spawnEntityInWorld((Entity) entityarrow);
						//player.worldObj.spawnParticle(this.particle, entityarrow.posX, entityarrow.posY,
						//		entityarrow.posZ, entityarrow.motionX, entityarrow.motionY, entityarrow.motionZ);
						player.worldObj.spawnParticle(this.particle, entityarrow.posX, entityarrow.posY,
								entityarrow.posZ, 0, .7, 0);
						player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "fireworks.largeBlast", 1.0f, 1.0f);
						// player.addVelocity(-(-MathHelper.sqrt_double(player.rotationYaw / 180.0f *
						// pi) * MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) *
						// this.Recoil, -(-MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) *
						// this.Recoil, -(MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) *
						// MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil);
						// -(-MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) *
						// MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil,
						// -(-MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil,
						// -(MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) *
						// MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil
					}
				}
			}
			// kickback
			if (mag3D(player.motionX, player.motionY, player.motionZ) > .3) {
				float rotationYaw = player.rotationYaw, rotationPitch = player.rotationPitch;
				float rx = -MathHelper.sin(rad(rotationYaw)) * MathHelper.cos(rad(rotationPitch));
				float rz = MathHelper.cos(rad(rotationYaw)) * MathHelper.cos(rad(rotationPitch));
				float ry = -MathHelper.sin(rad(rotationPitch));
				player.addVelocity(-1 * this.kickback * rx, -1 * this.kickback * ry, -1 * this.kickback * rz);
			}
			// recoil
			//player.rotationPitch / 180.0f * pi
			
			player.rotationYaw += 0;
			player.rotationPitch += 0;
		} else {
			player.stopUsingItem();
		}
	}

	private void reload() {
		// TODO Auto-generated method stub
		this.mag = -20;
	}

	public void setSpinupTime(float t) {
		this.spinupTime = t;
	}

	public void setSpinupFireRate(float t) {
		this.spinupFireRate = t;
	}

}
