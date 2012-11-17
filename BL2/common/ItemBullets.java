package BL2.common;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ItemBullets extends Item
{
	public static final String[] ammoTypes = new String[] {"Pistol", "SMG", "Assault Rifle", "Rocket Launcher", "Sniper", "Shotgun"};
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
		 par3List.clear();

		 par3List.add(ammoTypes[par1ItemStack.getItemDamage()] + " Ammo");
		 
	 }

	
    protected ItemBullets(int id)
    {
        super(id);
        this.maxStackSize = 64;
        this.setCreativeTab(BL2Core.tabBL2);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    public void getSubItems(int i, CreativeTabs tabs, List l)
    {
        for (int j = 0; j < 6; j++)
        {
            ItemStack stack = new ItemStack(this, 64, j);
            l.add(stack);
        }
    }

    public int getIconFromDamage(int par1)
    {
        return 16 + par1;
    }

    public String getTextureFile()
    {
        return "/BL2/textures/Items.png";
    }
}
