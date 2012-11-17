package BL2.common;

import java.util.List;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemTemp extends Item{

	public ItemTemp(int par1)
    {
        super(par1);
        this.maxStackSize = 1;
        this.setCreativeTab(BL2Core.tabBL2);
        this.setIconIndex(48);
    }
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
		 par3List.clear();

		 par3List.add("Temp");
		 
	 }
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		par3EntityPlayer.dropPlayerItem(ItemGun.getRandomGun());
		par1ItemStack.stackSize = 0;
		return par1ItemStack;
    }
	
	public String getTextureFile(){
		return "/BL2/textures/Items.png";
	}
}
