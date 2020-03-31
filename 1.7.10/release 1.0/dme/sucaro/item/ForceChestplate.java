package dme.sucaro.item;

import java.util.List;

import dme.sucaro.entity.EntityBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;

public class ForceChestplate extends ItemArmor {

	public ForceChestplate(ArmorMaterial tragarmor, int renderIndex, int armorType) {
		super(tragarmor, renderIndex, armorType);
		//this.setTextureName("sucaro:hunter_chestplate");
		// TODO Auto-generated constructor stub
	}

	public void onArmorTick(World w, EntityPlayer p, ItemStack s) {
		if ((p.isSneaking()) && !p.capabilities.isFlying) {
			List boundingList = p.worldObj.getEntitiesWithinAABBExcludingEntity(p, p.boundingBox.expand(3, 5, 3));
			for (int g = 0; g < boundingList.size(); g++) {
				if (boundingList.get(g) instanceof EntityLivingBase) {
					EntityLivingBase elbf = (EntityLivingBase) boundingList.get(g);
					if (elbf != p) {
						elbf.addVelocity(((elbf.posX - p.posX) / 2), 0.061, (elbf.posZ - p.posZ) / 2); // 245, .082
						elbf.attackEntityFrom(DamageSource.inWall, 3);
					}
				} else if (boundingList.get(g) instanceof EntityPlayer && boundingList.get(g) != p) {
					EntityPlayer player = (EntityPlayer) boundingList.get(g);
					player.attackEntityFrom(DamageSource.inWall, 5);
					player.motionX += (player.posX - p.posX) / 8;
					player.motionY += (player.posY - p.posY) / 8;
					player.motionZ += (player.posZ - p.posZ) / 8;
					player.velocityChanged = true;
				} else if (boundingList.get(g).getClass().getGenericInterfaces().length > 0
						&& boundingList.get(g).getClass().getGenericInterfaces()[0] == IProjectile.class) {// instanceof
					// EntityArrow){
					if (boundingList.get(g) instanceof EntityBullet
							&& ((EntityBullet) boundingList.get(g)).shootingEntity != p) {
						EntityBullet elbf = (EntityBullet) boundingList.get(g);
						elbf.addVelocity(((elbf.posX - p.posX) * 4), ((elbf.posY - p.posY) * 4),
								(elbf.posZ - p.posZ) * 4); // 245, .082
					} else {
						Entity elbf = (Entity) boundingList.get(g);
						elbf.addVelocity(((elbf.posX - p.posX) * 1.5), ((elbf.posY - p.posY) * 1.5),
								((elbf.posZ - p.posZ) * 1.5)); // 245, .082
					}
				}
			}
		} else {

		}
	}

}
