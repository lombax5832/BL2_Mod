package BL2.common;

import java.awt.Color;
import java.util.List;

import org.lwjgl.util.vector.Vector;

import BL2.common.ItemGun.GunAtributes;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ISpecialArmor;

public class ItemArmorShield extends ItemArmor implements ISpecialArmor, IItemTickListener
{
	public static final int maxcharge = 200;

	public ItemArmorShield(int id, int renderindex, int armortype) 
	{
		super(id, EnumArmorMaterial.DIAMOND, renderindex, armortype);
		this.setMaxStackSize(1);
		this.setCreativeTab(BL2Core.tabBL2);
        this.setHasSubtypes(true);
        this.setMaxDamage(100);
	}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		NBTTagCompound tag = par1ItemStack.getTagCompound();
		if(tag == null)
		{
			tag = new NBTTagCompound();
			par1ItemStack.setTagCompound(tag);
		}
		
		par3List.clear();
		par3List.add("YOLO");
		par3List.add("Charge: " + tag.getInteger("charge"));
	}
	
	public float getDamageForItemStack(ItemStack it)
    {
		System.out.println("yes");
		NBTTagCompound tag = it.getTagCompound();
		if(tag == null)
		{
			
			tag = new NBTTagCompound();
			it.setTagCompound(tag);
		}
    	return tag.getInteger("charge") / maxcharge;
    }

	@Override
	public boolean onTick(EntityPlayer ep, ItemStack it) 
	{
		NBTTagCompound tag = it.getTagCompound();
		if(tag == null)
		{
			tag = new NBTTagCompound();
			it.setTagCompound(tag);
		}
		if(tag.getInteger("hitticker") > 0)
		{
			tag.setInteger("hitticker", tag.getInteger("hitticker") - 1);
			double distance = .75;//radius of particle circle
			for(int i = 0; i < 3; i++)//particles per tick
			{
				Vector v = new Vector((Math.random() * 2) - 1, (Math.random() * 2) - 1, (Math.random() * 2) - 1);
				v.normalize();
				for(int o = 0; o < 4; o++)
				{
					if(ep.getCurrentArmor(o) == it)
					{
						BL2Core.nethandler.sendParticlePacket(ep.worldObj,  v.x * distance, v.y * distance * 2, v.z * distance, ep.posX, ep.posY, ep.posZ, o);
					}
				}
			}
		}
		if(tag.getInteger("regenticker") > 0)
		{
			tag.setInteger("regenticker", tag.getInteger("regenticker") - 1);
		}
		else if(tag.getInteger("charge") < maxcharge)
		{
			tag.setInteger("charge", tag.getInteger("charge") + 1);
		}
		else if(tag.getInteger("charge") >= maxcharge)
		{
			tag.setInteger("charge", maxcharge);
		}
		return false;
	}

	@SuppressWarnings("unused")
	@Override
	public ArmorProperties getProperties(EntityLiving player, ItemStack armor, DamageSource source, double damage, int slot) 
	{
		if(true)//shield activated
		{
			double absorbed = 0;
			NBTTagCompound tag = armor.getTagCompound();
			if(tag == null)
			{
				tag = new NBTTagCompound();
				armor.setTagCompound(tag);
			}
			while(tag.getInteger("charge") >= 10)//can take more of the damage
			{
				//use energy for one damage
				absorbed++;
				tag.setInteger("charge", tag.getInteger("charge") - 10);
			}
			tag.setInteger("regenticker", 100);
			
			//got hit
			tag.setInteger("hitticker", 20);
			
			return new ISpecialArmor.ArmorProperties(10, damage / absorbed, Integer.MAX_VALUE);
		}
		return new ISpecialArmor.ArmorProperties(0, getBaseAbsorptionRatio(), 0);
	}
	
	private double getBaseAbsorptionRatio()
	{
		switch(this.armorType)
		{
		case 0:
			return .15;
		case 1:
			return .4;
		case 2:
			return .3;
		case 3:
			return .15;
		default:
			return 0;
		}
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLiving entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		
	}
	
	public static class Vector
	{
		double x;
		double y; 
		double z;
		
		public Vector(double x, double y, double z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public void normalize()
		{
			double d = length();
			x /= d;
			y /= d;
			z /= d;
		}
		
		public double length()
		{
			return Math.sqrt((x * x) + (y * y) + (z * z));
		}
	}
}