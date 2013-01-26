package BL2.common.handler;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

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
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if (!types.equals(EnumSet.of(TickType.SERVER)) || !tickEnd) return;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}

}
