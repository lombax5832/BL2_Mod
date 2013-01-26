package BL2.common.item;

import java.util.List;

import BL2.common.BL2Core;
import BL2.common.handler.IItemTickListener;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.IArmorTextureProvider;
import net.minecraftforge.common.ISpecialArmor;

public class ItemArmorShield extends ItemArmor implements ISpecialArmor, IItemTickListener, IArmorTextureProvider
{
	private String itemName;
	
	public static final String[] shieldNames = new String[] {"","Standard Shield"	, "Turtle Shield"	, "Booster Shield"	, "Absorption Shield"	, "Amp Shield"};
	public static final String[] Companies = new String[] {"", 	"Tediore"			, "Pangolin"		, "Dahl"			, "Vladof"				, "Hyperion"};
	
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
        		atr.charging = false;
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
		for (int j = 1; j < 6; j++)
        {
			if(j != 3){
				ItemStack stack = new ItemStack(this, 1, j);
		        ShieldAtributes atr = new ShieldAtributes(stack);
				if(j == 2){
					atr.maxcharge = 400;
					atr.rechargeDelay = 400;
					atr.chargeRate = 2;
				}
		        atr.rechargeTicker = 1;
		        atr.charge = atr.maxcharge;
		        l.add(stack);
		        atr.save(stack);
			}
        }
    }
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		ShieldAtributes atr = new ShieldAtributes(par1ItemStack);
		
		par3List.clear();
		par3List.add(shieldNames[par1ItemStack.getItemDamage()]);
		par3List.add("Charge: " + atr.charge + "/" + atr.maxcharge);
		par3List.add("Recharge Delay: " + atr.rechargeDelay/20 + " Seconds");
		par3List.add("Recharge Rate: " + atr.chargeRate*20 +" Per Second");
		if(par1ItemStack.getItemDamage() == 5){
			par3List.add("Amp Damage: " + ((float)atr.amp/2) + " Hearts while shield is full");
		}
	}
	
	public float getDamageForItemStack(ItemStack it)
    {
		//System.out.println("yes");
		ShieldAtributes atr = new ShieldAtributes(it);
		float damage = ((float)atr.charge/atr.maxcharge);
		//System.out.println((float)atr.charge/atr.maxcharge);
    	return 1 - damage;
    }

	@Override
	public boolean onTick(EntityPlayer ep, ItemStack it) 
	{
		ShieldAtributes atr = new ShieldAtributes(it);
		//System.out.println(atr.canHit);
		//System.out.println(atr.rechargeDelay);
		//System.out.println((float)atr.charge/atr.maxcharge);
		if(atr.hitTicker > 0 || atr.charging == true)
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
						int playerID = ep.entityId;
						BL2Core.nethandler.sendParticlePacket(ep.worldObj,  v.x * distance, v.y * distance * 1.5, v.z * distance, playerID, it.getItemDamage(), o);
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
			atr.charging = true;
			if(atr.charge + atr.chargeRate <= atr.maxcharge){
				atr.charge = atr.charge + atr.chargeRate;
			}else{
				atr.charge = atr.maxcharge;
			}
			atr.save(it);
		}
		else if(atr.charge >= atr.maxcharge)
		{
			atr.charging = false;
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
				if(armor.getItemDamage() == 4){
					float g = (float) Math.random() * 100;
					
					if(g < 5){
						
					}else{
						getHit(atr, damage);
					}
				}else{
					getHit(atr, damage);
				}
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
		return 0;
	}

	@Override
	public void damageArmor(EntityLiving entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		
	}
	
	public static class ShieldAtributes
    {
		public int company;
		public int maxcharge = 50;
        public int charge;
        public int chargeRate = 1;
        public int rechargeDelay = 200;
        public int rechargeTicker;
        public int rechargeRate;
        public int hitTicker;
        public int lastHit;
        public int absorbed;
        public int amp = 8;
        public boolean canHit = true;
        public boolean charging = false;

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
            
            tag.setInteger("company", company);
            tag.setInteger("maxcharge", maxcharge);
            tag.setInteger("charge", charge);
            tag.setInteger("chargeRate", chargeRate);
            tag.setInteger("rechargeDelay", rechargeDelay);
            tag.setInteger("rechargeTicker", rechargeTicker);
            tag.setInteger("rechargeRate", rechargeRate);
            tag.setInteger("hitTicker", hitTicker);
            tag.setInteger("lastHit", lastHit);
            tag.setInteger("absorbed", absorbed);
            tag.setInteger("amp", amp);
            tag.setBoolean("canHit", canHit);
            tag.setBoolean("charging", charging);
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

            company = tag.getInteger("company");
            maxcharge = tag.getInteger("maxcharge");
            charge = tag.getInteger("charge");
            chargeRate = tag.getInteger("chargeRate");
            rechargeDelay = tag.getInteger("rechargeDelay");
            rechargeTicker = tag.getInteger("rechargeTicker");
            rechargeRate = tag.getInteger("rechargeRate");
            hitTicker = tag.getInteger("hitTicker");
            lastHit = tag.getInteger("lastHit");
            absorbed = tag.getInteger("absorbed");
            amp = tag.getInteger("amp");
            canHit = tag.getBoolean("canHit");
            charging = tag.getBoolean("charging");
        }
    }
	
	public static class Vector
	{
		public double x;
		public double y; 
		public double z;
		
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
        return "/BL2/textures/Shields.png";
    }
	
	public int getIconFromDamage(int par1)
    {
        return par1 - 1;
    }

	@Override
	public String getArmorTextureFile(ItemStack itemstack) {	
		int damVal = itemstack.getItemDamage();
		if (itemstack.itemID == BL2Core.shield.shiftedIndex){
			return "/BL2/textures/Armor/shield_"+ damVal +".png";
		}
		return null;
	}
}