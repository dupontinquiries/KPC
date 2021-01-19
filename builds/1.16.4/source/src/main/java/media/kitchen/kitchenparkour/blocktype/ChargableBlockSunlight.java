package media.kitchen.kitchenparkour.blocktype;

import media.kitchen.kitchenparkour.blocktype.tileentity.ChargableTile;
import media.kitchen.kitchenparkour.blocktype.tileentity.ChargableTileTaydon;
import net.minecraft.block.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ChargableBlockSunlight extends BlockBase implements ITileEntityProvider {

    protected int lmin, lmax;

    protected BlockState blockStateIn = Blocks.DIRT.getDefaultState();

    public ChargableBlockSunlight(Properties props) {
        super(props);
    }

    public ChargableBlockSunlight(Properties props, BlockState blockStateIn) {
        this(props);
        this.blockStateIn = blockStateIn;
    }

    public ChargableBlockSunlight(Properties props, BlockState blockStateIn, int lmin, int lmax) {
        this(props, blockStateIn);
        this.lmin = lmin;
        this.lmax = lmax;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        DaylightDetectorBlock block;
        if (lmax == 6) return new ChargableTile(blockStateIn, lmin, lmax);
        else return new ChargableTileTaydon(blockStateIn, lmin, lmax);
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

}
