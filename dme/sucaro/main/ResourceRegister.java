package dme.sucaro.main;

import net.minecraft.util.ResourceLocation;
/*import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;*/

public class ResourceRegister {

	public static final ResourceLocation Custom_Chest_Loot = register("dme.sucaro.loot:LootMap");

	public static void registerResources() {

	}

	private static ResourceLocation register(String id) {
		return null;
		//return LootTableList.register(new ResourceLocation(Main.MODID, id));
	}

}
