package BL2.common;

import java.util.List;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGrenade extends EntityThrowable
{
    public EntityGrenade(World world)
    {
        super(world);
    }

    public EntityGrenade(World world, EntityLiving el)
    {
        super(world, el);
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
        		stuckTo = mop.entityHit;
        		BL2Core.nethandler.sendParentPacket(worldObj, this, (EntityLiving) stuckTo);
        		difX = mop.entityHit.posX - posX;
        		difY = mop.entityHit.posY - posY;
        		difZ = mop.entityHit.posZ - posZ;
        		timeTilBoom = 60;
        		return;
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
    }
}
