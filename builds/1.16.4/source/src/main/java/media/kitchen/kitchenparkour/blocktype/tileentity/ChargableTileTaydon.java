package media.kitchen.kitchenparkour.blocktype.tileentity;

import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.block.BlockState;

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
