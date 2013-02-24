package f4113n.Borderlands2Mod.common.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import f4113n.Borderlands2Mod.common.BL2Core;
import f4113n.Borderlands2Mod.common.entity.EntityGrenade;
import f4113n.Borderlands2Mod.common.item.ItemGun;

public class NetworkHandler implements IPacketHandler
{
	public static final int particlePacketID = 0;
	public static final int reloadPacketID = 1;
	public static final int grenadePacketID = 2;
	
	public void sendParticlePacket(World world, double x, double y, double z, int playerID, int type, int inventoryIndex)
	{
		try
        {
			ByteArrayOutputStream baout = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baout);
            out.writeByte(particlePacketID);
            out.writeInt(world.provider.dimensionId);
            out.writeInt(inventoryIndex);
            out.writeDouble(x);
            out.writeDouble(y);
            out.writeDouble(z);
            out.writeInt(playerID);
            out.writeInt(type);
            out.close();
            Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.channel = "bl2";
            packet.isChunkDataPacket = false;
            packet.data = baout.toByteArray();
            packet.length = baout.size();
            Iterator<EntityPlayer> players = world.playerEntities.iterator();

            while (players.hasNext())
            {
                EntityPlayer player = players.next();
                
                Entity hostPlayer = world.getEntityByID(playerID);
                
                if(player.getDistanceSqToEntity(hostPlayer) < 64.0D){
                	PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
                }
            }
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}
	
	public void sendReloaderPacket()
	{
		
	}
	
	public void sendGrenadePacket(World world, EntityGrenade grenade, String var, Object arg)
	{
		try
        {
			ByteArrayOutputStream baout = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baout);
            out.writeByte(grenadePacketID);
            out.writeInt(world.provider.dimensionId);
            out.writeInt(grenade.entityId);
            out.writeUTF(var);
            if(var.equals("parent"))
            {
            	 out.writeInt(((Entity)arg).entityId);
            }else
            if(var.equals("homing"))
            {
            	out.writeBoolean((Boolean)arg);
            }
            out.close();
            Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.channel = "bl2";
            packet.isChunkDataPacket = false;
            packet.data = baout.toByteArray();
            packet.length = baout.size();
            
            Iterator<EntityPlayer> players = world.playerEntities.iterator();

            while (players.hasNext())
            {
                EntityPlayer player = players.next();
                
                if(player.getDistanceSqToEntity(grenade) < 64.0D)
                {
                	PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
                }
            }
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}

	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player p) 
	{
		EntityPlayer player = null;
		
        ByteArrayInputStream in = new ByteArrayInputStream(packet.data, 1, packet.data.length - 1);

        try
        {
        	DataInputStream din = new DataInputStream(in);
            switch (packet.data[0])
            {
                case NetworkHandler.reloadPacketID:
                {
                    player = (EntityPlayer)p;
                    
                    ItemStack stack = player.getCurrentEquippedItem();
                    if(stack.getItem() == BL2Core.guns)
                    {
                    	ItemGun.reload(stack);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
	}
}