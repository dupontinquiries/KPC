package com.kitchen.parkourcombat.items.armorbase;

import com.kitchen.parkourcombat.Main;
import com.kitchen.parkourcombat.items.armor.ArmorBase;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RubyBase extends ArmorBase implements IHasModel {
	
	private final int expAmount = 5;

	public RubyBase(String string, ArmorMaterial armorMaterialRuby, int i, EntityEquipmentSlot head) {
		super(string, armorMaterialRuby, i, head);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		float diff = player.getMaxHealth() - player.getHealth();
		if (diff > 0 && diff < 3) {
			player.heal(1);
		}
	}
	
	//this.onCreated(stack, worldIn, playerIn);

}
