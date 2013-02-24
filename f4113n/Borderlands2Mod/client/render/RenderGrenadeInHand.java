package f4113n.Borderlands2Mod.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import f4113n.Borderlands2Mod.client.model.ModelGrenadeHeld;
import f4113n.Borderlands2Mod.client.model.ModelGrenadeInv;


public class RenderGrenadeInHand implements IItemRenderer {

    private ModelGrenadeHeld grenadeModel;
    private ModelGrenadeInv grenadeInv;

    public RenderGrenadeInHand() {

    	grenadeModel = new ModelGrenadeHeld();
    	grenadeInv = new ModelGrenadeInv();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {

        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        switch (type) {
        	case ENTITY: {
        		renderGrenade(0F, -1.9F, 0F);
        		break;
        	}
            case EQUIPPED: {
            	renderGrenade(-.5F, -0.6F, .6F);
                break;
            }
            case INVENTORY: {
        		renderGrenadeInv(.6F, -5.3F, 0.6F);
            	break;
            }
            default:
                break;
        }

    }

    private void renderGrenade(float x, float y, float z) {

        Tessellator tesselator = Tessellator.instance;
        ForgeHooksClient.bindTexture("/f4113n/Borderlands2Mod/textures/TextureGrenade.png", 0);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        grenadeModel.render(.1F);
        GL11.glPopMatrix(); //end
    }
    
    private void renderGrenadeInv(float x, float y, float z) {

        Tessellator tesselator = Tessellator.instance;
        ForgeHooksClient.bindTexture("/f4113n/Borderlands2Mod/textures/TextureGrenade.png", 0);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        grenadeInv.render(.29F);
        GL11.glPopMatrix(); //end
    }


}