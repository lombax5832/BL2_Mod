package BL2.common.item;

import java.util.List;

import BL2.common.BL2Core;
import BL2.common.entity.EntityGrenade;
import BL2.common.item.ItemArmorShield.ShieldAtributes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemGrenade extends Item
{
    public ItemGrenade(int par1)
    {
        super(par1);
        maxStackSize = 16;
        setCreativeTab(BL2Core.tabBL2);
        setIconCoord(12, 0);
    }

    public void getSubItems(int i, CreativeTabs tabs, List l)
    {
		for (int j = 1; j < 5; j++)
        {
			ItemStack stack = new ItemStack(this, 1, j);
	        GrenadeAtributes atr = new GrenadeAtributes(stack);
	        if(j == 1){
	        	atr.homing = false;
	        	atr.sticky = false;
	        }else 
	        if(j == 2){
	        	atr.homing = true;
	        	atr.sticky = false;
	        }else
        	if(j == 3){
        		atr.homing = false;
	        	atr.sticky = true;
        	}else
        	if(j == 4){
        		atr.homing = true;
	        	atr.sticky = true;
        	}else
//        	if(j == 5){
//        		atr.homing = false;
//	        	atr.sticky = false;
//	        	atr.hackySack = true;
//        	}
	        l.add(stack);
	        atr.save(stack);
        }
    }
    
    public void addName(List par3List, GrenadeAtributes atr){
    	String name = "";
    	boolean changedName = false;
    	if(atr.sticky){
    		name += "Sticky ";
    		changedName = true;
    	}
    	if(atr.homing){
    		name += "Homing ";
    		changedName = true;
    	}
    	if(atr.hackySack){
    		name += "Hacky Sack ";
    		changedName = true;
    	}
    	if(changedName == false){
    		name += "Lobbed ";
    	}
    	name += "Grenade";
    	par3List.add("" + 	name + "");
    }
    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
    	GrenadeAtributes atr = new GrenadeAtributes(par1ItemStack);	
		par3List.clear();
		addName(par3List, atr);
	}
    
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            //item.stackSize--;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
        	GrenadeAtributes atr = new GrenadeAtributes(item);
        	world.spawnEntityInWorld(new EntityGrenade(world, player, atr.sticky, atr.homing, atr.hackySack));
        }

        return item;
    }
    
    public static class GrenadeAtributes
    {
		public boolean sticky;
		public boolean homing;
		public boolean hackySack;

        public GrenadeAtributes(ItemStack it)
        {
            load(it);
        }

        public void save(ItemStack it)
        {
	    boolean newTag = false;
            NBTTagCompound tag = it.getTagCompound();
            if(tag == null) {
            	tag = new NBTTagCompound();
            	newTag = true;
            }
            
            tag.setBoolean("sticky", sticky);
            tag.setBoolean("homing", homing);
            tag.setBoolean("hackySack", hackySack);
		    if(newTag)
	            	it.setTagCompound(tag);
	        }

        public void load(ItemStack it)
        {
            NBTTagCompound tag = it.getTagCompound();

            if (tag == null)
            {
                return;
            }

            sticky = tag.getBoolean("sticky");
            homing = tag.getBoolean("homing");
            hackySack = tag.getBoolean("hackySack");
        }
    }
}
