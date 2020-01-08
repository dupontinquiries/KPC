package dme.sucaro.item;

import java.util.List;

import dme.sucaro.entity.EntityBullet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;

public class GodArmor extends ItemArmor {
	
	public GodArmor(ArmorMaterial tragarmor, int p_i45325_2_, int p_i45325_3_) {
		super(tragarmor, p_i45325_2_, p_i45325_3_);
		// TODO Auto-generated constructor stub
	}
	
	public void onArmorTick(World w, EntityPlayer p, ItemStack s)
    {
		if ((p.isSneaking() || true) && !p.capabilities.isFlying) {
			List boundingList = p.worldObj.getEntitiesWithinAABBExcludingEntity(p, p.boundingBox.expand(3, 5, 3));
	    	for (int g = 0; g < boundingList.size(); g++){
	    		if (boundingList.get(g) instanceof EntityLivingBase){
	    			EntityLivingBase elbf = (EntityLivingBase) boundingList.get(g);
	    			if(elbf != p){
	    				elbf.addVelocity(((elbf.posX-p.posX)/2), 0.061, (elbf.posZ-p.posZ)/2); //245, .082
	    			}
	    		} else if (boundingList.get(g) instanceof EntityArrow){
	    			EntityArrow elbf = (EntityArrow) boundingList.get(g);
    				elbf.addVelocity(((elbf.posX-p.posX)*1.5), ((elbf.posY-p.posY)*1.5), ((elbf.posZ-p.posZ)*1.5)); //245, .082
	    		} else if (boundingList.get(g) instanceof EntityBullet && ((EntityBullet) boundingList.get(g)).shootingEntity != p){
	    			EntityBullet elbf = (EntityBullet) boundingList.get(g);
    				elbf.addVelocity(((elbf.posX-p.posX)*4), ((elbf.posY-p.posY)*4), (elbf.posZ-p.posZ)*4); //245, .082
	    		} else if (boundingList.get(g) instanceof EntityPlayer && boundingList.get(g) != p){
	    			EntityPlayer player = (EntityPlayer) boundingList.get(g);
	    			player.motionX += (player.posX-p.posX)/8;
	    			player.motionY += (player.posY-p.posY)/8;
	    			player.motionZ += (player.posZ-p.posZ)/8;
    				player.velocityChanged = true;
	    		}
	    	}
		} else {
			
		}
    }
	
}
