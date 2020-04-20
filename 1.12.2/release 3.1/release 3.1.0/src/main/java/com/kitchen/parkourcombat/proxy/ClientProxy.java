package com.kitchen.parkourcombat.proxy;

import com.kitchen.parkourcombat.entity.projectile.EntityBullet;
import com.kitchen.parkourcombat.renderer.entity.RenderBullet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
        //RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet(Minecraft.getMinecraft().getRenderManager()));
	}
}
