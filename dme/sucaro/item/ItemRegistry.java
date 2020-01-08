package dme.sucaro.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public final class ItemRegistry {
	
	public static ToolMaterial higgs = EnumHelper.addToolMaterial("Higgs", 2, 11000, 5, 40, 20);
	public static ToolMaterial tragfringe = EnumHelper.addToolMaterial("tragfringe", 1, 11000, 2, 13, 20); // 20, 50
	public static ToolMaterial tragparkour = EnumHelper.addToolMaterial("tragparkour", 1, 11000, 2, 1, 20);

	public static ArmorMaterial tragarmor = EnumHelper.addArmorMaterial("tragarmor", 20000,
			new int[] { 12, 12, 12, 12 }, 20);

	public static Item ripper = new ItemRipper(tragfringe).setTextureName("sucaro:ripper")
			.setCreativeTab(CreativeTabs.tabCombat).setUnlocalizedName("ripper");
	
	public static Item parkour = new ItemParkour(tragparkour).setTextureName("sucaro:parkour")
			.setCreativeTab(CreativeTabs.tabCombat).setUnlocalizedName("parkour");

	public static GodArmor forcechestplate = new GodArmor(tragarmor, (int) 1, (int) 1);

	public static Item chainingGun = new chaingingGun().setTextureName("sucaro:chainingGun")
			.setUnlocalizedName("chainingGun").setCreativeTab(CreativeTabs.tabCombat);
	public static Item proxGun = new proxGun().setTextureName("sucaro:proxGun").setUnlocalizedName("proxGun")
			.setCreativeTab(CreativeTabs.tabCombat);
	public static Item moonArrow = new Item().setTextureName("sucaro:moonArrow").setUnlocalizedName("moonArrow")
			.setCreativeTab(CreativeTabs.tabCombat);
	public static Item neutronGrenade = new neutronGrenade().setTextureName("sucaro:neutronGrenade")
			.setUnlocalizedName("neutronGrenade").setCreativeTab(CreativeTabs.tabCombat);
	public static Item spreadLauncher = new spreadLauncher().setTextureName("sucaro:spreadLauncher")
			.setUnlocalizedName("spreadLauncher").setCreativeTab(CreativeTabs.tabCombat);
	public static Item beamer = new beamer().setTextureName("sucaro:beamer").setUnlocalizedName("beamer")
			.setCreativeTab(CreativeTabs.tabCombat);

	public static Item excavator = new ItemExcavator(higgs).setTextureName("sucaro:excavator").setUnlocalizedName("Excavator")
			.setCreativeTab(CreativeTabs.tabTools);

	public static Item higgsStaff = new GravityOre().setTextureName("sucaro:higgsStaff")
			.setUnlocalizedName("Higgs Staff").setCreativeTab(CreativeTabs.tabTools);

	public static Item gravOre = new GravityOre().setTextureName("sucaro:gravore").setUnlocalizedName("Gravity Ore")
			.setCreativeTab(CreativeTabs.tabCombat);

	// public static DamageSource Arc = new
	// DamageSource("arc").func_76348_h().func_76359_i().func_76351_m();

	// guns

	public static Item MGMk1 = new ItemGun(02, 3.0, 0, 0.007, 1.5, 3.0f, 0, 01, 0, 45, Items.iron_ingot, "smoke", false,
			false, 0.0).setTextureName("sucaro:MG1").setUnlocalizedName("MG Mk. 1");
	public static Item MGMk2 = new ItemGun(07, 4.0, 1, 0.005, 0.5, 3.0f, 0, 01, 0, 35, Items.iron_ingot, "smoke", false,
			false, 0.0).setTextureName("sucaro:MG2").setUnlocalizedName("MG Mk. 2");
	public static Item MGMk3 = new ItemGun(01, 2.0, 0, 0.003, 1.0, 3.0f, 0, 01, 0, 35, Items.iron_ingot, "smoke", false,
			false, 0.0).setTextureName("sucaro:MG3").setUnlocalizedName("MG Mk. 3");
	public static Item MGMk4 = new ItemGun(30, 5.0, 0, 0.010, 0.1, 5.0f, 0, 01, 0, 35, Items.iron_ingot, "smoke", false,
			false, 0.0).setTextureName("sucaro:MG4").setUnlocalizedName("MG Mk. 4");
	public static Item SGMk1 = new ItemGun(06, 3.0, 0, 0.060, 1.0, 2.0f, 0, 06, 0, 15, Items.iron_ingot, "fireworksSpark",
			false, false, 0.0).setTextureName("sucaro:SG1").setUnlocalizedName("SG Mk. 1");
	public static Item SGMk2 = new ItemGun(13, 4.0, 0, 0.080, 1.3, 2.0f, 0, 12, 0, 15, Items.iron_ingot, "fireworksSpark",
			false, false, 0.0).setTextureName("sucaro:SG1").setUnlocalizedName("SG Mk. 2");
	public static Item SMGmk9 = new ItemGun(1, 8.0, 0, 0.01, 0.3, 4.0f, 1, 1, 100, 25, null, "cloud", false, false, 0.1)
			.setTextureName("sucaro:SMG9").setUnlocalizedName("SMGmk9");
	//
	public static Item minigun = new ItemGun(2, 45.0, 2.5, 0.005, 15.0, 15.0f, 1, 1, 0, 200, Items.diamond, "smoke", true,
			false, 10.0).setTextureName("sucaro:minigun1").setUnlocalizedName("Minigun Mk. 1");
	//
	public static Item SMGmk3 = new ItemGun(1, 8.0, 0, 0.01, 0.3, 4.0f, 1, 1, 100, null, "cloud", false, false, 0.1)
			.setTextureName("sucaro:SMG3").setUnlocalizedName("SMGmk3");
	public static Item RLmk1 = new ItemGun(80, 700.0, 0, 0.03, 1.0, 6.0f, 6, 1, 0, null, "portal", true, false, 10.0)
			.setTextureName("sucaro:RL1").setUnlocalizedName("RLmk1");
	public static Item RLmk2 = new ItemGun(40, 5000.0, 0, 0.03, 0.4, 6.0f, 3, 2, 0, null, "flame", true, true, 5.0)
			.setTextureName("sucaro:RL2").setUnlocalizedName("RLmk2");
	public static Item SRMk1 = new ItemGun(40, 40.0, 0, 0.6, 0.0, 10.0f, 3, 1, 0, Items.arrow, "enchantmenttable",
			false, false, 0.0).setTextureName("sucaro:SR1").setUnlocalizedName("SRmk1");
	public static Item SRMk2 = new ItemGun(15, 15.0, 0, 0.23, 0.04, 7.0f, 1, 1, 0, Items.arrow, "flame", true, false,
			2.305).setTextureName("sucaro:SR2").setUnlocalizedName("SRmk2");
	public static Item FTMk1 = new ItemGun(0, 0.0, 0, 0.0, 1.5, 2.0f, 0, 5, 100, null, "flame", true, true, 4.0)
			.setTextureName("sucaro:FT1").setUnlocalizedName("FTmk1");

	public static final void init() {

		

		GameRegistry.registerItem(chainingGun, chainingGun.getUnlocalizedName());
		GameRegistry.registerItem(proxGun, proxGun.getUnlocalizedName());
		GameRegistry.registerItem(moonArrow, moonArrow.getUnlocalizedName());
		GameRegistry.registerItem(neutronGrenade, neutronGrenade.getUnlocalizedName());
		GameRegistry.registerItem(ripper, ripper.getUnlocalizedName());
		GameRegistry.registerItem(parkour, parkour.getUnlocalizedName());
		GameRegistry.registerItem(spreadLauncher, spreadLauncher.getUnlocalizedName());
		GameRegistry.registerItem(beamer, beamer.getUnlocalizedName());
		GameRegistry.registerItem(forcechestplate, forcechestplate.getUnlocalizedName());

		// guns

		GameRegistry.registerItem(minigun, minigun.getUnlocalizedName());
		//
		GameRegistry.registerItem(FTMk1, FTMk1.getUnlocalizedName());
		GameRegistry.registerItem(MGMk1, MGMk1.getUnlocalizedName());
		GameRegistry.registerItem(MGMk2, MGMk2.getUnlocalizedName());
		GameRegistry.registerItem(MGMk3, MGMk3.getUnlocalizedName());
		GameRegistry.registerItem(MGMk4, MGMk4.getUnlocalizedName());
		GameRegistry.registerItem(SGMk1, SGMk1.getUnlocalizedName());
		GameRegistry.registerItem(SGMk2, SGMk2.getUnlocalizedName());
		GameRegistry.registerItem(SMGmk9, SMGmk9.getUnlocalizedName());
		//
		GameRegistry.registerItem(RLmk1, RLmk1.getUnlocalizedName());
		GameRegistry.registerItem(RLmk2, RLmk2.getUnlocalizedName());
		GameRegistry.registerItem(SRMk1, SRMk1.getUnlocalizedName());
		GameRegistry.registerItem(SRMk2, SRMk2.getUnlocalizedName());
		GameRegistry.registerItem(SMGmk3, SMGmk3.getUnlocalizedName());

		// items

		GameRegistry.registerItem(excavator, excavator.getUnlocalizedName());

		GameRegistry.registerItem(higgsStaff, higgsStaff.getUnlocalizedName());

		GameRegistry.registerItem(gravOre, gravOre.getUnlocalizedName());

	}

	// CrucibleMaterial = EnumHelper.addArmorMaterial("ARMORMATERIALHUNTER",
	// 1000000, new int[] { 14, 14, 14, 14 }, 0);
	// hunterhelm = new ItemArmorHunter(RegistryItem.CrucibleMaterial, 1,
	// 0).setUnlocalizedName("HunterHelm").setTextureName("sucaro:hunter_helmet");
	// hunterchest = new ItemArmorHunter(RegistryItem.CrucibleMaterial, 2,
	// 1).setUnlocalizedName("HunterChest").setTextureName("sucaro:hunter_chestplate");
	// hunterlegs = new ItemArmorHunter(RegistryItem.CrucibleMaterial, 3,
	// 2).setUnlocalizedName("HunterLegs").setTextureName("sucaro:hunter_leggings");
	// hunterboots = new ItemArmorHunter(RegistryItem.CrucibleMaterial, 4,
	// 3).setUnlocalizedName("HunterBoots").setTextureName("sucaro:hunter_boots");
	// RegistryItem.Material = EnumHelper.addToolMaterial("CrucibleTool", 10, 1000,
	// 9.0f, 10.0f, 20);
	// RegistryItem.TamingMaterial = EnumHelper.addToolMaterial("TamingMat", 10, 50,
	// 13.0f, 100.0f, 2000);
	// crusword = new
	// CrucibleSword(RegistryItem.Material).setUnlocalizedName("CrucibleSword").setTextureName("sucaro:crucible_sword").func_77637_a(CreativeTabs.field_78037_j);
	// tamestick = new
	// TamingStick(RegistryItem.TamingMaterial).setUnlocalizedName("TamingStick").setTextureName("sucaro:tamingstick").func_77637_a(CreativeTabs.field_78037_j);
	// cruleap = new
	// Item().setUnlocalizedName("CrucibleLeap").setTextureName("sucaro:crucible_leap").func_77637_a(CreativeTabs.field_78037_j);
	// flamethrower = new
	// Item().setUnlocalizedName("flamethrower").setTextureName("sucaro:flamethrower").func_77637_a(CreativeTabs.field_78037_j);
	// RegistryItem.Blink = new
	// ItemBlink().func_77625_d(1).func_77637_a(CreativeTabs.field_78035_l).setUnlocalizedName("blink").setTextureName("sucaro:blink").func_77637_a(CreativeTabs.field_78037_j);
	// RegistryItem.Leap = new
	// ItemLeap().func_77625_d(1).func_77637_a(CreativeTabs.field_78035_l).setUnlocalizedName("leap").setTextureName("LeapOrb").func_77637_a(CreativeTabs.field_78037_j);

	/*
	 * MGMk1 = new ItemGun(2, 5.0, 0.0, 1.5, 5.0f, 1, 1, 0, Items.blaze_powder,
	 * "smoke", false, false, 0.0)
	 * .setTextureName("sucaro:MG1").setUnlocalizedName("MGmk1"); MGMk2 = new
	 * ItemGun(7, 30.0, 0.0, 2.0, 5.0f, 1, 4, 0, null, "portal", true, false,
	 * 1.7).setTextureName("sucaro:MG2") .setUnlocalizedName("MGmk2"); SMGmk3 = new
	 * ItemGun(1, 4.0, 0.01, 0.3, 4.0f, 1, 1, 100, null, "cloud", false, false, 0.1)
	 * .setTextureName("sucaro:SMG3").setUnlocalizedName("SMGmk3"); RLmk1 = new
	 * ItemGun(80, 700.0, 0.03, 1.0, 6.0f, 6, 1, 0, null, "portal", true, false,
	 * 10.0) .setTextureName("sucaro:RL1").setUnlocalizedName("RLmk1"); RLmk2 = new
	 * ItemGun(40, 5000.0, 0.03, 0.4, 6.0f, 3, 2, 0, null, "flame", true, true, 5.0)
	 * .setTextureName("sucaro:RL2").setUnlocalizedName("RLmk2"); SRMk1 = new
	 * ItemGun(40, 40.0, 0.6, 0.0, 10.0f, 3, 1, 0, Items.arrow, "enchantmenttable",
	 * false, false, 0.0).setTextureName("sucaro:SR1").setUnlocalizedName("SRmk1");
	 * SRMk2 = new ItemGun(15, 15.0, 0.23, 0.04, 7.0f, 1, 1, 0, Items.arrow,
	 * "flame", true, false, 2.305)
	 * .setTextureName("sucaro:SR2").setUnlocalizedName("SRmk2"); SGMk1 = new
	 * ItemGun(10, 5.0, 0.1, 1.5, 2.0f, 1, 20, 0, Items.blaze_powder,
	 * "fireworksSpark", false, false,
	 * 0.0).setTextureName("sucaro:SG1").setUnlocalizedName("SGmk1"); FTMk1 = new
	 * ItemGun(0, 0.0, 0.0, 1.5, 2.0f, 0, 5, 100, null, "flame", true, true,
	 * 4.0).setTextureName("sucaro:FT1") .setUnlocalizedName("FTmk1");
	 */

}
