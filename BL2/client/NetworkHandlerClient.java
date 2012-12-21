package BL2.client;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.client.multiplayer.WorldClient;
import BL2.common.NetworkHandler;
import BL2.common.ShieldFX;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class NetworkHandlerClient extends NetworkHandler
{
	
	/*public void sendParticlePacket(World world, double x, double y, double z, int inventoryIndex)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			super.sendParticlePacket(world, x, y, z, inventoryIndex);
		}else
		{
			EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
			 ShieldFX fx = new ShieldFX(world, player, player.getCurrentArmor(inventoryIndex), x, y, z, Color.CYAN);
	         ModLoader.getMinecraftInstance().effectRenderer.addEffect(fx);
		}
	}*/
	
	public void sendReloaderPacket()
	{
		try
        {
			ByteArrayOutputStream baout = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baout);
            out.writeByte(1);
            out.close();
            Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.channel = "bl2";
            packet.isChunkDataPacket = false;
            packet.data = baout.toByteArray();
            packet.length = baout.size();

            PacketDispatcher.sendPacketToServer(packet);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}
	
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player p)
    {
		//System.out.println("recieved packet");
        ByteArrayInputStream in = new ByteArrayInputStream(packet.data, 1, packet.data.length - 1);

        try
        {

        	if(packet.data.length < 1)
        	{
        		
        		System.out.println("No packets");
        		
        	}
        	
            switch (packet.data[0])
            {
                case NetworkHandler.particlePacketID:
                {
                	//System.out.println("spawned");
                	DataInputStream din = new DataInputStream(in);
                	byte dummy = din.readByte();
                	int dimension = din.readInt();
                	int index = din.readInt();
                    double x = din.readDouble();
                    double y = din.readDouble();
                    double z = din.readDouble();
                    int playerId = din.readInt();
                    //String username = din.readUTF();
//                    while(true)
//                    {
//                    	try
//                    	{
//                    		username += din.readChar();
//                    	} catch (NullPointerException e)
//            		    {
//            		    	System.out.print("");
//                    		break;
//            		    }
//                    }
                    WorldClient world = Minecraft.getMinecraft().theWorld;

                    EntityPlayer player = (EntityPlayer) world.getEntityByID(playerId);
                    
                    if (world.provider.dimensionId != dimension)
                    {
                        return;
                    }
                    //EntityPlayer player = world.getPlayerEntityByName(username);
                    
//                    if((Player)player == p)
//                    {
//                    	player = (EntityPlayer)p;
//                    	ShieldFX fx = new ShieldFX(world, player, player.getCurrentArmor(index), x, y-1.5, z, Color.CYAN);
//                    	ModLoader.getMinecraftInstance().effectRenderer.addEffect(fx);
//                    }else
//                    {
//                    	ShieldFX fx = new ShieldFX(world, player, player.getCurrentArmor(index), x, y, z, Color.CYAN);
//                    	ModLoader.getMinecraftInstance().effectRenderer.addEffect(fx);
//                    }
                    
                }
            }
        }
        catch (IOException var22)
        {
            var22.printStackTrace();
        }
    }
}
