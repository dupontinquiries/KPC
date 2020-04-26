package com.kitchen.parkourcombat.items.tools;

import java.util.ArrayList;
import java.util.List;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.Coord;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolSpade extends ItemSpade implements IHasModel {
	
	static private final List<Coord> surface = gen3DRect(3, 3, 3);

	public ToolSpade(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
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
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote && (double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(1, entityLiving);
        }
        
        //break area
        float hardness = state.getBlockHardness(worldIn, pos);
        if (	state.getMaterial() == Material.GROUND ||
        		state.getMaterial() == Material.CARPET ||
        		state.getMaterial() == Material.CLAY ||
        		state.getMaterial() == Material.SNOW ||
        		state.getMaterial() == Material.CRAFTED_SNOW ||
        		state.getMaterial() == Material.GRASS ||
        		state.getMaterial() == Material.SAND)
        {
        	for (Coord coord : surface)
        	{
        		BlockPos bp = new BlockPos(pos.getX() + coord.getX(), pos.getY() + coord.getY(), pos.getZ() + coord.getZ());
        		if (worldIn.getBlockState(bp).getBlockHardness(worldIn, bp) != state.getBlockHardness(worldIn, pos)) {
        			continue;
        		}
        		//world.addBlockEvent(x + coord.getX(), y + coord.getY(), z + coord.getZ(), b, EventID.ACTION, 0);
        		worldIn.destroyBlock(bp, true);
        	}
        } 
        return true;
    }
	
	public ItemStack getRepairItemStack()
    {
		Item ret = ModItems.RUBY;
        ItemStack repairMaterial = new ItemStack(ret,1);
        return repairMaterial;
    }
	
	
}
