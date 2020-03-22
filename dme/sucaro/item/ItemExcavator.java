package dme.sucaro.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;
import com.sun.java.accessibility.util.EventID;

import dme.sucaro.gen.Coord;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

public class ItemExcavator extends ItemPickaxe {
	
	private static final Set itemSet = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});

	static private List<Coord> surface = gen3DRect(5, 5, 5);
	
	private Random random = new Random();
	
	public ItemExcavator(ToolMaterial mat) {
		super(mat); //
	}

	public ItemStack onItemRightClick(ItemStack s, World w, EntityPlayer p) {
		return s;
	}
	
	private static ArrayList<Coord> gen3DRect(int l, int h, int w) {
		ArrayList<Coord> list = new ArrayList<Coord>();
		int a = l;
		int b = w;
		int c = h;
		while (l > -1) {
			l--;
			while (w > -1) {
				w--;
				while (h > -1) {
					h--;
					final Coord tmp = new Coord(l, h, w);
					list.add(tmp);
				}
				h = c;
			}
			w = b;
		}
		return list;
	}
	
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
        if (random.nextDouble() < .05){
        	p_77644_3_.worldObj.spawnEntityInWorld(new EntityItem(p_77644_3_.worldObj, p_77644_3_.posX, p_77644_3_.posY, p_77644_3_.posZ, new ItemStack(Items.diamond, random.nextInt(5) + 1, 0)));
        }
    	p_77644_1_.damageItem(2, p_77644_3_);
        return true;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase elb)
    {
        //System.out.println("Block Destroyed");
		EntityPlayer player = (EntityPlayer) elb;
        float hardness = block.getBlockHardness(world, x, y, z);
    	for (Coord coord: surface) {
    		Block b = world.getBlock(x + coord.getX(), y + coord.getY(), z + coord.getZ());
    		if (Block.getIdFromBlock(b) != Block.getIdFromBlock(block)) { //b.getBlockHardness(world, x + coord.getX(), y + coord.getY(), z + coord.getZ()) > hardness){
    			continue;
    		}
    		//world.addBlockEvent(x + coord.getX(), y + coord.getY(), z + coord.getZ(), b, EventID.ACTION, 0);
    		world.setBlock(x + coord.getX(), y + coord.getY(), z + coord.getZ(), Blocks.air);
    		if (!((EntityPlayer) elb).capabilities.isCreativeMode) {
    			b.dropBlockAsItem(world, x + coord.getX(), y + coord.getY(), z + coord.getZ(), this.getMetadata(0), 0);
    			//b.getItemDropped(p_149650_1_, p_149650_2_, p_149650_3_)
    			//Block.breakBlock(world, x + coord.getX(), y + coord.getY(), z + coord.getZ(), b, b.hasTileEntity(0));  //.dropBlockAsItem(world, x + coord.getX(), y + coord.getY(), z + coord.getZ(), 1, 0);
    		}
    	}
        return true;
    }
}
