package BL2.common;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import vazkii.codebase.common.CommonUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityGrenade extends Entity implements IProjectile
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	protected boolean inGround = false;
	/**
	 * Is the entity that throws this 'thing' (snowball, ender pearl, eye of
	 * ender or potion)
	 */
	private EntityLiving thrower;
	private String ownername = null;
	private int ticksInGround;
	private int ticksInAir = 0;

	public EntityGrenade(World world)
	{
		super(world);
		setSize(0.25F, 0.25F);
	}

	protected void entityInit()
	{
	}

	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge length * 64 * renderDistanceWeight
	 * Args: distance
	 */
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double par1)
	{
		double d = boundingBox.getAverageEdgeLength() * 4.0D;
		d *= 64.0D;
		return par1 < d * d;
	}

	public EntityGrenade(World world, EntityLiving el)
	{
		super(world);
		thrower = el;
		setSize(0.25F, 0.25F);
		// setLocationAndAngles(thrower.posX, thrower.posY +
		// (double)thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw +
		// ((float)Math.random() - .5F) * 6 * (1F / 1F), thrower.rotationPitch +
		// ((float)Math.random() - .5F) * 6 * (1F / 1F));
		setLocationAndAngles(el.posX, el.posY + el.getEyeHeight(), el.posZ, el.rotationYaw, el.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		float var3 = 0.4F;
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI) * var3;
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI) * var3;
		motionY = -MathHelper.sin((rotationPitch + func_70183_g()) / 180.0F * (float)Math.PI) * var3;
		setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
	}

	public EntityGrenade(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		ticksInGround = 0;
		setSize(0.25F, 0.25F);
		setPosition(par2, par4, par6);
		yOffset = 0.0F;
	}

	protected float func_70182_d()
	{
		return 1.5F;
	}

	protected float func_70183_g()
	{
		return 0.0F;
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= (double)var9;
		par3 /= (double)var9;
		par5 /= (double)var9;
		par1 += rand.nextGaussian() * 0.007499999832361937D * (double)par8;
		par3 += rand.nextGaussian() * 0.007499999832361937D * (double)par8;
		par5 += rand.nextGaussian() * 0.007499999832361937D * (double)par8;
		par1 *= (double)par7;
		par3 *= (double)par7;
		par5 *= (double)par7;
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		prevRotationYaw = rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float)(Math.atan2(par3, (double)var10) * 180.0D / Math.PI);
		ticksInGround = 0;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double par1, double par3, double par5)
	{
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			prevRotationYaw = rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float)(Math.atan2(par3, (double)var7) * 180.0D / Math.PI);
		}
	}

	public EntityLiving parent;
	public double difX;
	public double difY;
	public double difZ;
	public int timeTilBoom;

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		if(parent == null)
		{
			super.onUpdate();
			if(inGround)
			{
				int id = worldObj.getBlockId(xTile, yTile, zTile);
				if(id == inTile)
				{
					ticksInGround++;
					if(ticksInGround == 1200)
					{
						setDead();
					}
					return;
				}
				inGround = false;
				motionX *= (double)(rand.nextFloat() * 0.2F);
				motionY *= (double)(rand.nextFloat() * 0.2F);
				motionZ *= (double)(rand.nextFloat() * 0.2F);
				ticksInGround = 0;
				ticksInAir = 0;
			} else
			{
				ticksInAir++;
			}
			Vec3 pos = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			Vec3 newpos = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition collision = worldObj.rayTraceBlocks(pos, newpos);
			pos = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			newpos = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			if(collision != null)
			{
				newpos = worldObj.getWorldVec3Pool().getVecFromPool(collision.hitVec.xCoord, collision.hitVec.yCoord, collision.hitVec.zCoord);
			}
			if(!worldObj.isRemote)
			{
				Entity en = null;
				List entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
				double shortestdis = 0.0D;
				EntityLiving thrower = getThrower();
				for(int i = 0; i < entities.size(); i++)
				{
					Entity entity = (Entity)entities.get(i);
					if(entity.canBeCollidedWith() && (entity != thrower || ticksInAir >= 5))
					{
						AxisAlignedBB bb = entity.boundingBox.expand(0.3F, 0.3F, 0.3F);
						MovingObjectPosition col = bb.calculateIntercept(pos, newpos);
						if(col != null)
						{
							double dis = pos.distanceTo(col.hitVec);
							if(dis < shortestdis || shortestdis == 0.0D)
							{
								en = entity;
								shortestdis = dis;
							}
						}
					}
				}
				if(en != null)
				{
					collision = new MovingObjectPosition(en);
				}
			}
			if(collision != null)
			{
				if(collision.typeOfHit == EnumMovingObjectType.TILE && worldObj.getBlockId(collision.blockX, collision.blockY, collision.blockZ) == Block.portal.blockID)
				{
					setInPortal();
				}else
				{
					onImpact(collision);
				}
			}
			//worldObj.spawnParticle("largeexplode", posX, posY, posZ, -motionX, -motionY + 0.2D, -motionZ);
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float hspeed = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			for(rotationPitch = (float)(Math.atan2(motionY, (double)hspeed) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
			{
				;
			}
			while(rotationPitch - prevRotationPitch >= 180.0F)
			{
				prevRotationPitch += 360.0F;
			}
			while(rotationYaw - prevRotationYaw < -180.0F)
			{
				prevRotationYaw -= 360.0F;
			}
			while(rotationYaw - prevRotationYaw >= 180.0F)
			{
				prevRotationYaw += 360.0F;
			}
			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float slowdown = 0.99F;
			float grav = getGravityVelocity();
			if(isInWater())
			{
				for(int i = 0; i < 4; i++)
				{
					float var20 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * var20, posY - motionY * var20, posZ - motionZ * var20, motionX, motionY, motionZ);
				}
				slowdown = 0.8F;
			}
			motionX *= (double)slowdown;
			motionY *= (double)slowdown;
			motionZ *= (double)slowdown;
			motionY -= (double)grav;
			setPosition(posX, posY, posZ);
		}else
		{
			setPosition(parent.posX + difX, parent.posY + difY, parent.posZ + difZ);
			timeTilBoom--;
			if(timeTilBoom <= 0)
			{
				CustomExplosion ex = new CustomExplosion(worldObj, this, posX, posY, posZ, 3.0F);
				ex.doExplosionA();
				ex.doExplosionB(false);
				setDead();
			}
		}
		// motionY -= (double)var19;
		
		// get the entities nearby (aka within a boundingbox near this
		// entity)
		// worldObj.getEntitiesWithinAABBExcludingEntity(this,
		// par2AxisAlignedBB)
		// iterate through the list and if each one is a entityplayer, move
		// a small amount toward them
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	protected float getGravityVelocity()
	{
		return 0.03F;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition mop)
	{
		if(mop.entityHit != null)
		{
			mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0);
			if(mop.entityHit instanceof EntityLiving)
			{
				parent = (EntityLiving)mop.entityHit;
				difX = posX - parent.posX;
				difY = posY - parent.posY;
				difZ = posZ - parent.posZ;
				timeTilBoom = 60;
				motionX = 0;
				motionY = 0;
				motionZ = 0;
				return;
			}
		}
		
		if(parent == null)
		{
			CustomExplosion ex = new CustomExplosion(worldObj, this, posX, posY, posZ, 3.0F);
			ex.doExplosionA();
			ex.doExplosionB(false);
			
			for(int i = 0; i < 8; i++)
			{
				// worldObj.spawnParticle("snowballpoof", posX,
				// posY, posZ, 0.0D, 0.0D, 0.0D);
			}
			if(!worldObj.isRemote)
			{
				setDead();
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short)xTile);
		par1NBTTagCompound.setShort("yTile", (short)yTile);
		par1NBTTagCompound.setShort("zTile", (short)zTile);
		par1NBTTagCompound.setByte("inTile", (byte)inTile);
		par1NBTTagCompound.setByte("inGround", (byte)(inGround ? 1 : 0));
		if((ownername == null || ownername.length() == 0) && thrower != null && thrower instanceof EntityPlayer)
		{
			ownername = thrower.getEntityName();
		}
		par1NBTTagCompound.setString("ownerName", ownername == null ? "" : ownername);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		xTile = par1NBTTagCompound.getShort("xTile");
		yTile = par1NBTTagCompound.getShort("yTile");
		zTile = par1NBTTagCompound.getShort("zTile");
		inTile = par1NBTTagCompound.getByte("inTile") & 255;
		inGround = par1NBTTagCompound.getByte("inGround") == 1;
		ownername = par1NBTTagCompound.getString("ownerName");
		if(ownername != null && ownername.length() == 0)
		{
			ownername = null;
		}
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	public EntityLiving getThrower()
	{
		if(thrower == null && ownername != null && ownername.length() > 0)
		{
			thrower = worldObj.getPlayerEntityByName(ownername);
		}
		return thrower;
	}
}
