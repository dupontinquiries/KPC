package dme.sucaro.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dme.sucaro.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class SpaceshipBlock extends Block {
	
	private String unlocalizedName;
	
	protected SpaceshipBlock(final Material material) {
		super(material);
		this.unlocalizedName = "SpaceshipBlock";
		this.setBlockName("Fractured Spaceship Hull");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(2.7f);
		this.setResistance(50.0f);
		this.setLightLevel(0.0f);
		this.setHarvestLevel("pickaxe", 2);
		this.setTickRandomly(true);
	}
	
	/**
     * Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    /**
     * Returns the unlocalized name of the block with "tile." appended to the front.
     */
    public String getUnlocalizedName()
    {
        return "tile." + this.unlocalizedName;
    }

	public Item getItemDropped(int meta, Random random, int fortune) {
		return Item.getItemFromBlock(BlockManager.gravOre);
	}

	/**
	 *
	 * @param number
	 * @return sign value
	 */
	private int getSign(double n) {
		if (n == 0) {
			return 0;
		} else if (n > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	private double magDist3D(double a, double b, double c, double x, double y, double z) {
		return Math.pow((Math.pow(a - x, 2) + Math.pow(b - y, 2) + Math.pow(c - z, 2)), .5);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World world, int x, int y, int z, Random r) {
		if (r.nextDouble() < .7) {
			return;
		}
		int sqrRad = 20;
		world.playSound(x, y, z, "fireworks.largeBlast", 1.0f, 1.0f, false);
		world.createExplosion(new EntityLightningBolt(world, x + r.nextInt(sqrRad / 4), y + r.nextInt(sqrRad / 4), z + r.nextInt(sqrRad / 4)),
				(double) (x + r.nextInt(sqrRad)), (double) (y + r.nextInt(sqrRad)), (double) (z + r.nextInt(sqrRad)), 1.3f, true);
		if (r.nextDouble() < .5) {
			return;
		}
	}

	public int tickRate() {
		return 1;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random random) {
		return 1;
	}

	/**
	 * This returns a complete list of items dropped from this block.
	 *
	 * @param world    The current world
	 * @param x        X Position
	 * @param y        Y Position
	 * @param z        Z Position
	 * @param metadata Current metadata
	 * @param fortune  Breakers fortune level
	 * @return A ArrayList containing all items this block drops
	 */
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			if (item != null) {
				ret.add(new ItemStack(item, 1, damageDropped(metadata)));
			}
		}
		return ret;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int dmg) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.125F);

		if (this.canSilkHarvest(world, player, x, y, z, dmg) && EnchantmentHelper.getSilkTouchModifier(player)) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			ItemStack itemstack = this.createStackedBlock(dmg);

			if (itemstack != null) {
				items.add(itemstack);
			}

			ForgeEventFactory.fireBlockHarvesting(items, world, this, x, y, z, dmg, 0, 1.0f, true, player);
			for (ItemStack is : items) {
				this.dropBlockAsItem(world, x, y, z, is);
			}
		} else {
			harvesters.set(player);
			int i1 = EnchantmentHelper.getFortuneModifier(player);
			this.dropBlockAsItem(world, x, y, z, dmg, i1);
			harvesters.set(null);
		}
	}

	/**
	 * called by spawner, ore, redstoneOre blocks
	 */
	public void dropXpOnBlockBreak(World p_149657_1_, int p_149657_2_, int p_149657_3_, int p_149657_4_,
			int p_149657_5_) {
		if (!p_149657_1_.isRemote) {
			while (p_149657_5_ > 0) {
				int i1 = EntityXPOrb.getXPSplit(p_149657_5_);
				p_149657_5_ -= i1;
				p_149657_1_.spawnEntityInWorld(new EntityXPOrb(p_149657_1_, (double) p_149657_2_ + 0.5D,
						(double) p_149657_3_ + 0.5D, (double) p_149657_4_ + 0.5D, i1));
			}
		}
	}

	/**
	 * Called whenever an entity is walking on top of this block. Args: world, x, y,
	 * z, entity
	 */
	public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_,
			Entity p_149724_5_) {
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z,
	 * side, hitX, hitY, hitZ, block metadata
	 */
	public int onBlockPlaced(final World world, final int x, final int y, final int z, int side, float hitX, float hitY,
			float hitZ, int meta) {
		return meta;
	}

	/**
	 * Called when a player hits the block. Args: world, x, y, z, player
	 */
	public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_,
			EntityPlayer p_149699_5_) {
	}

	/**
	 * Can add to the passed in vector for a movement vector to be applied to the
	 * entity. Args: x, y, z, entity, vec3d
	 */
	public void velocityToAddToEntity(World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_,
			Entity p_149640_5_, Vec3 p_149640_6_) {
	}

	/**
	 * Called when a tile entity on a side of this block changes is created or is
	 * destroyed.
	 * 
	 * @param world The world
	 * @param x     The x position of this block instance
	 * @param y     The y position of this block instance
	 * @param z     The z position of this block instance
	 * @param tileX The x position of the tile that changed
	 * @param tileY The y position of the tile that changed
	 * @param tileZ The z position of the tile that changed
	 */
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
	}

}
