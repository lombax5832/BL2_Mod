package f4113n.Borderlands2Mod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texturefx.TextureFX;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import f4113n.Borderlands2Mod.liquid.TileLiquidEridium;
import f4113n.core.*;
import f4113n.core.liquid.LiquidFX;
import f4113n.core.liquid.TELiquidBlock;
import f4113n.core.misc.*;
import f4113n.core.te.FCBlockTile;
import f4113n.core.te.parents.FCTile;

@Mod(modid = "BL2", name = "Borderlands2Mod", version = "1.4.7", dependencies = "after:FCore")
@NetworkMod(clientSideRequired = true)
public class BL2Core extends FBase
{
	@Mod.PreInit
    public void preinit(FMLPreInitializationEvent event)
    {
    	FCore.addMod(this);
    }
	
	public static TELiquidBlock liquid;
	
	@Override
	public void init() 
	{	
		liquid = new TELiquidBlock(this, "baseliquid");
		liquid.addSubBlock(TileLiquidEridium.class);
		liquid.setCreativeTab(CreativeTabs.tabAllSearch);
		
		//advgens = new BlockAdvGenerators(this, "AdvGenerators", Material.iron, Block.soundMetalFootstep);
		//advgens.addSubBlock(TileEntitySlagGenerator.class);
		
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
		}
	}
	
	@Override
    public void getProperties()
    {
    	
    }
}
