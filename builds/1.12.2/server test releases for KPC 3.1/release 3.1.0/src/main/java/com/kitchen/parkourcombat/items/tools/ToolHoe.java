package com.kitchen.parkourcombat.items.tools;

import java.util.Random;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.init.ModItems;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolHoe extends ItemHoe implements IHasModel {
	
	private Random random = new Random();
	private final double dropChance = 0.03;

	public ToolHoe(String name, ToolMaterial material)
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
	
	/**
     * Called when a Block is right-clicked with this Item
     */
    @SuppressWarnings("incomplete-switch")
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else if (!worldIn.isRemote)
        {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(itemstack, player, worldIn, pos);
            if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up()))
            {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH)
                {
                    this.setBlock(itemstack, player, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                    // drop chance
                    itemstack.damageItem(1, player);
                    if (random.nextDouble() < dropChance){
                    	player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ,
                    			new ItemStack(Item.getByNameOrId("golden_apple"), random.nextInt(3) + 1)));
                    }
                    // !drop chance
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.DIRT)
                {
                    switch ((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT))
                    {
                        case DIRT:
                            this.setBlock(itemstack, player, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                            // drop chance
                            itemstack.damageItem(1, player);
                            if (random.nextDouble() < dropChance){
                            	player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ,
                            			new ItemStack(Item.getByNameOrId("golden_apple"), random.nextInt(3) + 1)));
                            }
                            // !drop chance
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            this.setBlock(itemstack, player, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            // drop chance
                            itemstack.damageItem(1, player);
                            if (random.nextDouble() < dropChance){
                            	int x = player.getPosition().getX(), y = player.getPosition().getY(), z = player.getPosition().getZ();
                            	ItemStack ist = new ItemStack(Items.GOLDEN_APPLE, random.nextInt(3) + 1);
                            	ist.setStackDisplayName("A gift from the sun gods...");
                            	EntityItem drop = new EntityItem(worldIn, x, y, z, ist);
                            	worldIn.spawnEntity(drop);
                            }
                            // !drop chance
                            return EnumActionResult.SUCCESS;
                    }
                }
            }

            return EnumActionResult.PASS;
        }
        return EnumActionResult.PASS;
    }
    
    public ItemStack getRepairItemStack()
    {
        Item ret = ModItems.RUBY;
        ItemStack repairMaterial = new ItemStack(ret,1);
        return repairMaterial;
    }
}
