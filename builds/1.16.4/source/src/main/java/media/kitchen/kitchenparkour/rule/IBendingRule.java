package media.kitchen.kitchenparkour.rule;

import net.minecraft.block.BlockState;

public interface IBendingRule {

    public EarthBendingRule EARTH_BENDING_RULE = new EarthBendingRule();

    public abstract boolean criteriaMet(BlockState state);

    public class EarthBendingRule implements IBendingRule {

        @Override
        public boolean criteriaMet(BlockState state) {
            return (
                    ( state.getBlock().getTranslationKey().contains("dirt") || state.getBlock().getTranslationKey().contains("ore")
                            || state.getBlock().getTranslationKey().contains("grass") || state.getBlock().getTranslationKey().contains("stone")
                            || state.getBlock().getTranslationKey().contains("diorite") || state.getBlock().getTranslationKey().contains("andesite")
                            || state.getBlock().getTranslationKey().contains("netherrack") || state.getBlock().getTranslationKey().contains("nylium")
                            || state.getBlock().getTranslationKey().contains("brick") || state.getBlock().getTranslationKey().contains("basalt")
                            || state.getBlock().getTranslationKey().contains("charred") || state.getBlock().getTranslationKey().contains("blackstone")
                            || state.getBlock().getTranslationKey().contains("magma") || state.getBlock().getTranslationKey().contains("concrete")
                            || state.getBlock().getTranslationKey().contains("obsidian") || state.getBlock().getTranslationKey().contains("granite")
                            || state.getBlock().getTranslationKey().contains("path") || state.getBlock().getTranslationKey().contains("marble") )
                            && state.isSolid()
            );
        }
    }

}