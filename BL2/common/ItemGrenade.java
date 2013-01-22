package BL2.common;

import java.util.List;

import BL2.common.ItemGun.GunAtributes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenade extends Item
{
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
   	par3List.clear();
   	par3List.add("Grenade");
	 }
	
    public ItemGrenade(int par1)
    {
        super(par1);
        maxStackSize = 16;
        setCreativeTab(BL2Core.tabBL2);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer ep)
    {
        if (!ep.capabilities.isCreativeMode)
        {
            item.stackSize--;
        }

        world.playSoundAtEntity(ep, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
        		world.spawnEntityInWorld(new EntityGrenade(world, ep));
        }

        return item;
    }
}
