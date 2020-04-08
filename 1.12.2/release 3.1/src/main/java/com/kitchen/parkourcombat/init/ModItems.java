package com.kitchen.parkourcombat.init;

import java.util.ArrayList;
import java.util.List;

import com.kitchen.parkourcombat.items.ItemBase;
import com.kitchen.parkourcombat.items.armor.SolarBoots;
import com.kitchen.parkourcombat.items.armor.SolarChestplate;
import com.kitchen.parkourcombat.items.armor.SolarHelm;
import com.kitchen.parkourcombat.items.armor.SolarLeggings;
import com.kitchen.parkourcombat.items.armorbase.NSBase;
import com.kitchen.parkourcombat.items.armorbase.RubyBase;
import com.kitchen.parkourcombat.items.food.FoodEffectBase;
import com.kitchen.parkourcombat.items.food.FoodEffectTime;
import com.kitchen.parkourcombat.items.gun.GunBase;
import com.kitchen.parkourcombat.items.gun.GunBase3;
import com.kitchen.parkourcombat.items.tools.EndStoneBlade;
import com.kitchen.parkourcombat.items.tools.ItemBaseDetonator;
import com.kitchen.parkourcombat.items.tools.ItemMagazineBase;
import com.kitchen.parkourcombat.items.tools.LegendaryBladeBase;
import com.kitchen.parkourcombat.items.tools.ToolAxe;
import com.kitchen.parkourcombat.items.tools.ToolHoe;
import com.kitchen.parkourcombat.items.tools.ToolNSBlade;
import com.kitchen.parkourcombat.items.tools.ToolParkour;
import com.kitchen.parkourcombat.items.tools.ToolPickaxe;
import com.kitchen.parkourcombat.items.tools.ToolSpade;
import com.kitchen.parkourcombat.items.tools.ToolSword;
import com.kitchen.parkourcombat.items.tools.ToolSwordChargable;
import com.kitchen.parkourcombat.items.tools.ToolSwordChargableBone;
import com.kitchen.parkourcombat.items.tools.ToolSwordChargableCreeper;
import com.kitchen.parkourcombat.items.tools.ToolSwordChargableEnd;
import com.kitchen.parkourcombat.items.tools.ToolSwordChargableHostile;
import com.kitchen.parkourcombat.items.tools.ToolUltimateParkour;
import com.kitchen.parkourcombat.util.Reference;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();

	//Materials
	public static final ToolMaterial SMALL_MAGAZINE = EnumHelper.addToolMaterial("small_magazine", 0, 35, 0F, 0F, 6);
	
	public static final ToolMaterial MATERIAL_PARKOUR_B = EnumHelper.addToolMaterial("material_parkour_b", 3, 450, 8.0F, 2.6F, 13);
	public static final ToolMaterial MATERIAL_PARKOUR_A = EnumHelper.addToolMaterial("material_parkour_a", 3, 650, 8.0F, 4.0F, 13);
	public static final ToolMaterial MATERIAL_RUBY = EnumHelper.addToolMaterial("material_ruby", 3, 450, 8.0F, 3.0F, 13);
	public static final ToolMaterial MATERIAL_DEAD = EnumHelper.addToolMaterial("material_dead", 3, 250, 8.0F, 1.0F, 2);
	public static final ToolMaterial MATERIAL_SOLAR = EnumHelper.addToolMaterial("material_solar", 3, 150, 5.0F, 3.0F, 33);
	public static final ToolMaterial MATERIAL_NIGHT_SHADE = EnumHelper.addToolMaterial("material_night_shade", 3, 150, 5.0F, 7.0F, 33);
	
	public static final ToolMaterial CHARGE_TIER_C = EnumHelper.addToolMaterial("charge_tier_c", 3, 250, 8.0F, 1.0F, 20);
	public static final ToolMaterial CHARGE_TIER_B = EnumHelper.addToolMaterial("charge_tier_b", 3, 350, 14.0F, 1.0F, 20);
	public static final ToolMaterial CHARGE_TIER_A = EnumHelper.addToolMaterial("charge_tier_a", 3, 450, 26.0F, 1.0F, 20);
	public static final ToolMaterial CHARGE_TIER_X = EnumHelper.addToolMaterial("charge_tier_a", 3, 950, 26.0F, 1.0F, 20);
	
	public static final ToolMaterial LEGENDARY_TIER_D = EnumHelper.addToolMaterial("legendary_tier_d", 3, 250, 8.0F, 4.0F, 20);
	public static final ToolMaterial LEGENDARY_TIER_C = EnumHelper.addToolMaterial("legendary_tier_c", 3, 310, 8.0F, 7.0F, 20);
	public static final ToolMaterial LEGENDARY_TIER_B = EnumHelper.addToolMaterial("legendary_tier_b", 3, 370, 14.0F, 9.0F, 35);
	public static final ToolMaterial LEGENDARY_TIER_A = EnumHelper.addToolMaterial("legendary_tier_a", 3, 440, 25.0F, 13.0F, 60);
	public static final ToolMaterial LEGENDARY_TIER_X = EnumHelper.addToolMaterial("legendary_tier_x", 3, 510, 28.0F, 18.0F, 60);
	public static final ToolMaterial LEGENDARY_TIER_NS = EnumHelper.addToolMaterial("legendary_tier_ns", 3, 140, 34.0F, 18.0F, 160);
	
	public static final ArmorMaterial ARMOR_MATERIAL_RUBY = EnumHelper.addArmorMaterial("armor_material_ruby", Reference.MODID + ":ruby", 25, // 21
			new int[] {6, 9, 12, 6}, 10, SoundEvents.BLOCK_ANVIL_PLACE, 4.0F); //2.0F
	public static final ArmorMaterial ARMOR_MATERIAL_SOLAR = EnumHelper.addArmorMaterial("armor_material_solar", Reference.MODID + ":solar", 4, // 21
			new int[] {12, 18, 24, 12}, 60, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, 4.0F);
	public static final ArmorMaterial ARMOR_MATERIAL_NS = EnumHelper.addArmorMaterial("armor_material_ns", Reference.MODID + ":ns", 4, // 21
			new int[] {24, 36, 48, 24}, 60, SoundEvents.BLOCK_NOTE_BASS, 4.0F);
	
	//Items
	public static final Item RUBY = new ItemBase("ruby");
	public static final Item CHARGED_RUBY = new ItemBase("charged_ruby");
	public static final Item OBSIDIAN_INGOT = new ItemBase("obsidian_ingot");
	public static final Item NS_GEM = new ItemBase("ns_gem");
	
	//Exploding Trap Powder
	public static final Item EXPLODING_TRAP_POWDER = new ItemBase("exploding_trap_powder");

	//Tools
	
	//Ruby
	public static final ItemSword RUBY_SWORD = new ToolSword("ruby_sword", MATERIAL_RUBY);
	public static final ItemSpade RUBY_SHOVEL = new ToolSpade("ruby_shovel", MATERIAL_RUBY);
	public static final ItemPickaxe RUBY_PICKAXE = new ToolPickaxe("ruby_pickaxe", MATERIAL_RUBY);
	public static final ItemAxe RUBY_AXE = new ToolAxe("ruby_axe", MATERIAL_RUBY);
	public static final ItemHoe RUBY_HOE = new ToolHoe("ruby_hoe", MATERIAL_RUBY);
	
	//Night Shade
	public static final ToolSwordChargable NS_ARTIFACT = new ToolSwordChargableHostile("ns_artifact", CHARGE_TIER_X);
	public static final ItemBase CHARGED_NS_ARTIFACT = new ItemBase("charged_ns_artifact");
	public static final ToolNSBlade NS_BLADE = new ToolNSBlade("ns_blade", LEGENDARY_TIER_NS);
	
	//parkour
	public static final ItemSword PARKOUR_GRIPPER = new ToolParkour("parkour_gripper", MATERIAL_PARKOUR_B);
	public static final ItemSword ULTIMATE_PARKOUR_GRIPPER = new ToolUltimateParkour("ultimate_parkour_gripper", MATERIAL_PARKOUR_A);
	
	//Legendary Blade
	public static final ToolSwordChargable DEAD_BLADE = new ToolSwordChargable("dead_blade", CHARGE_TIER_C);
	public static final ToolSwordChargable DEAD_BLADE_CAST = new ToolSwordChargable("dead_blade_cast", CHARGE_TIER_B);
	public static final ItemBase CHARGED_DEAD_BLADE_CAST = new ItemBase("charged_dead_blade_cast");
	public static final LegendaryBladeBase LEGENDARY_BLADE = new LegendaryBladeBase("legendary_blade", LEGENDARY_TIER_C, "wolf", (short) 340, (short) 280, RUBY);
	
	//Bone Blade
	public static final ToolSwordChargable SHARP_BONE = new ToolSwordChargableBone("sharp_bone", CHARGE_TIER_C);
	public static final ToolSwordChargable MAGICAL_BONE_CAST = new ToolSwordChargableBone("magical_bone_cast", CHARGE_TIER_B);
	public static final ItemBase CHARGED_MAGICAL_BONE_CAST = new ItemBase("charged_magical_bone_cast");
	public static final LegendaryBladeBase BONE_BLADE = new LegendaryBladeBase("bone_blade", LEGENDARY_TIER_C, "skeleton", (short) 160, (short) 160, Items.BONE);
	
	//Creeper Staff
	public static final ToolSwordChargable MAGIC_GUNPOWDER = new ToolSwordChargableCreeper("magic_gunpowder", CHARGE_TIER_A, false);
	public static final ToolSwordChargable MAGIC_GUNPOWDER_CELL = new ToolSwordChargableCreeper("magic_gunpowder_cell", CHARGE_TIER_X, true);
	public static final ItemBase CHARGED_MAGIC_GUNPOWDER_CELL = new ItemBaseDetonator("charged_magic_gunpowder_cell", 360);
	public static final LegendaryBladeBase CREEPER_STAFF = new LegendaryBladeBase("creeper_staff", LEGENDARY_TIER_D, "creeper", (short) 160, (short) 90, Items.GUNPOWDER);
	
	//Blaze Staff
	public static final LegendaryBladeBase BLAZE_STAFF = new LegendaryBladeBase("blaze_staff", LEGENDARY_TIER_X, "blaze", (short) 360, (short) 160, Items.BLAZE_POWDER);
	
	//End Stone Blade
	public static final Item DRAGON_SHELL = new ItemBase("dragon_shell");
	public static final ToolSwordChargable DIMENSIONAL_ARTIFACT = new ToolSwordChargableEnd("dimensional_artifact", CHARGE_TIER_C);
	public static final ToolSwordChargable DIMENSIONAL_ARTIFACT_CAST = new ToolSwordChargableEnd("dimensional_artifact_cast", CHARGE_TIER_B);
	public static final ToolSwordChargable LIFELESS_END_STONE_BLADE = new ToolSwordChargableEnd("lifeless_end_stone_blade", CHARGE_TIER_A);
	public static final ItemBase CHARGED_LIFELESS_END_STONE_BLADE = new ItemBase("charged_lifeless_end_stone_blade");
	public static final EndStoneBlade END_STONE_BLADE = new EndStoneBlade("end_stone_blade", LEGENDARY_TIER_B, "enderman", (short) 160, (short) 106, (short) 60);
	
	//Higgs Staff
	public static final Item HIGGS_STAFF = new ItemBase("higgs_staff").setMaxStackSize(1);
	
	//Armor
	
	//Ruby
	public static final Item RUBY_HELMET = new RubyBase("ruby_helmet", ARMOR_MATERIAL_RUBY, 1, EntityEquipmentSlot.HEAD);
	public static final Item RUBY_CHESTPLATE = new RubyBase("ruby_chestplate", ARMOR_MATERIAL_RUBY, 1, EntityEquipmentSlot.CHEST);
	public static final Item RUBY_LEGGINGS = new RubyBase("ruby_leggings", ARMOR_MATERIAL_RUBY, 2, EntityEquipmentSlot.LEGS);
	public static final Item RUBY_BOOTS = new RubyBase("ruby_boots", ARMOR_MATERIAL_RUBY, 1, EntityEquipmentSlot.FEET);

	//Solar
	public static final Item SOLAR_HELM = new SolarHelm("solar_helm", ARMOR_MATERIAL_SOLAR, 1, EntityEquipmentSlot.HEAD);
	public static final Item SOLAR_CHESTPLATE = new SolarChestplate("solar_chestplate", ARMOR_MATERIAL_SOLAR, 1, EntityEquipmentSlot.CHEST);
	public static final Item SOLAR_LEGGINGS = new SolarLeggings("solar_leggings", ARMOR_MATERIAL_SOLAR, 2, EntityEquipmentSlot.LEGS);
	public static final Item SOLAR_BOOTS = new SolarBoots("solar_boots", ARMOR_MATERIAL_SOLAR, 1, EntityEquipmentSlot.FEET);
	
	//Night Shade
	public static final Item NS_HELM = new NSBase("ns_helm", ARMOR_MATERIAL_NS, 1, EntityEquipmentSlot.HEAD);
	public static final Item NS_CHESTPLATE = new NSBase("ns_chestplate", ARMOR_MATERIAL_NS, 1, EntityEquipmentSlot.CHEST);
	public static final Item NS_LEGGINGS = new NSBase("ns_leggings", ARMOR_MATERIAL_NS, 2, EntityEquipmentSlot.LEGS);
	public static final Item NS_BOOTS = new NSBase("ns_boots", ARMOR_MATERIAL_NS, 1, EntityEquipmentSlot.FEET);
	
	//Food
	
	//public static final Item EVIL_APPLE = new FoodBase("evil_apple", 4, 2.4f, false);
	
	static PotionEffect[] EVIL_APPLE_EFFECTS = { new PotionEffect(MobEffects.POISON, (4*20), 1, false, true) };
	public static final Item EVIL_APPLE = new FoodEffectBase("evil_apple", 4, 2.4f, false, EVIL_APPLE_EFFECTS);
	
	static PotionEffect[] FLYING_APPLE_EFFECTS = { new PotionEffect(MobEffects.LEVITATION, (7*20), 1, false, true), 
			new PotionEffect(MobEffects.REGENERATION, (7*20), 2, false, true) };
	public static final Item FLYING_APPLE = new FoodEffectBase("flying_apple", 4, 2.4f, false, FLYING_APPLE_EFFECTS);
	
	public static final Item TEMPORAL_APPLE = new FoodEffectTime("temporal_apple", 5, 2.6f, false, 5000);
	
	//Guns
	
	//SMGs
	public static final Item SMG_MK_15 = new GunBase("smg_mk_15", 4.0F, 1, 35, 0.2F, false);
	public static final Item BULLET = new ItemBase("bullet");
	public static final ItemMagazineBase MAGAZINE = new ItemMagazineBase("magzine", SMALL_MAGAZINE);
}
