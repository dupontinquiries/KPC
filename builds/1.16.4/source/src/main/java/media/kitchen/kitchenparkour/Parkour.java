package media.kitchen.kitchenparkour;

import media.kitchen.kitchenparkour.gen.world.FeatureGen;
import media.kitchen.kitchenparkour.blocktype.tileentity.ChargableTile;
import media.kitchen.kitchenparkour.blocktype.tileentity.ChargableTileTaydon;
import media.kitchen.kitchenparkour.crafting.kpctable.KPCContainer;
import media.kitchen.kitchenparkour.enchantment.*;
import media.kitchen.kitchenparkour.itemtype.ItemBase;
import media.kitchen.kitchenparkour.itemtype.ItemBlockBase;
import media.kitchen.kitchenparkour.itemtype.MineralBase;
import media.kitchen.kitchenparkour.itemtype.armor.ruby.RubyBase;
import media.kitchen.kitchenparkour.itemtype.armor.ruby.RubyMaterial;
import media.kitchen.kitchenparkour.itemtype.armor.solar.SolarBase;
import media.kitchen.kitchenparkour.itemtype.armor.solar.SolarMaterial;
import media.kitchen.kitchenparkour.itemtype.armor.taydon.TaydonBase;
import media.kitchen.kitchenparkour.itemtype.armor.taydon.TaydonMaterial;
import media.kitchen.kitchenparkour.itemtype.parkour.ParkourBase;
import media.kitchen.kitchenparkour.itemtype.quest.QuestHitChanceToInsta;
import media.kitchen.kitchenparkour.itemtype.tools.*;
import media.kitchen.kitchenparkour.itemtype.tools.bending.EarthWielder;
import media.kitchen.kitchenparkour.itemtype.tools.bending.FireStaff;
import media.kitchen.kitchenparkour.itemtype.tools.bending.LavaWielder;
import media.kitchen.kitchenparkour.itemtype.tools.mining.AreaPickaxeBase;
import media.kitchen.kitchenparkour.itemtype.tools.spawnblade.SpawnBlade;
import media.kitchen.kitchenparkour.blocktype.BlockBase;
import media.kitchen.kitchenparkour.blocktype.ChargableBlockSunlight;
import media.kitchen.kitchenparkour.blocktype.SauberiteBlock;
import media.kitchen.kitchenparkour.crafting.KPCShapedRecipe;
import media.kitchen.kitchenparkour.crafting.KShapelessRecipe;
import media.kitchen.kitchenparkour.crafting.kpctable.KPCTable;
import media.kitchen.kitchenparkour.itemtype.parkour.AquaParkour;
import media.kitchen.kitchenparkour.itemtype.quest.QuestHitChargeBase;
import media.kitchen.kitchenparkour.itemtype.token.TokenBase;
import media.kitchen.kitchenparkour.itemtype.token.TokenType;
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
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static final String MOD_ID = "kitchenparkour";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);



    //

    //

    // Registries

    //

    //

    // Register Blocks
    // TileEntity tileentity = new ChargableTile(TileEntityType.MOB_SPAWNER);cks
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID); //new DeferredRegister<>(ForgeRegistries.BLOCKS, Parkour.MOD_ID);
    // Custom Blocks

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

    // Register recipe book

    //public static final DeferredRegister<RecipeBookCategory> RBOOKS = DeferredRegister.create(ForgeRegistries., MOD_ID); //new DeferredRegister<>(ForgeRegistries.BLOCKS, Parkour.MOD_ID);

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
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID); //new DeferredRegister<>(ForgeRegistries.ITEMS, Parkour.MOD_ID);
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
            () -> new QuestHitChanceToInsta<>(CHARGED_TAYDON_GEM.get(), 7 * QUEST_MODIFIER).setChance(0.7F));

    // Legendary Arsenal

    // wolf blade
    public static final RegistryObject<Item> WOLF_BLADE = ITEMS.register("wolf_blade",
            () -> new SpawnBlade(ItemTier.DIAMOND, 15, -2.7F, new Item.Properties().maxDamage(400).addToolType(ToolType.AXE, 2), EntityType.WOLF, 350, 85, 2));

    // bending

    public static final RegistryObject<Item> FIRE_STAFF = ITEMS.register("fire_staff",
            () -> new FireStaff(ToolTiers.EARTH_WIELDER, 3, -0.7F, new Item.Properties()));

    public static final RegistryObject<Item> EARTH_WIELDER = ITEMS.register("earth_wielder",
            () -> new EarthWielder(ToolTiers.EARTH_WIELDER, 3, -0.7F, new Item.Properties()));

    public static final RegistryObject<Item> LIFELESS_EARTH_WIELDER = ITEMS.register("lifeless_earth_wielder",
            () -> new QuestHitChargeBase(EARTH_WIELDER.get(), 70 * QUEST_MODIFIER));

    public static final RegistryObject<Item> LAVA_WIELDER = ITEMS.register("lava_wielder",
            () -> new LavaWielder(ToolTiers.LAVA_WIELDER, 5, -0.7F, new Item.Properties()));

    public static final RegistryObject<Item> LIFELESS_LAVA_WIELDER = ITEMS.register("lifeless_lava_wielder",
            () -> new QuestHitChargeBase(LAVA_WIELDER.get(), 70 * QUEST_MODIFIER));

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
            () -> new SpawnBlade(ItemTier.STONE, 7, -2.7F, new Item.Properties().maxDamage(370).addToolType(ToolType.AXE, 1)
                    , EntityType.SKELETON, 250, 120, 3));

    // honey blade
    public static final RegistryObject<Item> VEX_BLADE = ITEMS.register("vex_blade",
            () -> new SpawnBlade(ItemTier.STONE, 8, -2.2F, new Item.Properties().maxDamage(770).addToolType(ToolType.SHOVEL, 1)
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
            () -> new ParkourBase(0.8D, 0.8D) //, 1.1D)
                    .setCooldownTime(25).setWallLeapTime(10).setChargeTime(20));

    public static final RegistryObject<Item> EAGER_ARTIFACT = ITEMS.register("eager_artifact",
            () -> new QuestHitChargeBase(PARKOUR_GRIPPER.get(), 70 * QUEST_MODIFIER));

    // ultimate parkour gripper

    public static final RegistryObject<Item> ULTIMATE_PARKOUR_GRIPPER = ITEMS.register("ultimate_parkour_gripper",
            () -> new ParkourBase(1.45D, 1.2D) //, 0D, 1.0D)
                    .setCooldownTime(30).setWallLeapTime(10).setChargeTime(20));

    public static final RegistryObject<Item> HUNGRY_STICK = ITEMS.register("hungry_stick",
            () -> new QuestHitChargeBase(ULTIMATE_PARKOUR_GRIPPER.get(), 95 * QUEST_MODIFIER));

    // ultimate parkour gripper v2

    public static final RegistryObject<Item> ULTIMATE_PARKOUR_GRIPPER_V2 = ITEMS.register("ultimate_parkour_gripper_v2",
            () -> new ParkourBase(1.7D, 1.4D) //, 0D, 1.0D)
                    .setCooldownTime(25).setWallLeapTime(25).setChargeTime(25));

    public static final RegistryObject<Item> RAVENOUS_ROD = ITEMS.register("ravenous_rod",
            () -> new QuestHitChargeBase(ULTIMATE_PARKOUR_GRIPPER_V2.get(), 140 * QUEST_MODIFIER));

    // wings of order

    public static final RegistryObject<Item> GODS_WINGS = ITEMS.register("gods_wings",
            () -> new ParkourBase(2D, 1.2D) //, 0D, 1.1D) //SwordBase.setAttackAndSpeed(new ParkourBase(2D, 1.2D),18, 0.9F)
                    .setCooldownTime(50).setWallLeapTime(45).setChargeTime(25).setElytraFlyer());

    // blue fins

    public static final RegistryObject<Item> BLUE_FINS = ITEMS.register("blue_fins",
            () -> new AquaParkour(1.45D) //, 0D, 1.0D)
                    .setCooldownTime(65).setWallLeapTime(0).setChargeTime(0)); //2.3

    public static final RegistryObject<Item> MYSTICAL_FISH_BONE = ITEMS.register("mystical_fish_bone",
            () -> new QuestHitChargeBase(BLUE_FINS.get(), 80 * QUEST_MODIFIER));

    // Tokens of Valor

    public static final RegistryObject<Item> TOKEN_WARRIOR = ITEMS.register("token_warrior",
            () -> new TokenBase(25, TokenType.WARRIOR));

    public static final RegistryObject<Item> TOKEN_TANK = ITEMS.register("token_tank",
            () -> new TokenBase(25, TokenType.TANK));

    public static final RegistryObject<Item> TOKEN_SCOUT = ITEMS.register("token_scout",
            () -> new TokenBase(25, TokenType.SCOUT));

    public static final RegistryObject<Item> TOKEN_ALMIGHTY = ITEMS.register("token_almighty",
            () -> new TokenBase(25, TokenType.ALMIGHTY));

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

    public static final DeferredRegister<TileEntityType<?>> TET = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID); //new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Parkour.MOD_ID);
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
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID); //new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, Parkour.MOD_ID);

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

    // bending

    public static final RegistryObject<SoundEvent> FIREBALL_SOUND = SOUNDS.register("item.fireball",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.fireball")));

    public static final RegistryObject<SoundEvent> EARTH_WIELD_LIFT = SOUNDS.register("item.ew_lift",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.ew_lift")));

    public static final RegistryObject<SoundEvent> EARTH_WIELD_WINDUP = SOUNDS.register("item.ew_windup",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.ew_windup")));

    public static final RegistryObject<SoundEvent> QUEST_COMPLETE = SOUNDS.register("item.quest_complete",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.quest_complete")));
    // !Custom Sounds

    // Recipes

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID); //new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, Parkour.MOD_ID);

    public static final RegistryObject<KPCShapedRecipe.Serializer> KPC_SHAPED = RECIPE_SERIALIZERS.register("kpc_crafting_shaped",
            () -> new KPCShapedRecipe.Serializer());

    public static final RegistryObject<KShapelessRecipe.Serializer> KPC_SHAPELESS = RECIPE_SERIALIZERS.register("kpc_crafting_shapeless",
            () -> new KShapelessRecipe.Serializer());

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);

    public static final RegistryObject<ContainerType<?>> KPC_CRAFTING_CONTAINER = CONTAINER_TYPES.register("kpc_crafting_container",
            () -> IForgeContainerType.create((windowId, inv, extra) -> {
                return new KPCContainer(windowId, inv);
            }));

    /*
    ((windowId, inv, data) -> {
                World world = inv.player.getEntityWorld();
                // ((PacketBuffer) data).readBlockPos()
                return new KPCContainer(windowId, inv, IWorldPosCallable.of(world, inv.player.getPosition()));
            })
    */

    //public static final DeferredRegister<ContainerScreen<?>> CONTAINER_SCREENS = DeferredRegister.create(ForgeRegistries., MOD_ID);
    //public static final ContainerType<KPCContainer> KPC_CRAFTING = CustomContainerType.register("kpc_crafting", KPCContainer::new);


    // !Recipes

    //

    //

    // Enchantments

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MOD_ID); //new DeferredRegister<>(ForgeRegistries.ENCHANTMENTS, Parkour.MOD_ID);

    public static final RegistryObject<Enchantment> SUN_BLESSING = ENCHANTMENTS.register("sun_blessing",
            () -> new SunBlessing(Enchantment.Rarity.RARE,
                    EnchantmentType.create("sun_blessing_filter", item -> item == SOLAR_LEGS.get() ),
                    new EquipmentSlotType[] { EquipmentSlotType.LEGS } ));

    public static final RegistryObject<Enchantment> AQUA_BLESSING = ENCHANTMENTS.register("aqua_blessing",
            () -> new AquaBlessing(Enchantment.Rarity.VERY_RARE,
                    EnchantmentType.create("aqua_blessing_filter", item -> item == BLUE_FINS.get()),
                    new EquipmentSlotType[] { EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND } ));

    public static final RegistryObject<Enchantment> STEALTH = ENCHANTMENTS.register("stealth",
            () -> new Stealth(Enchantment.Rarity.RARE,
                    EnchantmentType.create("stealth_filter", item -> item == TAYDON_BOOTS.get()),
                    new EquipmentSlotType[] { EquipmentSlotType.FEET } ));

    public static final RegistryObject<Enchantment> SUN_WALKER = ENCHANTMENTS.register("sun_walker",
            () -> new SunWalker(Enchantment.Rarity.VERY_RARE,
                    EnchantmentType.create("sun_walker_filter", item -> item == SOLAR_BOOTS.get()),
                    new EquipmentSlotType[] { EquipmentSlotType.FEET } ));

    public static final RegistryObject<Enchantment> REAPER = ENCHANTMENTS.register("reaper",
            () -> new Reaper(Enchantment.Rarity.VERY_RARE,
                    EnchantmentType.create("reaper_filter", item -> item == TAYDON_HELM.get()),
                    new EquipmentSlotType[] { EquipmentSlotType.HEAD } ));

    public static final RegistryObject<Enchantment> SCORCHED_EARTH = ENCHANTMENTS.register("scorched_earth",
            () -> new SchorchedEarthEnchantment(Enchantment.Rarity.VERY_RARE,
                    EnchantmentType.create("scorched_earth_filter", item -> item == LAVA_WIELDER.get()),
                    new EquipmentSlotType[] { EquipmentSlotType.MAINHAND } ));

    // !Enchantments

    // !Registries

    //

    //

    public Parkour() {

        //ShapedRecipe.setCraftingSize(5, 5);

        // Register Armor Materials

        RubyMaterial.doInitStuff();
        SolarMaterial.doInitStuff();
        TaydonMaterial.doInitStuff();

        // !Register Armor Materials

        LOGGER.debug("Its about to get waaay more fun...");

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register Deferred Registers (Does not need to be before Configs)
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        // Sound Register
        SOUNDS.register(modEventBus);

        RECIPE_SERIALIZERS.register(modEventBus);

        ENCHANTMENTS.register(modEventBus);

        TET.register(modEventBus);

        CONTAINER_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, FeatureGen::addFeaturesToBiomes); // for ores

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

    }

    // !Structures


    // entities

    public static Item SUPER_DROWNED_ENTITY_EGG = null;

    // !entities
}
