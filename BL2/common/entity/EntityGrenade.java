package BL2.common.entity;

import java.util.List;

import BL2.common.BL2Core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

public class EntityGrenade extends EntityThrowable
{
    public boolean sticky = false;
    public boolean homing = false;
    public boolean sent = false;
	
    public EntityGrenade(World world)
    {
        super(world);
    }

    public EntityGrenade(World world, EntityLiving el, boolean isSticky, boolean isHoming)
    {
        super(world, el);
        this.sticky = isSticky;
        this.homing = isHoming;
    }

    public EntityGrenade(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
        	mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_85052_h()/*thrower*/), 0);
        	if(mop.entityHit instanceof EntityLiving)
        	{
        		if(sticky){
        			stuckTo = mop.entityHit;
	        		BL2Core.nethandler.sendGrenadePacket(worldObj, this, "parent", (EntityLiving) stuckTo);
	        		difX = mop.entityHit.posX - posX;
	        		difY = mop.entityHit.posY - posY;
	        		difZ = mop.entityHit.posZ - posZ;
	        		timeTilBoom = 60;
	        		return;
        		}else{
        			worldObj.newExplosion(this, posX, posY, posZ, 2.0F, false, false);
        			setDead();
        		}
        	}
        }

        for (int i = 0; i < 8; i++)
        {
            worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!worldObj.isRemote)
        {
        	worldObj.newExplosion(this, posX, posY, posZ, 2.0F, false, false);
     		setDead();
        }
    }
    
    public Entity stuckTo;
    public double difX;
    public double difY;
    public double difZ;
    public int timeTilBoom;
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	if(!sent && !FMLCommonHandler.instance().getEffectiveSide().isClient())
    	{
    		sent = true;
    		BL2Core.nethandler.sendGrenadePacket(worldObj, this, "homing", homing);
    	}
    	if(sticky){
	    	if(stuckTo != null && stuckTo.isDead)
	    	{
	    		stuckTo = null;
	    	}
	    	if(stuckTo == null)
	    	{
	    		super.onUpdate();
	    	}else
	    	{
	    		setPosition(stuckTo.posX - difX, stuckTo.posY - difY, stuckTo.posZ - difZ);
	    		timeTilBoom--;
	    		if(timeTilBoom < 0)
	    		{
	    			worldObj.newExplosion(this, posX, posY, posZ, 2.0F, false, false);
	    			
	    			setDead();
	    		}
	    	}
    	}else{
    		super.onUpdate();
    	}
    	if(homing){
    		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - 10, posY - 10, posZ - 10, posX + 10, posY + 10, posZ + 10));
    		EntityLiving current = null;
    		double minDisSq = Integer.MAX_VALUE;
    		for(Entity e : list)
    		{
    			if(e != func_85052_h() && e instanceof EntityLiving && !(e instanceof EntityPlayer))
    			{
    				if(e.getDistanceSqToEntity(this) < minDisSq)
    				{
    					current = (EntityLiving) e;
    					minDisSq = e.getDistanceSqToEntity(this);
    				}
    			}
    		}
    		
    		if(current != null)
    		{
    			double x = current.posX - posX;
    			double y = (current.posY + current.getEyeHeight()) - posY;
    			double z = current.posZ - posZ;
    			
    			double length = Math.sqrt(x * x + y * y + z * z);
    			//System.out.println("Current: " + current.getEntityName() + " X:" + x + " Y:" + y + " X:" + x);
    			
    			
    			x /= length * 1;
    			y /= length * 1;
    			z /= length * 1;
    			
    			
    			moveEntity(x, y, z);
    			
    		}
    	}
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        
        nbt.setBoolean("homing", homing);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    	super.readEntityFromNBT(nbt);
    	
    	homing = nbt.getBoolean("homing");
    }
}
