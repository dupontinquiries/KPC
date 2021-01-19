package media.kitchen.parkour.blocktype.tileentity;

import media.kitchen.parkour.Parkour;
import media.kitchen.parkour.itemtype.parkour.AmbigSoundType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Random;

public class ChargableTileTaydon extends ChargableTile {

    public ChargableTileTaydon(BlockState blockStateIn, int lmin, int lmax) {
        super(blockStateIn, lmin, lmax);
    }

    public ChargableTileTaydon() {
        super();
    }

    @Override
    protected void setup() {
        blockStateIn = Parkour.CHARGED_TAYDON_BLOCK.get().getDefaultState();
        lmin = 0;
        lmax = 6;
    }

}
