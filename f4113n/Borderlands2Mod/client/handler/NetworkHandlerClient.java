package f4113n.Borderlands2Mod.client.handler;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import f4113n.Borderlands2Mod.client.render.ShieldFX;
import f4113n.Borderlands2Mod.common.entity.EntityGrenade;
import f4113n.Borderlands2Mod.common.handler.NetworkHandler;

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
            out.writeByte(reloadPacketID);
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
	
	public Color getColor(int type){
		switch(type){
			case 1:
			{
				return Color.CYAN;
			}
			case 2:
			{
				return Color.RED;
			}
			case 3:
			{
				return Color.GREEN;
			}
			case 4:
			{
				return Color.MAGENTA;
			}
			case 5:
			{
				return Color.YELLOW;
			}
		}
		return Color.WHITE;
	}
	
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player p)
    {
		
		 EntityPlayer player = null;
		
		//System.out.println("recieved packet");
        ByteArrayInputStream in = new ByteArrayInputStream(packet.data, 1, packet.data.length - 1);

        try
        {

            switch (packet.data[0])
            {
                case NetworkHandler.particlePacketID:
                {
                	//System.out.println("spawned");
                	DataInputStream din = new DataInputStream(in);
                	int dimention = din.readInt();
                	int index = din.readInt();
                    double x = din.readDouble();
                    double y = din.readDouble();
                    double z = din.readDouble();
                    int playerID = din.readInt();
                    int type = din.readInt();
                    WorldClient world = Minecraft.getMinecraft().theWorld;

                    if (world.provider.dimensionId != dimention)
                    {
                        return;
                    }
                    if(player == null){
                    	player = (EntityPlayer) world.getEntityByID(playerID);
                    }
                    
                    if(player == p)
                    {
                    	ShieldFX fx = new ShieldFX(world, player, player.getCurrentArmor(index), x, y-1.5, z, getColor(type));
                    	ModLoader.getMinecraftInstance().effectRenderer.addEffect(fx);
                    }
                    else
                    {
                    	ShieldFX fx = new ShieldFX(world, player, player.getCurrentArmor(index), x, y, z, getColor(type));
                    	ModLoader.getMinecraftInstance().effectRenderer.addEffect(fx);
                    }
                    
                }
                case NetworkHandler.grenadePacketID:
                {
                	DataInputStream din = new DataInputStream(in);
                	int dimention = din.readInt();
                	
                	WorldClient world = Minecraft.getMinecraft().theWorld;

                    if (world.provider.dimensionId != dimention)
                    {
                        return;
                    }
                	
                    int gid = din.readInt();
                    EntityGrenade grenade = (EntityGrenade) world.getEntityByID(gid);
                    if(grenade == null)
                    {
                    	return;
                    }
                    
                    String var = din.readUTF();
                    if(var.equals("parent"))
                    {
                        grenade.stuckTo = (EntityLiving)world.getEntityByID(din.readInt());
                    }else
                    if(var.equals("homing"))
                    {
                        grenade.homing = din.readBoolean();
                    }
                }
            }
        }
        catch (IOException var22)
        {
            var22.printStackTrace();
        }
    }
}