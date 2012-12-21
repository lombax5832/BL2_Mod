package BL2.client;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class BL2KeyHandler extends KeyHandler{

	public static KeyBinding key = new KeyBinding("Reload Gun", Keyboard.KEY_R);
	
	public BL2KeyHandler() {
		super(new KeyBinding[] { key }, new boolean[] { false });
	}
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Reload Gun";
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}
	@Override
	public void keyDown(EnumSet<TickType> types,
			net.minecraft.client.settings.KeyBinding kb, boolean tickEnd,
			boolean isRepeat) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyUp(EnumSet<TickType> types,
			net.minecraft.client.settings.KeyBinding kb, boolean tickEnd) {
		// TODO Auto-generated method stub
		
	}

}
