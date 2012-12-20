package BL2.common;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class NetworkHandler implements IPacketHandler
{
	public static final int particlePacketID = 0;
	public static final int reloadPacketID = 1;
	
	public void sendParticlePacket(World world, double x, double y, double z, EntityPlayer player, int inventoryIndex)
	{
		try
        {
			ByteArrayOutputStream baout = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baout);
            out.writeByte(0);
            out.writeInt(world.provider.dimensionId);
            out.writeInt(inventoryIndex);
            out.writeDouble(x);
            out.writeDouble(y);
            out.writeDouble(z);
            out.writeInt(player.entityId);
            out.close();
            Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.channel = "bl2";
            packet.isChunkDataPacket = false;
            packet.data = baout.toByteArray();
            packet.length = baout.size();
            Iterator<EntityPlayer> players = world.playerEntities.iterator();

            while (players.hasNext())
            {
                EntityPlayer otherplayer = players.next();
                
                if (otherplayer.getDistanceSqToEntity(player) < 256.0D)
                {
                	//System.out.println("added for");
                    PacketDispatcher.sendPacketToPlayer(packet, (Player)otherplayer);
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
