package media.kitchen.parkour;

import media.kitchen.parkour.blocktype.BlockBase;
import media.kitchen.parkour.blocktype.ChargableBlockSunlight;
import media.kitchen.parkour.blocktype.SauberiteBlock;
import media.kitchen.parkour.blocktype.tileentity.ChargableTile;
import media.kitchen.parkour.blocktype.tileentity.ChargableTileTaydon;
import media.kitchen.parkour.crafting.KPCShapedRecipe;
import media.kitchen.parkour.crafting.KShapelessRecipe;
import media.kitchen.parkour.crafting.kpctable.KPCTable;
import media.kitchen.parkour.enchantment.Stealth;
import media.kitchen.parkour.enchantment.SunBlessing;
import media.kitchen.parkour.itemtype.*;
import media.kitchen.parkour.itemtype.armor.*;
import media.kitchen.parkour.itemtype.parkour.AquaParkour;
import media.kitchen.parkour.itemtype.parkour.ExplosiveParkour;
import media.kitchen.parkour.itemtype.parkour.ParkourBase;
import media.kitchen.parkour.itemtype.quest.QuestHitChargeBase;
import media.kitchen.parkour.itemtype.token.TokenBase;
import media.kitchen.parkour.itemtype.token.TokenType;
import media.kitchen.parkour.itemtype.tools.AreaPickaxeBase;
import media.kitchen.parkour.itemtype.tools.spawnblade.SpawnBlade;
import media.kitchen.parkour.itemtype.tools.supertrident.SuperTrident;
import media.kitchen.parkour.itemtype.tools.SwordBase;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Parkour.MOD_ID)
public class Parkour
{

    public static final int QUEST_MODIFIER = 3;

