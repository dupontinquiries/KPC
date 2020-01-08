package dme.sucaro.main;

import net.minecraft.client.renderer.entity.RenderArrow;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dme.sucaro.entity.EntityChain;
import dme.sucaro.render.RenderBullet;

public class ClientProxy extends CommonProxy {
	public void registerRender(){
		RenderingRegistry.registerEntityRenderingHandler(EntityChain.class, 
			      new RenderBullet());
	}
}
