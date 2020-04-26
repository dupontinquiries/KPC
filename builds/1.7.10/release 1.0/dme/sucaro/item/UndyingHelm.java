package dme.sucaro.item;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class UndyingHelm extends ItemArmor {
	
	private Random rand = new Random();

	public UndyingHelm(ArmorMaterial tragarmor, int renderIndex, int armorType) {
		super(tragarmor, renderIndex, armorType);
		//this.setTextureName("sucaro:hunter_helmet");
		// TODO Auto-generated constructor stub
	}

	public void onArmorTick(World w, EntityPlayer p, ItemStack s) {
		if (p.getHealth() <= 5) {
			p.heal( 40f );
			//p.heal( (float) (((rand.nextDouble() * 40) + 1) * 0.5f) );
			//p.setHealth(p.getMaxHealth());
			//p.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 30, 0));
			
			p.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 30, 3));
			p.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 30, 3));
			p.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 30, 1));
		}
	}

}
