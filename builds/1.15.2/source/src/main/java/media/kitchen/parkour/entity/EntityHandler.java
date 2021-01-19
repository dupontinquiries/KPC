package media.kitchen.parkour.entity;

import media.kitchen.parkour.Parkour;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class EntityHandler {
    public static EntityType<?> SUPER_DROWNED = EntityType.Builder.create(SuperDrowned::new, EntityClassification.MONSTER)
            .build(Parkour.MOD_ID + ":super_drowned_entity").setRegistryName(new ResourceLocation(Parkour.MOD_ID, "super_drowned_entity"));

    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll
                (
                        Parkour.SUPER_DROWNED_ENTITY_EGG = registerEntitySpawnEgg(SUPER_DROWNED, 0xf0f0f0, 0xdf51f5, "super_drowned_entity_egg")
                );
    }

    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(Parkour.KPC_TAB));
        item.setRegistryName(new ResourceLocation(Parkour.MOD_ID, name));
        return item;
    }
}
