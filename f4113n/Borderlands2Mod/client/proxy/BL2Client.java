package f4113n.Borderlands2Mod.client.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import f4113n.Borderlands2Mod.client.render.RenderBullet;
import f4113n.Borderlands2Mod.client.render.RenderGrenade;
import f4113n.Borderlands2Mod.client.render.RenderGrenadeInHand;
import f4113n.Borderlands2Mod.client.render.RenderGunInHand;
import f4113n.Borderlands2Mod.client.render.ShieldGUIHandler;
import f4113n.Borderlands2Mod.common.BL2Core;
import f4113n.Borderlands2Mod.common.entity.EntityBullet;
import f4113n.Borderlands2Mod.common.entity.EntityGrenade;
import f4113n.Borderlands2Mod.common.proxy.BL2Proxy;

public class BL2Client extends BL2Proxy{
	
	@Override
    public void registerRenderInformation()
    {
            MinecraftForgeClient.preloadTexture("/f4113n/Borderlands2Mod/textures/Items.png");
            MinecraftForgeClient.preloadTexture("/f4113n/Borderlands2Mod/textures/bullet.png");
            MinecraftForgeClient.preloadTexture("/f4113n/Borderlands2Mod/textures/Shields.png");
            RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
            BL2Core.shieldrenderid = RenderingRegistry.addNewArmourRendererPrefix("/f4113n/Borderlands2Mod/textures");
            RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderGrenade());
            //MinecraftForge.EVENT_BUS.register(new RenderShield());
    }
	
	@Override
	public void initItemRenderer(int itemID)
	{
	    if (itemID == BL2Core.guns.itemID)
	    {
	    	MinecraftForgeClient.registerItemRenderer(itemID, new RenderGunInHand());
	    }
	    if(itemID == BL2Core.grenade.itemID)
	    {
	    	MinecraftForgeClient.registerItemRenderer(itemID, new RenderGrenadeInHand());
	    }
	}

	@Override
    public void registerRenderTickHandler() {

        TickRegistry.registerTickHandler(new ShieldGUIHandler(), Side.CLIENT);
    }
	
	public void registerKeyBinding()
	{
		KeyBindingRegistry.registerKeyBinding(new f4113n.Borderlands2Mod.client.handler.BL2KeyHandler());
	}
}
