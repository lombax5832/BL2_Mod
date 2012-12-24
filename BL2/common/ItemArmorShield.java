package BL2.common;

import java.awt.Color;
import java.util.List;

import org.lwjgl.util.vector.Vector;

import BL2.common.ItemGun.GunAtributes;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.util.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IArmorTextureProvider;
import net.minecraftforge.common.ISpecialArmor;

public class ItemArmorShield extends ItemArmor implements ISpecialArmor, IItemTickListener, IArmorTextureProvider
{
	boolean isHit = false;

	public ItemArmorShield(int id, int renderindex, int armortype) 
	{
		super(id, EnumArmorMaterial.DIAMOND, renderindex, armortype);
		this.setMaxStackSize(1);
		this.setCreativeTab(BL2Core.tabBL2);
        this.setHasSubtypes(true);
        this.setMaxDamage(100);
	}
	
	public boolean getHit(ShieldAtributes atr, double damage){
        int dmg = (int) damage;
        atr.absorbed = 0;
        if(atr.canHit = true && atr.lastHit >= 5  && atr.charge >= 5)
        {
        	for(int i = 0; i < dmg; i++)
			{
        		atr.absorbed++;
	        	atr.lastHit = 0;
	        	atr.charge = atr.charge - (5);
	        	if(atr.charge < 5){
	        		break;
	        	}
			}
        	atr.canHit = false;
        	return true;
        }
        return false;
	}
	
	public boolean charging = false;
	
	public void getSubItems(int i, CreativeTabs tabs, List l)
    {
		ItemStack stack = new ItemStack(this);
        ShieldAtributes atr = new ShieldAtributes(stack);
        atr.rechargeTicker = 1;
        l.add(stack);
        atr.save(stack);
    }
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		ShieldAtributes atr = new ShieldAtributes(par1ItemStack);
		
		par3List.clear();
		par3List.add("Shield");
		par3List.add("Charge: " + atr.charge);
	}
	
	public float getDamageForItemStack(ItemStack it)
    {
		//System.out.println("yes");
		ShieldAtributes atr = new ShieldAtributes(it);
    	return atr.charge / atr.maxcharge;
    }

	@Override
	public boolean onTick(EntityPlayer ep, ItemStack it) 
	{
		ShieldAtributes atr = new ShieldAtributes(it);
		//System.out.println(atr.canHit);
		//System.out.println(atr.rechargeDelay);
		//System.out.println((float)atr.charge/atr.maxcharge);
		if(atr.hitTicker > 0 || this.charging == true)
		{
			atr.hitTicker--;
			double distance = .75;//radius of particle circle
			for(int i = 0; i < 10; i++)//particles per tick
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
		if(atr.lastHit >= 0 && atr.lastHit < 5){
			atr.lastHit++;
			atr.save(it);
		}
		if(atr.lastHit >= 5 && atr.canHit == false){
			atr.canHit = true;
			atr.save(it);
		}
		if(atr.rechargeTicker > 0)
		{
			atr.rechargeTicker--;
			atr.save(it);
		}
		if((atr.rechargeTicker == 0) && (atr.charge < atr.maxcharge))
		{
			this.charging = true;
			atr.charge++;
			atr.save(it);
		}
		else if(atr.charge >= atr.maxcharge)
		{
			this.charging = false;
			atr.charge = atr.maxcharge;
			atr.save(it);
		}
		return false;
	}

	@Override
	public ArmorProperties getProperties(EntityLiving player, ItemStack armor, DamageSource source, double damage, int slot) 
	{
		//if(true)//shield activated
		//{
			
			double absorbed = 0;
			ShieldAtributes atr = new ShieldAtributes(armor);
			int prevCharge = atr.charge;
			if(atr.charge > 0){
				atr.hitTicker = 20;
			}
			if(atr.charge > 0)//can take more of the damage
			{
				//use energy for one damage
				//atr.charge = atr.charge - 10;
				getHit(atr, damage);
			}
			atr.rechargeTicker = atr.rechargeDelay;
			
			//got hit
			
			//getHit(armor);
			
			atr.save(armor);
			if(prevCharge > 0){
				return new ISpecialArmor.ArmorProperties(10, atr.absorbed/damage, Integer.MAX_VALUE);
			}else
			{
				return new ISpecialArmor.ArmorProperties(0, getBaseAbsorptionRatio(), 0);
			}
		//}
		//return new ISpecialArmor.ArmorProperties(0, getBaseAbsorptionRatio(), 0);
	}
	
	private double getBaseAbsorptionRatio()
	{
		switch(this.armorType)
		{
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 0;
		case 3:
			return 0;
		default:
			return 0;
		}
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		ShieldAtributes atr = new ShieldAtributes(armor);
		int display = (int) (((float)atr.charge/atr.maxcharge) * 20);
		//System.out.println("Armor: "+ display);
		return display;
	}

	@Override
	public void damageArmor(EntityLiving entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		
	}
	
	public static class ShieldAtributes
    {
        /**
         * number of ticks between fires, should be >0
         */
		public int maxcharge = 200;
        public int charge;
        public int rechargeDelay = 100;
        public int rechargeTicker;
        public int rechargeRate;
        public int hitTicker;
        public int lastHit;
        public int absorbed;
        public boolean canHit = true;

        public ShieldAtributes(ItemStack it)
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
            
            tag.setInteger("maxcharge", maxcharge);
            tag.setInteger("charge", charge);
            tag.setInteger("rechargeDelay", rechargeDelay);
            tag.setInteger("rechargeTicker", rechargeTicker);
            tag.setInteger("rechargeRate", rechargeRate);
            tag.setInteger("hitTicker", hitTicker);
            tag.setInteger("lastHit", lastHit);
            tag.setInteger("absorbed", absorbed);
            tag.setBoolean("canHit", canHit);
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

            maxcharge = tag.getInteger("maxcharge");
            charge = tag.getInteger("charge");
            rechargeDelay = tag.getInteger("rechargeDelay");
            rechargeTicker = tag.getInteger("rechargeTicker");
            rechargeRate = tag.getInteger("rechargeRate");
            hitTicker = tag.getInteger("hitTicker");
            lastHit = tag.getInteger("lastHit");
            absorbed = tag.getInteger("absorbed");
            canHit = tag.getBoolean("canHit");
        }
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
	
	public String getTextureFile()
    {
        return "/BL2/textures/Items.png";
    }

	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		if (itemstack.itemID == BL2Core.shield.shiftedIndex){
			return "/BL2/textures/Armor/shield_1.png";
		}
		return null;
	}
}