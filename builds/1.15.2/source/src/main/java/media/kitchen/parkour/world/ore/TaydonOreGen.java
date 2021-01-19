package media.kitchen.parkour.world.ore;

import media.kitchen.parkour.Parkour;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class TaydonOreGen {
    public static void generateOre() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if ( biome.getPrecipitation() == Biome.RainType.SNOW ) {
                ConfiguredPlacement rubyConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(3, 5, 5, 28));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE.withConfiguration(
                                new OreFeatureConfig( OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                        Parkour.TAYDON_ORE.get().getDefaultState(), 6 ))
                                .withPlacement(rubyConfig));
            }
        }
    }
}
