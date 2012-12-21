package BL2.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CustomExplosion
{
    /** whether or not the explosion sets fire to blocks around it */
    public boolean isFlaming = false;
    public boolean field_82755_b = true;
    private int field_77289_h = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public List field_77281_g = new ArrayList();
    private Map field_77288_k = new HashMap();

    public CustomExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
    {
        this.worldObj = par1World;
        this.exploder = par2Entity;
        this.explosionSize = par9;
        this.explosionX = par3;
        this.explosionY = par5;
        this.explosionZ = par7;
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void doExplosionA()
    {
        float var1 = this.explosionSize;
        HashSet var2 = new HashSet();
        int var3;
        int var4;
        int var5;
        double var15;
        double var17;
        double var19;
        this.field_77281_g.addAll(var2);
        this.explosionSize *= 2.0F;
        var3 = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
        var4 = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
        var5 = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
        int var28 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
        int var7 = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
        int var29 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
        List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var3, (double)var5, (double)var7, (double)var4, (double)var28, (double)var29));
        Vec3 var30 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);

        for (int var11 = 0; var11 < var9.size(); ++var11)
        {
            Entity var31 = (Entity)var9.get(var11);
            double var13 = var31.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

            if (var13 <= 1.0D)
            {
                var15 = var31.posX - this.explosionX;
                var17 = var31.posY + (double)var31.getEyeHeight() - this.explosionY;
                var19 = var31.posZ - this.explosionZ;
                double var33 = (double)MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D)
                {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    double var32 = (double)this.worldObj.getBlockDensity(var30, var31.boundingBox);
                    double var34 = (1.0D - var13) * var32;
                    var31.attackEntityFrom(DamageSource.explosion, (int)((var34 * var34 + var34) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D));
                    var31.motionX += var15 * var34;
                    var31.motionY += var17 * var34;
                    var31.motionZ += var19 * var34;

                    if (var31 instanceof EntityPlayer)
                    {
                        this.field_77288_k.put((EntityPlayer)var31, this.worldObj.getWorldVec3Pool().getVecFromPool(var15 * var34, var17 * var34, var19 * var34));
                    }
                }
            }
        }

        this.explosionSize = var1;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void doExplosionB(boolean par1)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);

        for (int i = 0; i < 5; i++)
        {
            double x = this.explosionX + ((this.explosionSize * Math.random()) - (this.explosionSize / 2F));
            double y = this.explosionY + ((this.explosionSize * Math.random()) - (this.explosionSize / 2F));
            double z = this.explosionZ + ((this.explosionSize * Math.random()) - (this.explosionSize / 2F));
            this.worldObj.spawnParticle("explode", x, y, z, 0, 0, 0);
            this.worldObj.spawnParticle("smoke", x, y, z,  0, 0, 0);
        }
    }

    public Map func_77277_b()
    {
        return this.field_77288_k;
    }
}
