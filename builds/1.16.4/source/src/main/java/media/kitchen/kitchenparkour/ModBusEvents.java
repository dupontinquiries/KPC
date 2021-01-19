package media.kitchen.kitchenparkour;

import media.kitchen.kitchenparkour.itemtype.token.capabilities.ParkourScoutCapability;
import media.kitchen.kitchenparkour.itemtype.token.capabilities.ParkourTankCapability;
import media.kitchen.kitchenparkour.itemtype.token.capabilities.ParkourWarriorCapability;
import media.kitchen.kitchenparkour.itemtype.token.TokenType;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid=Parkour.MOD_ID)
public class ModBusEvents {

    // structures

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> args) {
        // register structures
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
        // register ores
    }

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

    // mob events
    @SubscribeEvent
    public void entityJoinWorldEvent(EntityJoinWorldEvent e) {
        System.out.println("detected spawn");
        if (e.getEntity() instanceof CreeperEntity) {
            System.out.println("spawned creeper");
            if ( e.getWorld().rand.nextDouble() < 1 ) {
                System.out.println("injecting creeper");
                ( (CreeperEntity) e.getEntity() ).addPotionEffect(new EffectInstance(
                        Effects.SPEED, 20*60*10, 1));;
            }
        }
    }

    // data model/texture media.kitchen.kitchenparkour.gen
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        Parkour.LOGGER.debug("Starting server side data generators");
        DataGenerator generator = event.getGenerator();
        /*
        if (event.includeServer())
        {
            generator.addProvider(new RecipesDataGen(generator));
            //generator.addProvider(new LootTablesDataGen(generator));
            generator.addProvider(new BlockTagsDataGen(generator));
            generator.addProvider(new ItemTagsDataGen(generator));
        }

        if (event.includeClient())
        {
            Parkour.LOGGER.debug("Starting client side data generators");
            //generator.addProvider(new BlockStatesDataGen(generator, Parkour.MOD_ID, event.getExistingFileHelper()));
            //generator.addProvider(new ItemModelsDataGen(generator, Parkour.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new LanguagesDataGen(generator, Parkour.MOD_ID));
        }
         */
    }

}