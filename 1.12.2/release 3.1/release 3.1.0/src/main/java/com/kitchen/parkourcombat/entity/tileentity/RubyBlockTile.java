package com.kitchen.parkourcombat.entity.tileentity;

import com.kitchen.parkourcombat.blocks.RubyBlock;
import com.kitchen.parkourcombat.init.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class RubyBlockTile extends TileEntity implements ITickable {
	
	private short counts = 0;
	private int charge = 0;
	private static final int MAX_CHARGE = 20;
	
	public RubyBlockTile() {
		super();
	}

	// trap trigger
	
	@Override
	public void update() {
		if (counts != 5) {
			++counts;
			return;
		} else {
			counts = 0;
		}
		World worldIn = this.getWorld();
		if (worldIn.provider.hasSkyLight())
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            int i = worldIn.getLightFor(EnumSkyBlock.SKY, pos) - worldIn.getSkylightSubtracted();
            float f = worldIn.getCelestialAngleRadians(1.0F);
            
            if (i > 0)
            {
            	float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
                f = f + (f1 - f) * 0.2F;
                i = Math.round((float)i * MathHelper.cos(f));
            }
            //i = MathHelper.clamp(i, 0, 15);
            if (i > -3) {
            	if (charge < MAX_CHARGE) {
            		++charge;
            	}
            } else {
            	if (charge > 0) {
            		//--charge;
            	}
            }
            if (charge >= MAX_CHARGE) {
            	IBlockState state = worldIn.getBlockState(pos);
                worldIn.setBlockState(pos, ModBlocks.CHARGED_RUBY_BLOCK.getDefaultState(), 2);
            } else {
            	//worldIn.setBlockState(pos, ModBlocks.RUBY_BLOCK.getDefaultState());
            }
            ( (RubyBlock) iblockstate.getBlock()).setCharge(charge);
            //sendUpdates();
            System.out.println(charge);
        }
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	private void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}

	/*
	protected void eventTriggered(World world, int x, int y, int z) {
		//world.destroyBlock(new BlockPos(x, y, z), false);
		
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	private void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}
	*/
	
}
