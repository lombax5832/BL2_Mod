package BL2.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public interface IItemTickListener {
	boolean onTick(EntityPlayer var1, ItemStack var2);
}
