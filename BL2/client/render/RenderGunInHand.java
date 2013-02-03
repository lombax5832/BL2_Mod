package BL2.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import BL2.client.model.ModelPistol;
import BL2.client.model.ModelPistolInv;
import BL2.client.model.ModelSMG;
import BL2.client.model.ModelSMGInv;
import BL2.client.model.ModelSniper;
import BL2.client.model.ModelSniperInv;


public class RenderGunInHand implements IItemRenderer {

    private ModelPistol pistolModel;
    private ModelSniper sniperModel;
    private ModelSMG smgModel;
    
    private ModelSniperInv sniperInv;
    private ModelPistolInv pistolInv;
    private ModelSMGInv smgInv;
    
    private int pistolId = 1;
    private int smgId = 2;
    private int sniperId = 5;

    public RenderGunInHand() {

    	sniperInv = new ModelSniperInv();
    	pistolInv = new ModelPistolInv();
    	smgInv = new ModelSMGInv();
    	
    	smgModel = new ModelSMG();
        pistolModel = new ModelPistol();
        sniperModel = new ModelSniper();
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
        		if(item.getItemDamage() == pistolId){
	        		renderPistol(0F, -1.4F, 0F);
	        		break;
        		}else if(item.getItemDamage() == smgId){
        			renderSMG(0F, 0F, 0F);
        			break;
        		}else if(item.getItemDamage() == sniperId){
            		renderSniper(-.5F, -1.4F, 0F);
                	break;
            	}
        	}
            case EQUIPPED: {
            	if(item.getItemDamage() == pistolId){
	            	renderPistol(0F, -0.5F, .8F);
	                break;
            	}else if(item.getItemDamage() == smgId){
            		renderSMG(0F, .5F, .4F);
        			break;
        		}else if(item.getItemDamage() == sniperId){
            		renderSniper(.6F, -0.85F, 0.65F);
                	break;
            	}
            }
            case INVENTORY: {
            	if(item.getItemDamage() == pistolId){
            		renderPistolForInventory(.6F, -.9F, 1F);
                	break;
            	}else if(item.getItemDamage() == smgId){
            		renderSMGForInventory(.6F, -0.1F, 0.7F);
        			break;
        		}else if(item.getItemDamage() == sniperId){
            		renderSniperForInventory(.3F, -1.0F, 1F);
                	break;
            	}
            }
            default:
                break;
        }

    }

    private void renderSMG(float x, float y, float z) {

        Tessellator tesselator = Tessellator.instance;
        ForgeHooksClient.bindTexture("/BL2/textures/Guns/SMGModel.png", 0);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        smgModel.render(0.1F);
        GL11.glPopMatrix(); //end
    }
    
    private void renderSMGForInventory(float x, float y, float z) {

        Tessellator tesselator = Tessellator.instance;
        ForgeHooksClient.bindTexture("/BL2/textures/Guns/SMGModel.png", 0);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        smgInv.render(0.094F);
        GL11.glPopMatrix(); //end
    }
    
    private void renderSniper(float x, float y, float z) {

        Tessellator tesselator = Tessellator.instance;
        ForgeHooksClient.bindTexture("/BL2/textures/Guns/SniperModel.png", 0);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        sniperModel.render(0.1F);
        GL11.glPopMatrix(); //end
    }
    
    private void renderSniperForInventory(float x, float y, float z) {

        Tessellator tesselator = Tessellator.instance;
        ForgeHooksClient.bindTexture("/BL2/textures/Guns/SniperModel.png", 0);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        sniperInv.render(.08F);
        GL11.glPopMatrix(); //end
    }
    
    private void renderPistol(float x, float y, float z) {

        Tessellator tesselator = Tessellator.instance;
        ForgeHooksClient.bindTexture("/BL2/textures/Guns/PistolModel.png", 0);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        pistolModel.render(0.1F);
        GL11.glPopMatrix(); //end
    }
    
    private void renderPistolForInventory(float x, float y, float z) {

        Tessellator tesselator = Tessellator.instance;
        ForgeHooksClient.bindTexture("/BL2/textures/Guns/PistolModel.png", 0);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        pistolInv.render(0.1F);
        GL11.glPopMatrix(); //end
    }


}