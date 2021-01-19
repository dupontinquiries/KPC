package dme.sucaro.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
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
		if (p.isSneaking() && !p.capabilities.isFlying) {
			List boundingList = p.worldObj.getEntitiesWithinAABBExcludingEntity(p, p.boundingBox.expand(3, 5, 3));
	    	for (int g = 0; g < boundingList.size(); g++){
	    		if(boundingList.get(g) instanceof EntityLivingBase){
	    			EntityLivingBase elbf = (EntityLivingBase) boundingList.get(g);
	    			if(elbf != p){
	    				//((elbf.posY-p.posY )/245)
	    				elbf.addVelocity(((elbf.posX-p.posX )/30), 0.061, (elbf.posZ-p.posZ )/30); //245, .082
	    			}
	    		}
	    	}
		}
    }
	
}
