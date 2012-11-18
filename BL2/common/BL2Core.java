package BL2.common;

import vazkii.codebase.common.CommonUtils;
import net.minecraft.src.BaseMod;
import net.minecraft.src.Item;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "BL2", name = "Borderlands 2", version = "0.5(1.4.5)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class BL2Core
{
    public static Item guns;
    public static Item bullets;
    public static Item bandoiler;
    public static Item temp;

    @SidedProxy(clientSide = "BL2.client.BL2Client", serverSide= "BL2.common.BL2Proxy")
    public static BL2Proxy proxy;
    
    public static CreativeTabBL2 tabBL2 = new CreativeTabBL2("BL2");
    
    @Mod.PreInit
    public void preInt(FMLPreInitializationEvent event){
			
    }
    
    @Mod.Init
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenderInformation();
    	
    	EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 1, this, 64, 10, true);
        
        guns = new ItemGun(16000);
        bullets = new ItemBullets(16001);
        bandoiler = new ItemBandoiler(16002);
        temp = new ItemTemp(16003);
        LanguageRegistry.addName(guns, "Gun");
        //registerHandlers();
    }
}
