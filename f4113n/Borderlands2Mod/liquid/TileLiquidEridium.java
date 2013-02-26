package f4113n.Borderlands2Mod.liquid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import f4113n.Borderlands2Mod.BL2Core;
import f4113n.core.FBase;
import f4113n.core.liquid.TEFluid;

public class TileLiquidEridium extends TEFluid
{
	public TileLiquidEridium()
	{
		maxLevel = 8;
		speed = 5;
	}
	
	/**
	 * Return an array of ints between 0 and 255 that represent the alpha, red, green, and blue multiplers for this liquid.
	 */
	@SideOnly(Side.CLIENT)
    public int[] colorMultipliers()
    {
		return new int[]{255, 255, 255, 255};
    }
	
	@Override
	public void setTo(World world, int x, int y, int z) 
	{
		world.setBlockAndMetadataWithNotify(x, y, z, BL2Core.liquid.blockID, 0);
	}

	@Override
	public int getTexture() 
	{
		return 0;
	}

	@Override
	public int getTextureFor() 
	{
		return 0;
	}

	@Override
	public boolean canInteract(TEFluid tile)
	{
		return tile instanceof TileLiquidEridium;
	}

	@Override
	public String getName()
	{
		return "Liquid Eridium";
	}
}