    public static final ItemGroup KPC_TAB = new ItemGroup("kpc_tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Parkour.BLUE_FINS.get());
        }
    };

    public static final ItemGroup KPC_ARMOR = new ItemGroup("kpc_armor") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Parkour.SOLAR_HELM.get());
        }
    };

    public static final ItemGroup KPC_TOKENS = new ItemGroup("kpc_tokens") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Parkour.TOKEN_TANK.get());
        }
    };

    static final class ClientConfig {

        final ForgeConfigSpec.BooleanValue clientBoolean;
        final ForgeConfigSpec.ConfigValue<List<String>> clientStringList;
        final ForgeConfigSpec.EnumValue<DyeColor> clientDyeColorEnum;

        final ForgeConfigSpec.BooleanValue modelTranslucency;
        final ForgeConfigSpec.DoubleValue modelScale;

        ClientConfig(final ForgeConfigSpec.Builder builder) {
            builder.push("general");
            clientBoolean = builder
                    .comment("An example boolean in the client config")
                    .translation(Parkour.MOD_ID + ".config.clientBoolean")
                    .define("clientBoolean", true);
            clientStringList = builder
                    .comment("An example list of Strings in the client config")
                    .translation(Parkour.MOD_ID + ".config.clientStringList")
                    .define("clientStringList", new ArrayList<>());
            clientDyeColorEnum = builder
                    .comment("An example DyeColor enum in the client config")
                    .translation(Parkour.MOD_ID + ".config.clientDyeColorEnum")
                    .defineEnum("clientDyeColorEnum", DyeColor.WHITE);

            modelTranslucency = builder
                    .comment("If the model should be rendered translucent")
                    .translation(Parkour.MOD_ID + ".config.modelTranslucency")
                    .define("modelTranslucency", true);
            modelScale = builder
                    .comment("The scale to render the model at")
                    .translation(Parkour.MOD_ID + ".config.modelScale")
                    .defineInRange("modelScale", 0.0625F, 0.0001F, 100F);
            builder.pop();
        }

        /*
        ClientConfig(final ForgeConfigSpec.Builder builder) {
            builder.push("general");
            clientBoolean = builder
                    .comment("An example boolean in the client config")
                    .translation(Parkour.MODID + ".config.clientBoolean")
                    .define("clientBoolean", true);
            clientStringList = builder
                    .comment("An example list of Strings in the client config")
                    .translation(Parkour.MODID + ".config.clientStringList")
                    .define("clientStringList", new ArrayList<>());
            clientDyeColorEnum = builder
                    .comment("An example DyeColor enum in the client config")
                    .translation(Parkour.MODID + ".config.clientDyeColorEnum")
                    .defineEnum("clientDyeColorEnum", DyeColor.WHITE);

            modelTranslucency = builder
                    .comment("If the model should be rendered translucent")
                    .translation(Parkour.MODID + ".config.modelTranslucency")
                    .define("modelTranslucency", true);
            modelScale = builder
                    .comment("The scale to render the model at")
                    .translation(Parkour.MODID + ".config.modelScale")
                    .defineInRange("modelScale", 0.0625F, 0.0001F, 100F);
            builder.pop();
        }
        */

    }
    public static final String MOD_ID = "kitchenparkour";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);



    //

    //

    // Registries

    //

    //

    // Register Blocks
    // TileEntity tileentity = new ChargableTile(TileEntityType.MOB_SPAWNER);cks
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Parkour.MOD_ID);
    // Custom Blocks

    // Working Example
    public static final RegistryObject<Block> CUSTOM_BLOCK = BLOCKS.register("custom_block",
            () -> new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON)
                    .hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)));

    // Ruby Blocks
    public static final RegistryObject<Block> RUBY_ORE = BLOCKS.register("ruby_ore",
            () -> new BlockBase(Block.Properties.create(Material.IRON, MaterialColor.OBSIDIAN)
                    .hardnessAndResistance(5.0F, 7.0F)
                    .sound(SoundType.STONE)
                    .harvestLevel(3).harvestTool(ToolType.PICKAXE))); // 0-3 = vanilla, 4 & 5 = upgrades to ruby pick

    public static final RegistryObject<Block> CHARGED_RUBY_BLOCK = BLOCKS.register("charged_ruby_block",
            () -> new BlockBase(Block.Properties.create(Material.IRON, MaterialColor.OBSIDIAN)
                    .hardnessAndResistance(7.0F, 16.0F)
                    .sound(SoundType.GLASS)
                    .harvestLevel(3).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block",
            () -> new ChargableBlockSunlight(Block.Properties.create(Material.IRON, MaterialColor.OBSIDIAN)
                    .hardnessAndResistance(6.0F, 13.0F)
                    .sound(SoundType.METAL)
                    .harvestLevel(3).harvestTool(ToolType.PICKAXE), CHARGED_RUBY_BLOCK.get().getDefaultState(), 6, 50));

    /*
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block",
            () -> new BlockBase(Block.Properties.create(Material.IRON, MaterialColor.OBSIDIAN)
                    .hardnessAndResistance(6.0F, 13.0F)
                    .sound(SoundType.METAL)
                    .harvestLevel(3).harvestTool(ToolType.PICKAXE)));

     */

    // Taydon Blocks
    public static final RegistryObject<Block> TAYDON_ORE = BLOCKS.register("taydon_ore",
            () -> new BlockBase(Block.Properties.create(Material.IRON, MaterialColor.OBSIDIAN)
                    .hardnessAndResistance(6.0F, 8.0F)
                    .sound(SoundType.STONE)
                    .harvestLevel(4).harvestTool(ToolType.PICKAXE))); // 0-3 = vanilla, 4 & 5 = upgrades to ruby pick

    public static final RegistryObject<Block> CHARGED_TAYDON_BLOCK = BLOCKS.register("charged_taydon_block",
            () -> new BlockBase(Block.Properties.create(Material.IRON, MaterialColor.OBSIDIAN)
                    .hardnessAndResistance(8.0F, 17.0F)
                    .sound(SoundType.GLASS)
                    .harvestLevel(4).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> TAYDON_BLOCK = BLOCKS.register("taydon_block",
            () -> new ChargableBlockSunlight(Block.Properties.create(Material.IRON, MaterialColor.OBSIDIAN)
                    .hardnessAndResistance(7.0F, 14.0F)
                    .sound(SoundType.METAL)
                    .harvestLevel(4).harvestTool(ToolType.PICKAXE), CHARGED_TAYDON_BLOCK.get().getDefaultState(), 0, 6));

    // Crafting Table
    public static final RegistryObject<Block> KPC_TABLE = BLOCKS.register("kpc_table",
            () -> new KPCTable(Block.Properties.from(Blocks.OBSIDIAN)));

    // Sauberite Block
    public static final RegistryObject<Block> SAUBERITE_BLOCK = BLOCKS.register("sauberite_block",
            () -> new SauberiteBlock(Block.Properties.from(Blocks.BEDROCK)));

    // Oberite Block
    public static final RegistryObject<Block> OBERITE_BLOCK = BLOCKS.register("oberite_block",
            () -> new BlockBase(Block.Properties.from(Blocks.OBSIDIAN)));

    /*
    public static final RegistryObject<Block> KPCT = BLOCKS.register("kpct",
            () -> new KPCT(Block.Properties.create(Material.IRON, MaterialColor.OBSIDIAN)
                    .hardnessAndResistance(5.0F, 16.0F).sound(SoundType.ANVIL)));
     */

    // !Register Blocks

    //

    //

    // Register Items
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Parkour.MOD_ID);
    // Custom Items

    // Crafting
    public static final RegistryObject<Item> KPC_TABLE_ITEM = ITEMS.register("kpc_table",
            () -> new ItemBlockBase(KPC_TABLE.get()));

    // Ruby
    public static final RegistryObject<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore",
            () -> new ItemBlockBase(RUBY_ORE.get()));

    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block",
            () -> new ItemBlockBase(RUBY_BLOCK.get()));

    public static final RegistryObject<Item> CHARGED_RUBY_BLOCK_ITEM = ITEMS.register("charged_ruby_block",
            () -> new ItemBlockBase(CHARGED_RUBY_BLOCK.get()));

    // Taydon
    public static final RegistryObject<Item> TAYDON_ORE_ITEM = ITEMS.register("taydon_ore",
            () -> new ItemBlockBase(TAYDON_ORE.get()));

    public static final RegistryObject<Item> TAYDON_BLOCK_ITEM = ITEMS.register("taydon_block",
            () -> new ItemBlockBase(TAYDON_BLOCK.get()));

    public static final RegistryObject<Item> CHARGED_TAYDON_BLOCK_ITEM = ITEMS.register("charged_taydon_block",
            () -> new ItemBlockBase(CHARGED_TAYDON_BLOCK.get()));

    // Sauberite
    public static final RegistryObject<Item> SAUBERITE_BLOCK_ITEM = ITEMS.register("sauberite_block",
            () -> new ItemBlockBase(SAUBERITE_BLOCK.get()));

    public static final RegistryObject<Item> OBERITE_BLOCK_ITEM = ITEMS.register("oberite_block",
            () -> new ItemBlockBase(OBERITE_BLOCK.get()));


    public static final RegistryObject<Item> RUBY_POWDER = ITEMS.register("ruby_powder",
            () -> new MineralBase(new Item.Properties().maxStackSize(64)));

    public static final RegistryObject<Item> RUBY_GEM = ITEMS.register("ruby_gem",
            () -> new MineralBase(new Item.Properties().maxStackSize(64)));

    public static final RegistryObject<Item> CHARGED_RUBY_GEM = ITEMS.register("charged_ruby_gem",
            () -> new MineralBase(new Item.Properties().maxStackSize(64)));


    public static final RegistryObject<Item> CHARGED_TAYDON_GEM = ITEMS.register("charged_taydon_gem",
            () -> new MineralBase(new Item.Properties().maxStackSize(64)));

    public static final RegistryObject<Item> TAYDON_GEM = ITEMS.register("taydon_gem",
            () -> new QuestHitChargeBase<>(CHARGED_TAYDON_GEM.get(), 20 * QUEST_MODIFIER));

    // Legendary Arsenal

    // wolf blade
    public static final RegistryObject<Item> WOLF_BLADE = ITEMS.register("wolf_blade",
            () -> new SpawnBlade(ItemTier.IRON, 8, -2.7F, new Item.Properties().maxDamage(400).addToolType(ToolType.AXE, 1), EntityType.WOLF, 280, 85, 2));

    public static final RegistryObject<Item> UNCHARGED_WOLF_BLADE = ITEMS.register("uncharged_wolf_blade",
            () -> new ItemBase(new Item.Properties().maxStackSize(1)) );

    public static final RegistryObject<Item> DEAD_BLADE_C = ITEMS.register("dead_blade_c",
            () -> new QuestHitChargeBase(UNCHARGED_WOLF_BLADE.get(), 85 * QUEST_MODIFIER));

    public static final RegistryObject<Item> DEAD_BLADE_B = ITEMS.register("dead_blade_b",
            () -> new QuestHitChargeBase(DEAD_BLADE_C.get(), 65 * QUEST_MODIFIER));

    public static final RegistryObject<Item> DEAD_BLADE_A = ITEMS.register("dead_blade_a",
            () -> new QuestHitChargeBase(DEAD_BLADE_B.get(), 45 * QUEST_MODIFIER));

    // bone blade
    public static final RegistryObject<Item> BONE_BLADE = ITEMS.register("bone_blade",
            () -> new SpawnBlade(ItemTier.STONE, 4, -2.7F, new Item.Properties().maxDamage(370).addToolType(ToolType.AXE, 1)
                    , EntityType.SKELETON, 250, 120, 3));

    // honey blade
    public static final RegistryObject<Item> VEX_BLADE = ITEMS.register("vex_blade",
            () -> new SpawnBlade(ItemTier.STONE, 5, -2.2F, new Item.Properties().maxDamage(770).addToolType(ToolType.SHOVEL, 1)
                    , EntityType.VEX, 300, 140, 6));

    public static final RegistryObject<Item> SHULKER_BLADE = ITEMS.register("shulker_blade",
            () -> new SpawnBlade(ItemTier.DIAMOND, 12, -2F, new Item.Properties().maxDamage(370).addToolType(ToolType.AXE, 1), EntityType.SHULKER, 95, 35, 3));


    // !Legendary Arsenal

    // // // // // // Ruby Tools // // // // // //

    public static final RegistryObject<Item> RUBY_PICK = ITEMS.register("ruby_pick",
            () -> AreaPickaxeBase.setSize( new AreaPickaxeBase(ItemTier.DIAMOND,
                            7, 1, new Item.Properties()
                            .addToolType(ToolType.PICKAXE, 4).maxStackSize(1).maxDamage(22400)),
                    3 ) );

    public static final RegistryObject<Item> UNLIT_SPARK = ITEMS.register("unlit_spark",
            () -> new QuestHitChargeBase(RUBY_PICK.get(), 45 * QUEST_MODIFIER));

    // // // // // // Solar Tools // // // // // //

    public static final RegistryObject<Item> SOLAR_PICK = ITEMS.register("solar_pick",
            () -> AreaPickaxeBase.setSize( new AreaPickaxeBase(ItemTier.DIAMOND,
                        7, 1, new Item.Properties()
                        .addToolType(ToolType.PICKAXE, 5).maxStackSize(1).maxDamage(57500)),
                    6 ) );

    public static final RegistryObject<Item> UNLIT_STAR = ITEMS.register("unlit_star",
            () -> new QuestHitChargeBase(SOLAR_PICK.get(), 70 * QUEST_MODIFIER));

    // // // // // // Parkour Grippers // // // // // //

    public static final RegistryObject<Item> PARKOUR_GRIPPER = ITEMS.register("parkour_gripper",
            () -> SwordBase.setAttackAndSpeed(new ParkourBase(0.7D),8, 0.85F)
                    .setCooldownTime(25).setWallLeapTime(10).setChargeTime(20));

    public static final RegistryObject<Item> EAGER_ARTIFACT = ITEMS.register("eager_artifact",
            () -> new QuestHitChargeBase(PARKOUR_GRIPPER.get(), 70 * QUEST_MODIFIER));

    // ultimate parkour gripper

    public static final RegistryObject<Item> ULTIMATE_PARKOUR_GRIPPER = ITEMS.register("ultimate_parkour_gripper",
            () -> SwordBase.setAttackAndSpeed(new ParkourBase(1.3D, 1.1D),12, 0.75F)
                    .setCooldownTime(30).setWallLeapTime(10).setChargeTime(20));

    public static final RegistryObject<Item> HUNGRY_STICK = ITEMS.register("hungry_stick",
            () -> new QuestHitChargeBase(ULTIMATE_PARKOUR_GRIPPER.get(), 95 * QUEST_MODIFIER));

    // ultimate parkour gripper v2

    public static final RegistryObject<Item> ULTIMATE_PARKOUR_GRIPPER_V2 = ITEMS.register("ultimate_parkour_gripper_v2",
            () -> SwordBase.setAttackAndSpeed(new ParkourBase(1.42D, 1.2D),16, 0.7F)
                    .setCooldownTime(25).setWallLeapTime(25).setChargeTime(25));

    public static final RegistryObject<Item> RAVENOUS_ROD = ITEMS.register("ravenous_rod",
            () -> new QuestHitChargeBase(ULTIMATE_PARKOUR_GRIPPER_V2.get(), 140 * QUEST_MODIFIER));

    // wings of order

    public static final RegistryObject<Item> GODS_WINGS = ITEMS.register("gods_wings",
            () -> SwordBase.setAttackAndSpeed(new ParkourBase(2D, 1.2D),18, 0.9F)
                    .setCooldownTime(50).setWallLeapTime(45).setChargeTime(25).setElytraFlyer());

    // blue fins

    public static final RegistryObject<Item> BLUE_FINS = ITEMS.register("blue_fins",
            () -> SwordBase.setAttackAndSpeed(new AquaParkour(1.45),13, 0.8F)
                    .setCooldownTime(65).setWallLeapTime(0).setChargeTime(0)); //2.3

    public static final RegistryObject<Item> MYSTICAL_FISH_BONE = ITEMS.register("mystical_fish_bone",
            () -> new QuestHitChargeBase(BLUE_FINS.get(), 80 * QUEST_MODIFIER));

    // super trident

    public static final RegistryObject<Item> SUPER_TRIDENT = ITEMS.register("super_trident",
            () -> new SuperTrident(new Item.Properties().maxStackSize(1).maxDamage(400).group(ItemGroup.COMBAT)));

    // Tokens of Valor

    public static final RegistryObject<Item> TOKEN_WARRIOR = ITEMS.register("token_warrior",
            () -> new TokenBase(25, TokenType.WARRIOR));

    public static final RegistryObject<Item> TOKEN_TANK = ITEMS.register("token_tank",
            () -> new TokenBase(25, TokenType.TANK));

    public static final RegistryObject<Item> TOKEN_SCOUT = ITEMS.register("token_scout",
            () -> new TokenBase(25, TokenType.SCOUT));

    // // // // // // Ruby Armor // // // // // //

    public static RubyMaterial RUBY_MATERIAL = new RubyMaterial();

    public static final RegistryObject<Item> RUBY_HELM = ITEMS.register("ruby_helm",
            () -> new RubyBase(RUBY_MATERIAL,
                    EquipmentSlotType.HEAD,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> RUBY_CHEST = ITEMS.register("ruby_chest",
            () -> new RubyBase(RUBY_MATERIAL,
                    EquipmentSlotType.CHEST,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> RUBY_LEGS = ITEMS.register("ruby_legs",
            () -> new RubyBase(RUBY_MATERIAL,
                    EquipmentSlotType.LEGS,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> RUBY_BOOTS = ITEMS.register("ruby_boots",
            () -> new RubyBase(RUBY_MATERIAL,
                    EquipmentSlotType.FEET,
                    new Item.Properties().maxDamage(200)));

    // // // // // // Solar Armor // // // // // //
    //MinecraftServer server;
    //ModLoader loader;
    GameData data;
    public static SolarMaterial SOLAR_MATERIAL = new SolarMaterial();

    public static final RegistryObject<Item> SOLAR_HELM = ITEMS.register("solar_helm",
            () -> new SolarBase(SOLAR_MATERIAL,
                    EquipmentSlotType.HEAD,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> SOLAR_CHEST = ITEMS.register("solar_chest",
            () -> new SolarBase(SOLAR_MATERIAL,
                    EquipmentSlotType.CHEST,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> SOLAR_LEGS = ITEMS.register("solar_legs",
            () -> new SolarBase(SOLAR_MATERIAL,
                    EquipmentSlotType.LEGS,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> SOLAR_BOOTS = ITEMS.register("solar_boots",
            () -> new SolarBase(SOLAR_MATERIAL,
                    EquipmentSlotType.FEET,
                    new Item.Properties().maxDamage(200)));

    // Taydon Armor

    public static TaydonMaterial TAYDON_MATERIAL = new TaydonMaterial();

    public static final RegistryObject<Item> TAYDON_HELM = ITEMS.register("taydon_helm",
            () -> new TaydonBase(TAYDON_MATERIAL,
                    EquipmentSlotType.HEAD,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> TAYDON_CHEST = ITEMS.register("taydon_chest",
            () -> new TaydonBase(TAYDON_MATERIAL,
                    EquipmentSlotType.CHEST,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> TAYDON_LEGS = ITEMS.register("taydon_legs",
            () -> new TaydonBase(TAYDON_MATERIAL,
                    EquipmentSlotType.LEGS,
                    new Item.Properties().maxDamage(200)));

    public static final RegistryObject<Item> TAYDON_BOOTS = ITEMS.register("taydon_boots",
            () -> new TaydonBase(TAYDON_MATERIAL,
                    EquipmentSlotType.FEET,
                    new Item.Properties().maxDamage(200)));

    // !Solar Armor

    // Register TileEntities

    public static final DeferredRegister<TileEntityType<?>> TET = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Parkour.MOD_ID);
    // Custom Blocks

    // Working Example

    public static final RegistryObject<TileEntityType<ChargableTile>> CHARGABLE_TE = TET.register("chargable_tile",
            () -> TileEntityType.Builder.create(ChargableTile::new, RUBY_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<ChargableTileTaydon>> CHARGABLE_TE_TAYDON = TET.register("chargable_tile_taydon",
            () -> TileEntityType.Builder.create(ChargableTileTaydon::new, TAYDON_BLOCK.get()).build(null));

    // !Register TileEntities

    // !Custom Items

    //

    //

    // Register Sounds
    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, Parkour.MOD_ID);

    // Custom Sounds
    public static final RegistryObject<SoundEvent> PARKOUR_GRIPPER_JUMP = SOUNDS.register("item.parkour_gripper_jump",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.parkour_gripper_jump")));
    public static final RegistryObject<SoundEvent> PARKOUR_GRIPPER_READY = SOUNDS.register("item.parkour_gripper_ready",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.parkour_gripper_ready")));
    public static final RegistryObject<SoundEvent> SWORD_ATTACK = SOUNDS.register("item.sword_attack",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.sword_attack")));
    public static final RegistryObject<SoundEvent> AQUA_PARKOUR_DASH = SOUNDS.register("item.aqua_parkour_dash",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.aqua_parkour_dash")));

    public static final RegistryObject<SoundEvent> KPC_TABLE_CRAFT = SOUNDS.register("block.kpc_table_craft",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "block.kpc_table_craft")));

    public static final RegistryObject<SoundEvent> BLOCK_CHARGE_SOUND = SOUNDS.register("block.block_charge_sound",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "block.block_charge_sound")));

    public static final RegistryObject<SoundEvent> QUEST_COMPLETE = SOUNDS.register("item.quest_complete",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.quest_complete")));
    // !Custom Sounds

    // Recipes

    public static final DeferredRegister<ContainerType<?>> MOD_CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Parkour.MOD_ID);
    //public static final RegistryObject<ContainerType<?>> KPC_CONTAINER = MOD_CONTAINERS.register("kpc_table", () -> new KPCContainer().getType());


    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, Parkour.MOD_ID);

    public static final RegistryObject<KPCShapedRecipe.Serializer> KPC_SHAPED = RECIPE_SERIALIZERS.register("kpc_crafting_shaped",
            () -> new KPCShapedRecipe.Serializer());

    public static final RegistryObject<KShapelessRecipe.Serializer> KPC_SHAPELESS = RECIPE_SERIALIZERS.register("kpc_crafting_shapeless",
            () -> new KShapelessRecipe.Serializer());

    //public static final RegistryObject<ShapedRecipe.Serializer> KPC_SHAPED = RECIPE_TYPES.register("kpc_shaped", () -> new ShapedRecipe.Serializer());

    // !Recipes

    //

    //

    // Enchantments

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = new DeferredRegister<>(ForgeRegistries.ENCHANTMENTS, Parkour.MOD_ID);

    public static final RegistryObject<Enchantment> SUN_BLESSING = ENCHANTMENTS.register("sun_blessing",
            () -> new SunBlessing(Enchantment.Rarity.RARE,
                    EnchantmentType.create("sun_blessing_filter", item -> item == SOLAR_LEGS.get() ),
                    new EquipmentSlotType[] { EquipmentSlotType.LEGS, EquipmentSlotType.CHEST } ));

    public static final RegistryObject<Enchantment> STEALTH = ENCHANTMENTS.register("stealth",
            () -> new Stealth(Enchantment.Rarity.RARE,
                    EnchantmentType.create("stealth_filter", item -> item == TAYDON_BOOTS.get()),
                    new EquipmentSlotType[] { EquipmentSlotType.FEET } ));



    /*
    public static final RegistryObject<KPCShaped.Serializer> SUN_BLESSING = ENCHANTMENTS.register("sun_blessing",
            () -> new SunBlessing(Enchantment.Rarity.UNCOMMON, EnchantmentType.create("carrot", item -> item.equals(Items.IRON_CHESTPLATE))));
     */
    /*
    public static final RegistryObject<Enchantment> SUN_BLESSING = ENCHANTMENTS.register("sun_blessing",
            () -> new SunBlessing(Enchantment.Rarity.COMMON,
                    EnchantmentType.create("solar_enchantment", Predicate.isEqual(SolarBase.class) ),
                    new EquipmentSlotType[] { EquipmentSlotType.LEGS, EquipmentSlotType.CHEST } ));
     */
    //working single player
    /*
    public static final RegistryObject<Enchantment> SUN_BLESSING = ENCHANTMENTS.register("sun_blessing",
            () -> new SunBlessing(Enchantment.Rarity.RARE,
                    EnchantmentType.create("solar", item -> item.equals(Items.DIAMOND_CHESTPLATE)),
                    new EquipmentSlotType[] { EquipmentSlotType.FEET } ));

    public static final RegistryObject<Enchantment> STEALTH = ENCHANTMENTS.register("stealth",
            () -> new Stealth(Enchantment.Rarity.VERY_RARE,
                    EnchantmentType.create("night_shade", item -> item.equals(Items.DIAMOND_BOOTS)),
                    new EquipmentSlotType[] { EquipmentSlotType.FEET } ));
     */

    /*
    public static final RegistryObject<Enchantment> STEALTH = ENCHANTMENTS.register("sun_blessing",
            () -> new SunBlessing(Enchantment.Rarity.COMMON,
                    EnchantmentType.create("stealth_enchantment", Predicate.isEqual(NSBase.class) ),
                    new EquipmentSlotType[] { EquipmentSlotType.FEET } ));
     */

    // !Enchantments

    // !Registries

    //

    //

    public static final ForgeConfigSpec CLIENT_SPEC;
    static final ClientConfig CLIENT;
    static {
        {
            final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
            CLIENT = specPair.getLeft();
            CLIENT_SPEC = specPair.getRight();
        }
    }

    public Parkour() {

        //ShapedRecipe.setCraftingSize(5, 5);

        // Register Armor Materials

        RubyMaterial.doInitStuff();
        SolarMaterial.doInitStuff();
        TaydonMaterial.doInitStuff();

        // !Register Armor Materials

        LOGGER.debug("Its about to get waaay more OP...");

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register Configs (Does not need to be after Deferred Registers)
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);


        // Register Deferred Registers (Does not need to be before Configs)
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        // Sound Register
        SOUNDS.register(modEventBus);

        RECIPE_SERIALIZERS.register(modEventBus);

        ENCHANTMENTS.register(modEventBus);

        TET.register(modEventBus);

        modEventBus.addListener(this::commonSetup); // for structures

        MinecraftForge.EVENT_BUS.register(this);
    }

    // Structures

    // KPC Forge

    public static final ResourceLocation KPC_FORGE_LOC = new ResourceLocation(MOD_ID, "kpc_forge");

    public static IStructurePieceType KPC_FORGE_PIECE = null;

    @ObjectHolder(MOD_ID + ":kpc_forge")
    public static Structure<NoFeatureConfig> KPC_FORGE; // = new KPCForgeBlob(NoFeatureConfig::deserialize); // Structure<NoFeatureConfig>

    // KPC Ruin

    public static final ResourceLocation KPC_RUIN_A_LOC = new ResourceLocation(MOD_ID, "kpc_pylon");

    public static IStructurePieceType KPC_RUIN_A_PIECE = null;

    @ObjectHolder(MOD_ID + ":kpc_pylon")
    public static Structure<NoFeatureConfig> KPC_RUIN_A;

    public static final ResourceLocation KPC_RUIN_B_LOC = new ResourceLocation(MOD_ID, "kpc_spawner");

    public static IStructurePieceType KPC_RUIN_B_PIECE = null;

    @ObjectHolder(MOD_ID + ":kpc_spawner")
    public static Structure<NoFeatureConfig> KPC_RUIN_B;

    public static final ResourceLocation KPC_FA_LOC = new ResourceLocation(MOD_ID, "kpc_hallway");

    public static IStructurePieceType KPC_FA_PIECE = null;

    @ObjectHolder(MOD_ID + ":kpc_hallway")
    public static Structure<NoFeatureConfig> KPC_FA;

    public void commonSetup(FMLCommonSetupEvent args) {
        DeferredWorkQueue.runLater(() -> {
            Iterator<Biome> biomes = ForgeRegistries.BIOMES.iterator();
            biomes.forEachRemaining((biome) -> {
                // KPC Forge
                if (biome.getRegistryName().toString().toLowerCase().contains("ocean") || biome == Biomes.DEEP_COLD_OCEAN || biome == Biomes.COLD_OCEAN || biome == Biomes.FROZEN_OCEAN
                        || biome == Biomes.DEEP_FROZEN_OCEAN

                        || biome == Biomes.BEACH

                        || biome == Biomes.DEEP_WARM_OCEAN || biome == Biomes.WARM_OCEAN || biome == Biomes.DEEP_OCEAN
                        || biome == Biomes.LUKEWARM_OCEAN || biome == Biomes.LUKEWARM_OCEAN) {

                    biome.addStructure(KPC_FORGE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
                    biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
                            KPC_FORGE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                                    .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

                }
                // KPC Ruin
                if (biome == Biomes.DARK_FOREST || biome == Biomes.DARK_FOREST_HILLS

                        || biome == Biomes.MOUNTAIN_EDGE || biome == Biomes.MOUNTAINS || biome == Biomes.WOODED_MOUNTAINS) {

                    //biome.addStructure(KPC_RUIN_A.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
                    //biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
                    //        KPC_RUIN_A.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    //                .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

                }
            });
        });
    }

    // !Structures


    // entities

    public static Item SUPER_DROWNED_ENTITY_EGG = null;

    // !entities
}
