// 
// Decompiled by Procyon v0.5.36
// 

package dme.sucaro.block;

import java.util.ArrayList;
import java.util.Random;

import dme.sucaro.entity.EntityGravity;
import dme.sucaro.item.HiggsStaff;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class GravityOre extends Block implements ITileEntityProvider {
	
	private static int blockid = 0;
	
	private static final String __OBFID = "TK_00000001";
	
	protected GravityOre(final Material material) {
		super(material);
		this.setBlockName("Gravity Ore");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(3.5f);
		this.setResistance(50.0f);
		this.setLightLevel(40.0f);
		this.setHarvestLevel("pickaxe", 2);
		this.setTickRandomly(true);
		
		//System.out.println(" hte = " + this.hasTileEntity());
		
		//this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		
		//EntityGravity gravity = new EntityGravity(world, x, y, z, 80, r);
		//world.spawnEntityInWorld(gravity);
		
		
	}
	
	//world.getTileEntity(x, y, z)
	
	// FOR SWORD p_149696_1_.playSoundEffect((double)p_149696_2_ + 0.5D, (double)p_149696_3_ + 0.5D, (double)p_149696_4_ + 0.5D, "note.snare /or bassattack" + s, 3.0F, f);
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		//System.out.println("  " + "Function  createNewTileEntity called!");
		TileEntity gravity = new EntityGravity(p_149915_1_, new Random());
		//TileEntity.addMapping(gravity.getClass(), "kEntityGravity");
		//NBTTagCompound tag = new NBTTagCompound();
		//tag.setString("id", "kEntityGravity_" + (int) (new Random().nextDouble() * 100000) + "_" + ++blockid);
		//gravity.writeToNBT(tag);
		return gravity;
	}

    //public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
	
	/**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return true;
    }
	
	/**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
	@Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

	public Item getItemDropped(int meta, Random random, int fortune) {
		//if (fortune > 0) {
		//	return Item.getItemFromBlock(BlockManager.gravOre);
		//} else {
		return Item.getItemById( (int) ((random.nextDouble() * 421) + 1) );
		//return Item.getItemById(Item.getIdFromItem(Items.iron_ingot)); // Item.getItemFromBlock(this);
			// 265 = iron ingot
		//}
	}
	
	
	/*
	private void drop(World world, int x, int y, int z)
    {
        if (func_149831_e(world, x, y - 1, z) && y >= 0)
        {
            byte b0 = 32;

            if (!fallInstantly && world.checkChunksExist(x - b0, y - b0, z - b0, x + b0, y + b0, z + b0))
            {
                if (!world.isRemote)
                {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this, world.getBlockMetadata(x, y, z));
                    this.func_149829_a(entityfallingblock);
                    world.spawnEntityInWorld(entityfallingblock);
                }
            }
            else
            {
                world.setBlockToAir(x, y, z);

                while (func_149831_e(world, x, y - 1, z) && y > 0)
                {
                    --y;
                }

                if (y > 0)
                {
                    world.setBlock(x, y, z, this);
                }
            }
        }
    }
    */

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
		if (r.nextDouble() > .2) {
			return;
		}
		world.playSound(x, y, z, "fireworks.largeBlast", 0.6f, 1.0f, false);
		//double rVal = .1 + (r.nextDouble() * .43);// rand.nextDouble() * .07;
		//EntityGravity gravity = new EntityGravity(world, x, y, z, 80, r);
		//world.spawnEntityInWorld(gravity);
	}

	public int tickRate() {
		return 1;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random random) {
		if (random.nextDouble() < .8) {
			return 0;
		}
		return (int) Math.pow(random.nextInt(15625) + 1, 0.333);
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
						(double) p_149657_3_ + 0.5D, (double) p_149657_4_ + 0.5D, 25));
			}
		}
	}

	/**
	 * Called whenever an entity is walking on top of this block. Args: world, x, y,
	 * z, entity
	 */
	@Override
	public void onEntityWalking(World world, int p_149724_2_, int p_149724_3_, int p_149724_4_,
			Entity entity) {
		/*
		if (entity instanceof EntityPlayer) {
			EntityLivingBase elbf = ((EntityPlayer) entity);
			
			//if (elbf.getHeldItem().setTagInfo(p_77983_1_, p_77983_2_); return;
			
			MovingObjectPosition mop = elbf.rayTrace(1, 1.0f);
			
			if (mop == null) return;
			
			elbf.addVelocity((mop.blockX - elbf.posX) * 3, .51,
					(mop.blockZ - elbf.posZ) * 3);     
		}
		*/
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
		/**
	     * Adds a block event with the given Args to the blockEventCache. During the next tick(), the block specified will
	     * have its onBlockEvent handler called with the given parameters. Args: X,Y,Z, Block, EventID, EventParameter
	     */
		//world.addBlockEvent(x, y, z, BlockManager.gravOre, 0, 0);
		return meta;
	}
	
	//public void onBlockEvent() {
	//	
	//}

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