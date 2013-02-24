package f4113n.Borderlands2Mod.client.handler;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import f4113n.Borderlands2Mod.common.BL2Core;

public class BL2KeyHandler extends KeyHandler
{
	public static KeyBinding reloadKey = new KeyBinding("Reload", 19);

	public BL2KeyHandler() 
	{
		super(new KeyBinding[]{reloadKey});
	}

	@Override
	public String getLabel() 
	{
		return null;
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
			System.out.println("r");
			BL2Core.nethandler.sendReloaderPacket();
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) 
	{
		
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.CLIENT);
	}

}