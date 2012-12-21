package BL2.common;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

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
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public void keyDown(EnumSet<TickType> types,
			net.minecraft.client.settings.KeyBinding kb, boolean tickEnd,
			boolean isRepeat) {
		// TODO Auto-generated method stub
		
		if(kb == reloadKey)
		{
			BL2Core.nethandler.sendReloaderPacket();
		}
		
	}

	@Override
	public void keyUp(EnumSet<TickType> types,
			net.minecraft.client.settings.KeyBinding kb, boolean tickEnd) {
		// TODO Auto-generated method stub
		
	}

}
