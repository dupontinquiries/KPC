package dme.sucaro.main;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dme.sucaro.entity.EntityChain;
import dme.sucaro.entity.EntityGravity;
import dme.sucaro.render.RenderBullet;

public class ClientProxy extends CommonProxy {
	public void registerRender(){
		RenderingRegistry.registerEntityRenderingHandler(EntityChain.class, 
			      new RenderBullet());
	}
}
