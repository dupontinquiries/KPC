package media.kitchen.parkour.crafting.kpctable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class KPCTable extends CraftingTableBlock {

    private static final ITextComponent label = new TranslationTextComponent("container.kpc_crafting");

    public KPCTable(Block.Properties props) {
        super(props);
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            player.openContainer(state.getContainer(worldIn, pos));
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Looks like this is called when fecthing the crafting interface
     * @param state
     * @param worldIn
     * @param pos
     * @return
     */

    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((a, p_220270_3_, p_220270_4_) -> {
            return new KPCContainer(a, p_220270_3_, IWorldPosCallable.of(worldIn, pos));
        }, label);
    }

}
