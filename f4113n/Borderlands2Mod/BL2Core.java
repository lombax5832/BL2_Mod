package f4113n.Borderlands2Mod;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import f4113n.Borderlands2Mod.client.handler.NetworkHandlerClient;
import f4113n.Borderlands2Mod.client.render.RenderBullet;
import f4113n.Borderlands2Mod.client.render.RenderGrenade;
import f4113n.Borderlands2Mod.client.render.ShieldGUIHandler;
import f4113n.Borderlands2Mod.common.CreativeTabBL2;
import f4113n.Borderlands2Mod.common.entity.EntityBullet;
import f4113n.Borderlands2Mod.common.entity.EntityGrenade;
import f4113n.Borderlands2Mod.common.handler.IItemTickListener;
import f4113n.Borderlands2Mod.common.handler.NetworkHandler;
import f4113n.Borderlands2Mod.common.item.ItemArmorShield;
import f4113n.Borderlands2Mod.common.item.ItemBandoiler;
import f4113n.Borderlands2Mod.common.item.ItemBullets;
import f4113n.Borderlands2Mod.common.item.ItemGrenade;
import f4113n.Borderlands2Mod.common.item.ItemGun;
import f4113n.Borderlands2Mod.common.item.ItemTemp;
import f4113n.Borderlands2Mod.liquid.TileLiquidEridium;
import f4113n.core.FBase;
import f4113n.core.FCore;
import f4113n.core.Platform;
import f4113n.core.liquid.LiquidFX;
import f4113n.core.liquid.TELiquidBlock;

@Mod(modid = "BL2", name = "Borderlands2Mod", version = "1.4.7", dependencies = "after:FCore")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"bl2"}, packetHandler = NetworkHandlerClient.class),
serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"bl2"}, packetHandler = NetworkHandler.class))
public class BL2Core extends FBase implements ITickHandler
{
	@Mod.PreInit
    public void preinit(FMLPreInitializationEvent event)
    {
    	FCore.addMod(this);
    }
	
	public static TELiquidBlock liquid;
	public static Item guns;
    public static Item bullets;
    public static Item bandoiler;
    public static Item shield;
    public static Item grenade;
    public static Item temp;
    public static int shieldrenderid = 0;
    public static CreativeTabBL2 tabBL2 = new CreativeTabBL2("Borderlands 2");
    
    @SidedProxy(clientSide = "f4113n.Borderlands2Mod.client.handler.NetworkHandlerClient", serverSide = "f4113n.Borderlands2Mod.common.handler.NetworkHandler")
    public static NetworkHandler nethandler;
	
	@Override
	public void init() 
	{	
		liquid = new TELiquidBlock(this, "baseliquid");
		liquid.addSubBlock(TileLiquidEridium.class);
		liquid.setCreativeTab(CreativeTabs.tabAllSearch);
		
		if(Platform.getSide() == Side.CLIENT)
		{
			try
			{
				Object o = Minecraft.class.getField("renderEngine").get(FMLClientHandler.instance().getClient());
				o.getClass().getMethod("registerTextureFX", Class.forName("TextureFX")).invoke(o, new LiquidFX(this, 0, 0, 255, 0, 255, 0, 255));
			}catch(Exception ex)
			{
				//not client?
			}
            MinecraftForgeClient.preloadTexture("/f4113n/Borderlands2Mod/textures/bullet.png");
            MinecraftForgeClient.preloadTexture("/f4113n/Borderlands2Mod/textures/Shields.png");
            RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
            BL2Core.shieldrenderid = RenderingRegistry.addNewArmourRendererPrefix("/f4113n/Borderlands2Mod/textures");
            RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderGrenade());
            
            TickRegistry.registerTickHandler(new ShieldGUIHandler(), Side.CLIENT);
		}
		
		EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 1, this, 64, 10, true);
    	EntityRegistry.registerModEntity(EntityGrenade.class, "Grenade", 2, this, 64, 10, true);
    	
        guns = new ItemGun(16000);
        bullets = new ItemBullets(16001);
        bandoiler = new ItemBandoiler(16002);
        shield = new ItemArmorShield(16003, shieldrenderid, 1).setIconIndex(65);
        grenade = new ItemGrenade(16004);
        temp = new ItemTemp(16005);
        LanguageRegistry.addName(guns, "Gun");
        //registerHandlers();
        TickRegistry.registerTickHandler(this, Side.SERVER);
//        MinecraftForge.EVENT_BUS.register(new RenderShield());
        
        GameRegistry.addRecipe(new ItemStack(temp), new Object[]
        		{
        	"IWI",
        	"WGW",
        	"IWI",
        	'I', Item.ingotIron,
        	'W', Block.planks,
        	'G', Item.ingotGold
        		});
	}
	
	@Override
    public void getProperties()
    {
    	
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
    		
//    		if(update)
//    		{
//    			ep.openContainer.updateCraftingResults();
//    		}
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
