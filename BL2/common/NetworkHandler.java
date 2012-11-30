package BL2.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class NetworkHandler implements IPacketHandler
{
	public static final int particlePacketID = 0;
	
	public void sendParticlePacket(World world, double x, double y, double z, double px, double py, double pz, int inventoryIndex)
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
            out.writeDouble(px);
            out.writeDouble(py);
            out.writeDouble(pz);
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

                if (player.getDistanceSq(px + x, py + y, pz + z) == 0D)
                {
                	System.out.println("added for");
                	y =+ 1.5D;
                	out.writeDouble(y);
                    PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
                }
                if (player.getDistanceSq(px + x, py + y, pz + z) < 256.0D)
                {
                	System.out.println("added for");
                    PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
                }
            }
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}

	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		
	}
}
