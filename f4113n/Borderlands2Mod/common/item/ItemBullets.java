package f4113n.Borderlands2Mod.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import f4113n.Borderlands2Mod.common.BL2Core;

public class ItemBullets extends Item
{
	public static final String[] ammoTypes = new String[] {"", "Pistol", "SMG", "Assault Rifle", "Rocket Launcher", "Sniper", "Shotgun"};
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
		 par3List.clear();

		 par3List.add(ammoTypes[par1ItemStack.getItemDamage()] + " Ammo");
		 
	 }

	
    public ItemBullets(int id)
    {
        super(id);
        this.maxStackSize = 64;
        this.setCreativeTab(BL2Core.tabBL2);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    public void getSubItems(int i, CreativeTabs tabs, List l)
    {
        for (int j = 1; j < 7; j++)
        {
            ItemStack stack = new ItemStack(this, 64, j);
            l.add(stack);
        }
    }

    public int getIconFromDamage(int par1)
    {
        return 15 + par1;
    }

    public String getTextureFile()
    {
        return "/f4113n/Borderlands2Mod/textures/Items.png";
    }
    
    public boolean isFull3D()
    {
        return true;
    }
}
