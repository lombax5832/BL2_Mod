package BL2.common;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import BL2.client.NetworkHandlerClient;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "BL2", name = "Borderlands 2", version = "0.8(1.4.6)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"bl2"}, packetHandler = NetworkHandlerClient.class),
serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"bl2"}, packetHandler = NetworkHandler.class))
public class BL2Core implements ITickHandler
{
    public static Item guns;
    public static Item bullets;
    public static Item bandoiler;
    public static Item shield;
    public static Item temp;
    
    public static int shieldrenderid = 0;

    @SidedProxy(clientSide = "BL2.client.BL2Client", serverSide= "BL2.common.BL2Proxy")
    public static BL2Proxy proxy;
    @SidedProxy(clientSide = "BL2.client.NetworkHandlerClient", serverSide = "BL2.common.NetworkHandler")
    public static NetworkHandler nethandler;
    
    public static CreativeTabBL2 tabBL2 = new CreativeTabBL2("BL2");
    
    @Mod.PreInit
    public void preInt(FMLPreInitializationEvent event){
			
    }
    
    @Mod.Init
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerKeyBinding();
    	proxy.registerRenderInformation();
    	
    	EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 1, this, 64, 10, true);
        
        guns = new ItemGun(16000);
        bullets = new ItemBullets(16001);
        bandoiler = new ItemBandoiler(16002);
        shield = new ItemArmorShield(16003, shieldrenderid, 1).setIconIndex(65);
        temp = new ItemTemp(16004);
        LanguageRegistry.addName(guns, "Gun");
        //registerHandlers();
        TickRegistry.registerTickHandler(this, Side.SERVER);
    }
    
    public void tickStart(EnumSet<TickType> var1, Object... var2)
    {
    	if(var1.contains(TickType.PLAYER))
    	{
    		EntityPlayer ep = (EntityPlayer)var2[0];
    		
    		if(ep.isDead)
    		{
    			return;
    		}
    		
    		boolean update = false;
    		for(int i = 0; i < 4; i++)
    		{
    			if(ep.inventory.armorInventory[i] != null && ep.inventory.armorInventory[i].getItem() instanceof IItemTickListener)
    			{
	    			if(((IItemTickListener)ep.inventory.armorInventory[i].getItem()).onTick(ep, ep.inventory.armorInventory[i]))
	    			{
	    				update = true;
	    			}
    			}
    		}
    		
    		if(update)
    		{
    			ep.openContainer.updateCraftingResults();
    		}
    	}
    }

	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() 
	{
		return "BL2";
	}
}