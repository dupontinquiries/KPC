package media.kitchen.kitchenparkour.gen.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;

public class CustomOreFeatureConfig extends OreFeatureConfig {


    public CustomOreFeatureConfig(RuleTest p_i241989_1_, BlockState state, int size) {
        super(p_i241989_1_, state, size);
    }

    public static final class CustomFillerBlockType {
        public static final RuleTest END_STONE_RULE = new BlockMatchRuleTest(Blocks.END_STONE);
    }
}