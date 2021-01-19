package media.kitchen.kitchenparkour.gen.world;

import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Vector;

public class FeatureGen {

    private static Vector<OreData> ORE_VECTOR = new Vector<>();

    // called to handle ore featuring
    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {

        // init ore data

        ORE_VECTOR.add( new OreData(8, -1, 0, 105, Parkour.RUBY_ORE.get().getDefaultState(), OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER).addBiome(Biome.Category.NETHER) ); // ruby data
        ORE_VECTOR.add( new OreData(3, 1, 0, 13, Parkour.TAYDON_ORE.get().getDefaultState(), CustomOreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD).addBiome(Biome.Category.ICY).addBiome(Biome.Category.FOREST).addBiome(Biome.Category.EXTREME_HILLS).addBiome(Biome.Category.JUNGLE).addBiome(Biome.Category.PLAINS).addBiome(Biome.Category.SWAMP).addBiome(Biome.Category.DESERT) ); // taydon data
        ORE_VECTOR.add( new OreData(13, 3, 0, 65, Parkour.TAYDON_ORE.get().getDefaultState(), CustomOreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD).addBiome(Biome.Category.MUSHROOM) ); // taydon data
        ORE_VECTOR.add( new OreData(8, 1, 0, 70, Parkour.OBERITE_BLOCK.get().getDefaultState(), OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD).addBiome(Biome.Category.ICY) ); // sauberite data

        // generate ore features

        for ( OreData d : ORE_VECTOR) {
            if ( d.biomes.contains(event.getCategory()) ) {
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE.withConfiguration(new OreFeatureConfig(d.filler, d.state, d.veinSize)).range(d.maxHeight).square()
                );
            }
        }

        // build & generate structure features

        if ( !event.getCategory().equals(Biome.Category.NETHER) && !event.getCategory().equals(Biome.Category.THEEND) ) {
            ///event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, new ConfiguredFeature<NoFeatureConfig, StructurePiece>());
            //event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.BONUS_CHEST.withConfiguration(FeatureSpreadConfig.NO_FEATURE_CONFIG)); // works, generates a bonus chest every chunk
        }

    }

    private static class OreData {

        public RuleTest filler;
        public BlockState state;
        public int veinSize, veinsPerChunk, minHeight, maxHeight;
        public Vector<Biome.Category> biomes;


        public OreData addBiome(Biome.Category b) {
            biomes.add(b);
            return this;
        }

        public OreData(int a, int b, int c, int d, BlockState s, RuleTest f) {
            veinSize = a;
            veinsPerChunk = b;
            minHeight = c;
            maxHeight = d;
            state = s;
            filler = f;
            biomes = new Vector<>();
        }

    }

}