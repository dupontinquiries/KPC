package media.kitchen.parkour;

import media.kitchen.parkour.itemtype.token.capabilities.ParkourScoutCapability;
import media.kitchen.parkour.itemtype.token.capabilities.ParkourTankCapability;
import media.kitchen.parkour.itemtype.token.capabilities.ParkourWarriorCapability;
import media.kitchen.parkour.itemtype.token.TokenType;
import media.kitchen.parkour.world.ore.OberiteGen;
import media.kitchen.parkour.world.ore.RubyOreGen;
import media.kitchen.parkour.world.ore.TaydonOreGen;
import media.kitchen.parkour.world.structure.KPCForgeBlob;
import media.kitchen.parkour.world.structure.KPCForgeBlobPiece;
import media.kitchen.parkour.world.structure.KPCNBTStruc;
import media.kitchen.parkour.world.structure.KPCNBTStrucPiece;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid=Parkour.MOD_ID)
public class ModBusEvents {

    //register .nbt files

    //public static ResourceLocation KPC_STRONGHOLD = new ResourceLocation(Parkour.MOD_ID, "kpc_stronghold");

    // structures

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> args) {

        //Parkour.KPC_FORGE = new KPCForgeBlob(NoFeatureConfig::deserialize);

        //args.getRegistry().register(Parkour.KPC_FORGE.setRegistryName(Parkour.KPC_FORGE_LOC));

        // KPC Forge
        Parkour.KPC_FORGE_PIECE = Registry.register(Registry.STRUCTURE_PIECE, Parkour.KPC_FORGE_LOC, KPCForgeBlobPiece.Piece::new);
        args.getRegistry().register(new KPCForgeBlob(NoFeatureConfig::deserialize).setRegistryName(Parkour.KPC_FORGE_LOC));

        // KPC Ruin
        Parkour.KPC_RUIN_A_PIECE = Registry.register(Registry.STRUCTURE_PIECE, Parkour.KPC_RUIN_A_LOC, KPCNBTStrucPiece.Piece::new);
        args.getRegistry().register(new KPCNBTStruc(NoFeatureConfig::deserialize).setRegistryName(Parkour.KPC_RUIN_A_LOC));

        Parkour.KPC_RUIN_B_PIECE = Registry.register(Registry.STRUCTURE_PIECE, Parkour.KPC_RUIN_B_LOC, KPCNBTStrucPiece.Piece::new);
        args.getRegistry().register(new KPCNBTStruc(NoFeatureConfig::deserialize).setRegistryName(Parkour.KPC_RUIN_B_LOC));

        Parkour.KPC_FA_PIECE = Registry.register(Registry.STRUCTURE_PIECE, Parkour.KPC_FA_LOC, KPCNBTStrucPiece.Piece::new);
        args.getRegistry().register(new KPCNBTStruc(NoFeatureConfig::deserialize).setRegistryName(Parkour.KPC_FA_LOC));

        /*
        Parkour.KPC_FORGE_AREA_FORGE = Registry.register(Registry.STRUCTURE_PIECE,
                Parkour.KPC_FORGE_LOC,
                KPCForgeBlobPiece.Piece::new);
        args.getRegistry().register(new KPCForgeBlob(NoFeatureConfig::deserialize).setRegistryName(Parkour.KPC_FORGE_LOC));
         */
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
        RubyOreGen.generateOre();
        TaydonOreGen.generateOre();
        OberiteGen.generateOre();
    }

    /*
    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> args) {
        Parkour.BRICK_HOUSE_PIECE = Registry.register(Registry.STRUCTURE_PIECE, Parkour.HOUSE_LOC, BrickHousePiece.Piece::new);
        args.getRegistry().register(new BrickHouse(NoFeatureConfig::deserialize).setRegistryName(Parkour.HOUSE_LOC));
    }
     */

    // !structures

    // Tile Entities

    /*
    @SubscribeEvent
    public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt) {
        TileEntityType<?> type = TileEntityType.Builder.create(factory, validBlocks).build(null);
        type.setRegistryName(Parkour.MOD_ID, "chargabletile");
        evt.getRegistry().register(type);
    }
     */


    // !Tile Entities

    // mob spawns


    /*
    @SubscribeEvent
    public void entityJoinWorldEvent(EntityJoinWorldEvent e) {
        System.out.println("detected spawn");
        if (e.getEntity() instanceof CreeperEntity) {
            System.out.println("spawned creeper");
            if ( e.getWorld().rand.nextDouble() < 1 ) {
                //e.getEntity().setInvisible(true);
                System.out.println("injecting creeper");
                ( (CreeperEntity) e.getEntity() ).addPotionEffect(new EffectInstance(Effects.SPEED, 15*60, 1));;
            }
        }
    }
    */


    // !mob spawns

    // tile entities

    /*
    @SubscribeEvent
    public void renderTiles(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(Parkour.CHARGABLE_TE.get(), ChargableTile::new);
        ClientRegistry.bindTileEntityRenderer(Parkour.CHARGABLE_TE_TAYDON.get(), ChargableTileTaydon::new);
    }
     */

    // !tile entities

    // capabilities

    @SubscribeEvent
    public void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(Parkour.MOD_ID, TokenType.WARRIOR.getId() + "_cap"), new ParkourWarriorCapability());
            event.addCapability(new ResourceLocation(Parkour.MOD_ID, TokenType.TANK.getId() + "_cap"), new ParkourTankCapability());
            event.addCapability(new ResourceLocation(Parkour.MOD_ID, TokenType.SCOUT.getId() + "_cap"), new ParkourScoutCapability());
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        //Register the Currency capability
        ParkourWarriorCapability.register();
        ParkourTankCapability.register();
        ParkourScoutCapability.register();

    }

    // !capabilities

}