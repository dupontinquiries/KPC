package media.kitchen.parkour.world.structure;

import media.kitchen.parkour.Parkour;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class KPCForgeBlobPiece {

    public static class Piece extends TemplateStructurePiece {

        public Piece(TemplateManager templateMgr, CompoundNBT nbt) {
            super(Parkour.KPC_FORGE_PIECE, nbt);
            this.setupTemplate(templateMgr);
        }

        public Piece(TemplateManager templateMgr, ResourceLocation resLoc, BlockPos blockPos, Rotation rot, int offsetY) {
            super(Parkour.KPC_FORGE_PIECE, 0);
            this.templatePosition = new BlockPos(blockPos.getX(), blockPos.getY() - offsetY, blockPos.getZ());
            this.setupTemplate(templateMgr);
        }

        private void setupTemplate(TemplateManager templateMgr) {
            Template template = templateMgr.getTemplateDefaulted(Parkour.KPC_FORGE_LOC);
            PlacementSettings placementsettings = (new PlacementSettings())
                    .setRotation(Rotation.NONE)
                    .setMirror(Mirror.NONE)
                    .setCenterOffset(BlockPos.ZERO)
                    .addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        public boolean func_225577_a_(IWorld worldIn, ChunkGenerator<?> chunkGenIn, Random rand, MutableBoundingBox mutableBB, ChunkPos chunkPos) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(Rotation.NONE).setMirror(Mirror.NONE).setCenterOffset(BlockPos.ZERO).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3, 0, 0)));
            int strucHeight = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, blockpos1.getX(), blockpos1.getZ());
            this.templatePosition = this.templatePosition.add(0, strucHeight, 0);
            boolean superReturn = super.func_225577_a_(worldIn, chunkGenIn, rand, mutableBB, chunkPos);
            int i = templatePosition.getX(),
                    j = templatePosition.getY() + 12,
                    k = templatePosition.getZ();
            //spawnBoss(worldIn, mutableBB, i, j, k, rand);
            return superReturn;
        }

        // spawn boss fight
        protected boolean spawnBoss(IWorld worldIn, MutableBoundingBox p_175817_2_, int i, int j, int k, Random rand) {
                // make a drowned
                System.out.println("   spawned bosses at " + i + ", " + j + ", " + k);
                if ( !worldIn.isRemote() ) {
                    for (int g = 0; g < 10; ++g) {
                        // create a boss
                        DrownedEntity boss = EntityType.DROWNED.create(worldIn.getWorld());

                        // buff its attributes
                        boss.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(boss.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() * 8F);
                        boss.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(boss.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() * 60F);

                        boss.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(boss.getAttribute(SharedMonsterAttributes.ARMOR).getBaseValue() * 20F);

                        boss.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(boss.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() * 1.9F);

                        // give it a crown
                        boss.replaceItemInInventory(100 + EquipmentSlotType.HEAD.getIndex(), new ItemStack(Items.GOLDEN_HELMET));
                        // armor it up
                        boss.replaceItemInInventory(100 + EquipmentSlotType.CHEST.getIndex(), new ItemStack(Items.GOLDEN_CHESTPLATE));
                        boss.replaceItemInInventory(100 + EquipmentSlotType.LEGS.getIndex(), new ItemStack(Items.GOLDEN_LEGGINGS));
                        boss.replaceItemInInventory(100 + EquipmentSlotType.FEET.getIndex(), new ItemStack(Items.GOLDEN_BOOTS));
                        // give it a weapon
                        ItemStack weapon = new ItemStack(Items.TRIDENT);
                        weapon.addEnchantment(Enchantments.CHANNELING, 1);
                        //weapon.addEnchantment(Enchantments.MENDING, 1);
                        //weapon.addEnchantment(Enchantments.LOYALTY, 3);
                        //weapon.addEnchantment(Enchantments.PIERCING, 4);
                        //weapon.addEnchantment(Enchantments.IMPALING, 1);

                        boss.setHeldItem(Hand.MAIN_HAND, weapon);

                        // give it regeneration
                        boss.addPotionEffect(new EffectInstance(Effects.REGENERATION, 400, 2));

                        //boss.heal(boss.getMaxHealth());
                        boss.setLocationAndAngles((double) i - 6 + rand.nextInt(12), (double) j + rand.nextInt(3), (double) k - 6 + rand.nextInt(12), 0.0F, 0.0F);
                        //boss.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(boss)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);

                        // spawn
                        //worldIn.addEntity(boss);
                        worldIn.getChunk(i / 16, k / 16).addEntity(boss);
                        //worldIn.addEntity(boss);
                    }
                }
                return true;
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand,
                                        MutableBoundingBox sbb) {
            // empty, don't think I needed this for such a simple example?
        }
    }

}
