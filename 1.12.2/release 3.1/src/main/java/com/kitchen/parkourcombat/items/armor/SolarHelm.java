package com.kitchen.parkourcombat.items.armor;

import com.kitchen.parkourcombat.items.armorbase.SolarBase;
import com.kitchen.parkourcombat.util.IHasModel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class SolarHelm extends SolarBase implements IHasModel {
	
	private short maxCounts = 10, counts = maxCounts;

	public SolarHelm(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
		super.onArmorTick(world, player, stack);
		
		//particles
		if (counts == 0) {
			counts = maxCounts;
			
			world.spawnParticle(EnumParticleTypes.REDSTONE,
					player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(),
					0.0D, 0.0D, 0.0D);
		} else {
			--counts;
		}
		
		//photosynthesis
		World worldIn = player.world;
		if (worldIn.provider.hasSkyLight())
        {
            IBlockState iblockstate = worldIn.getBlockState(player.getPosition());
            int i = worldIn.getLightFor(EnumSkyBlock.SKY, player.getPosition()) - worldIn.getSkylightSubtracted();
            float f = worldIn.getCelestialAngleRadians(1.0F);
            
            if (i > 0)
            {
            	float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
                f = f + (f1 - f) * 0.2F;
                i = Math.round((float)i * MathHelper.cos(f));
            }
            i = MathHelper.clamp(i, 0, 15);
            //System.out.println("   i = " + i);
            if (i > 3) {
            	if (!player.isPotionActive(Potion.getPotionFromResourceLocation("regeneration"))) {
            		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 10, 1, false, true));
            	}
            	if (stack.isItemDamaged()) {
            		stack.setItemDamage(stack.getItemDamage() - 1);
            	}
            } else {
            	if (!player.isPotionActive(Potion.getPotionFromResourceLocation("slowness"))) {
            		player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, 1, false, true));
            	}
            }
        }
    }
	

}
