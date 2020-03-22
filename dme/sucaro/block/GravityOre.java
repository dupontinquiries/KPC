// 
// Decompiled by Procyon v0.5.36
// 

package dme.sucaro.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dme.sucaro.item.ItemRegistry;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class GravityOre extends BlockFalling {
	protected GravityOre(final Material material) {
		super(material);
		this.setBlockName("Gravity Ore");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(1.7f);
		this.setResistance(50.0f);
		this.setLightLevel(100.0f);
		this.setHarvestLevel("pickaxe", 2);
		this.setTickRandomly(true);
	}

	public Item getItemDropped(int meta, Random random, int fortune) {
		//if (fortune > 0) {
		//	return Item.getItemFromBlock(BlockManager.gravOre);
		//} else {
		return Item.getItemById(Item.getIdFromItem(Items.iron_ingot)); // Item.getItemFromBlock(this);
			// 265 = iron ingot
		//}
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
		// System.out.println("ticking");
		// Random rand = new Random();
		// playSound(x, y, z, "fireworks.largeBlast", 1.0f, 1.0f, false);
		if (r.nextDouble() < .8) {
			return;
		}
		//double rVal = .1 + (r.nextDouble() * .43);// rand.nextDouble() * .07;
		int sqrRad = 65;
		AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(x - sqrRad, y - sqrRad, z - sqrRad, x + sqrRad, y + sqrRad,
				z + sqrRad);
		double chillFactor = .323; //.223
		List boundingList = world.getEntitiesWithinAABB(Entity.class, axis);
		for (int g = 0; g < boundingList.size(); g++) {
			Entity elbf = (Entity) boundingList.get(g);
			if (elbf instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) elbf;
				if (player.inventory.hasItem(ItemRegistry.higgsStaff)) {
					return;
				}
				if (player.isAirBorne) {
					chillFactor += .1;
				} else if (player.isSneaking()) {
					chillFactor -= .2;
				}
			}
			//EntityLivingBase elbf = (EntityLivingBase) boundingList.get(g);
			
			if (elbf instanceof EntityLivingBase && r.nextDouble() < .01) {
				elbf.attackEntityFrom(DamageSource.magic, r.nextInt(2) + r.nextInt(2));
			}
			double mult = (sqrRad - magDist3D(elbf.posX, elbf.posY, elbf.posZ, x, y, z)) / sqrRad;
			
			elbf.motionX += getSign(elbf.posX - x) * -1 * mult * chillFactor;
			elbf.motionY += getSign(elbf.posY - y) * -1 * mult * 2.25 * chillFactor;
			elbf.motionZ += getSign(elbf.posZ - z) * -1 * mult * chillFactor;
			elbf.velocityChanged = true;
			//elbf.fallDistance = 0;
		}			
	}

	public int tickRate() {
		return 1;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random random) {
		return random.nextInt(15) + 1;
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
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int dmg)
    {
        player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
        player.addExhaustion(0.025F);

        if (this.canSilkHarvest(world, player, x, y, z, dmg) && EnchantmentHelper.getSilkTouchModifier(player))
        {
            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
            ItemStack itemstack = this.createStackedBlock(dmg);

            if (itemstack != null)
            {
                items.add(itemstack);
            }

            ForgeEventFactory.fireBlockHarvesting(items, world, this, x, y, z, dmg, 0, 1.0f, true, player);
            for (ItemStack is : items)
            {
                this.dropBlockAsItem(world, x, y, z, is);
            }
        }
        else
        {
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
		/*
		 * System.out.println("placing block"); final Thread one; final BlockFalling
		 * block = this; one = new Thread(){ public void run() { try{ Thread.sleep(100);
		 * System.out.println("running thread"); block.updateTick(world, x, y, z, new
		 * Random()); } catch(InterruptedException v){System.out.println(v);} catch
		 * (Error e) { System.out.println("error running thread"); return; } } };
		 */
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

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the
	 * block.
	 */
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		TileEntityChest tileentitychest = new TileEntityChest();
		return tileentitychest;
	}

	/*
	 * int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F /
	 * 360.0F) + 0.5D) & 3;
	 * 
	 * if (l == 0) { b0 = 2; }
	 * 
	 * if (l == 1) { b0 = 5; }
	 * 
	 * if (l == 2) { b0 = 3; }
	 * 
	 * if (l == 3) { b0 = 4; }
	 */

}