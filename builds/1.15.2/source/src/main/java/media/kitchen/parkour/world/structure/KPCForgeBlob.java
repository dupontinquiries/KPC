package media.kitchen.parkour.world.structure;

import com.mojang.datafixers.Dynamic;
import media.kitchen.parkour.Parkour;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.function.Function;

public class KPCForgeBlob extends ScatteredStructure<NoFeatureConfig> {

    public KPCForgeBlob(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    protected int getSeedModifier() {
        return 165745296;
    }

    @Override
    public IStartFactory getStartFactory() {
        return KPCForgeBlob.Start::new;
    }

    @Override
    public String getStructureName() {
        return Parkour.KPC_FORGE_LOC.toString();
    }

    @Override
    public int getSize() {
        return 1;
    }

    public class Start extends StructureStart {

        private int makeRandom( int i ) {
            if (rand.nextBoolean() ) {
                return 3 * (1 + rand.nextInt(i));
            } else {
                return -3 * (1 + rand.nextInt(i));
            }
        }

        public Start(Structure<?> structIn, int int_1, int int_2, MutableBoundingBox mutableBB, int int_3, long long_1) {
            super(structIn, int_1, int_2, mutableBB, int_3, long_1);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            int worldX = chunkX * 16;
            int worldZ = chunkZ * 16;
            BlockPos blockpos = new BlockPos(worldX, 0, worldZ);
            int offy = 1 + rand.nextInt( 6);
            Rotation r = Rotation.randomRotation(rand);
            this.components.add(new KPCForgeBlobPiece.Piece(templateManagerIn, Parkour.KPC_FORGE_LOC, blockpos,
                    r, offy));
            // TODO: add second floor
            //this.components.add(new KPCNBTStrucPiece.Piece(templateManagerIn, Parkour.KPC_RUIN_A_LOC, blockpos.add(makeRandom(5), 13, makeRandom(5)),
            //        Rotation.randomRotation(rand), 1 + rand.nextInt( 6), Parkour.KPC_RUIN_A_PIECE));
            //this.components.add(new KPCNBTStrucPiece.Piece(templateManagerIn, Parkour.KPC_RUIN_A_LOC, blockpos.add(makeRandom(5), 22, makeRandom(5)),
            //        Rotation.randomRotation(rand), 1 + rand.nextInt( 6), Parkour.KPC_RUIN_A_PIECE));
            //this.components.add(new KPCNBTStrucPiece.Piece(templateManagerIn, Parkour.KPC_FA_LOC, blockpos.add(makeRandom(1), 13, makeRandom(1)),
            //        r, offy, Parkour.KPC_FA_PIECE));
            this.recalculateStructureSize();
        }

    }

}