package dme.sucaro.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dme.sucaro.entity.EntityChain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class chaingingGun extends ItemBow
// EntityChain entityarrow = new EntityChain(w, p, 1);
{
	public static final String[] bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	private static final String OBFID = "CL_00001777";

	public chaingingGun() {
		this.maxStackSize = 1;
		this.setMaxDamage(384);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world,
	 * entityplayer, itemInUseCount
	 */
	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {
		int j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;

		// System.out.println("firing");
		if (!p_77615_2_.isRemote) {
			// p_77615_2_.spawnEntityInWorld(entityarrow);
		}
	}

	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
		return p_77654_1_;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 7200000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is
	 * being used
	 */
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.bow;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed.
	 * Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		if (p_77659_3_.capabilities.isCreativeMode || p_77659_3_.inventory.hasItem(ItemRegistry.moonArrow)) {
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		int j = this.getMaxItemUseDuration(stack) - count;
		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

		if (flag || player.inventory.hasItem(ItemRegistry.moonArrow)) {
			float f = (float) j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if ((double) f < 0.1D) {
				return;
			}

			if (f > 1.0F) {
				f = 1.0F;
			}

			EntityChain entityarrow = new EntityChain(player.worldObj, player, (7200000 - count) / 100 + 1);
			if (f == 1.0F) {
				entityarrow.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

			if (k > 0) {
				entityarrow.setDamage(entityarrow.getDamage() + (double) k * 0.5D + 0.5D);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

			if (l > 0) {
				entityarrow.setKnockbackStrength(l);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
				entityarrow.setFire(100);
			}

			stack.damageItem(1, player);
			// player.worldObj.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F /
			// (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				player.inventory.consumeInventoryItem(ItemRegistry.moonArrow);
			}

			if (!player.worldObj.isRemote) {
				player.worldObj.spawnEntityInWorld(entityarrow);
			}
		}
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on
	 * material.
	 */
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		this.itemIcon = p_94581_1_.registerIcon(this.getIconString() + "_standby");
		this.iconArray = new IIcon[bowPullIconNameArray.length];

		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = p_94581_1_.registerIcon("chainingGun" + "_" + bowPullIconNameArray[i]);
		}
	}

	/**
	 * used to cycle through icons based on their used duration, i.e. for the bow
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int p_94599_1_) {
		return this.iconArray[p_94599_1_];
	}
}