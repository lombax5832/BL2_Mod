package BL2.common;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenade extends Item
{
    public ItemGrenade(int par1)
    {
        super(par1);
        maxStackSize = 16;
        setCreativeTab(CreativeTabs.tabAllSearch);
        setItemName("Grenade");
        LanguageRegistry.addName(this, "Grenade");
        setIconCoord(12, 0);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            item.stackSize--;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
        	world.spawnEntityInWorld(new EntityGrenade(world, player));
        }

        return item;
    }
}
