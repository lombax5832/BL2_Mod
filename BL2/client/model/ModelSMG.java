// Date: 2/3/2013 2:07:31 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package BL2.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSMG extends ModelBase
{
  //fields
    ModelRenderer base;
    ModelRenderer gas_chamber;
    ModelRenderer barrel;
    ModelRenderer magazine_catch;
    ModelRenderer magazine;
    ModelRenderer front_cube;
    ModelRenderer shaft;
    ModelRenderer butt;
    ModelRenderer grip_topp;
    ModelRenderer grip1;
    ModelRenderer side_piston_rght;
    ModelRenderer Side_left_piston;
    ModelRenderer pin2;
    ModelRenderer pin1;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
  
  public ModelSMG()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      base = new ModelRenderer(this, 32, 0);
      base.addBox(-1.5F, 0F, -7F, 3, 3, 14);
      base.setRotationPoint(0F, 12F, -1F);
      base.setTextureSize(128, 64);
      base.mirror = true;
      setRotation(base, 0F, 0F, 0F);
      gas_chamber = new ModelRenderer(this, 0, 21);
      gas_chamber.addBox(-0.5F, -0.7F, -8F, 1, 2, 15);
      gas_chamber.setRotationPoint(0F, 12F, -1F);
      gas_chamber.setTextureSize(128, 64);
      gas_chamber.mirror = true;
      setRotation(gas_chamber, 0F, 0F, 0F);
      barrel = new ModelRenderer(this, 68, 0);
      barrel.addBox(-0.5F, 2F, -10F, 1, 1, 10);
      barrel.setRotationPoint(0F, 12F, -1F);
      barrel.setTextureSize(128, 64);
      barrel.mirror = true;
      setRotation(barrel, 0F, 0F, 0F);
      magazine_catch = new ModelRenderer(this, 0, 43);
      magazine_catch.addBox(-0.5F, 2F, -3F, 1, 3, 4);
      magazine_catch.setRotationPoint(0F, 12F, -1F);
      magazine_catch.setTextureSize(128, 64);
      magazine_catch.mirror = true;
      setRotation(magazine_catch, 0F, 0F, 0F);
      magazine = new ModelRenderer(this, 12, 42);
      magazine.addBox(-0.5F, 3F, -2F, 1, 9, 2);
      magazine.setRotationPoint(0F, 12F, -1F);
      magazine.setTextureSize(128, 64);
      magazine.mirror = true;
      setRotation(magazine, 0F, 0F, 0F);
      front_cube = new ModelRenderer(this, 92, 0);
      front_cube.addBox(-1F, 1.5F, -8.4F, 2, 2, 2);
      front_cube.setRotationPoint(0F, 12F, -1F);
      front_cube.setTextureSize(128, 64);
      front_cube.mirror = true;
      setRotation(front_cube, 0F, 0F, 0F);
      shaft = new ModelRenderer(this, 0, 55);
      shaft.addBox(-0.5F, 2.5F, -6F, 1, 1, 5);
      shaft.setRotationPoint(0F, 12F, -1F);
      shaft.setTextureSize(128, 64);
      shaft.mirror = true;
      setRotation(shaft, 0F, 0F, 0F);
      butt = new ModelRenderer(this, 33, 25);
      butt.addBox(-1.5F, 3F, 3F, 3, 4, 4);
      butt.setRotationPoint(0F, 12F, -1F);
      butt.setTextureSize(128, 64);
      butt.mirror = true;
      setRotation(butt, 0.5235988F, 0F, 0F);
      grip_topp = new ModelRenderer(this, 49, 20);
      grip_topp.addBox(-0.5F, 3F, 3F, 1, 2, 4);
      grip_topp.setRotationPoint(0F, 12F, -1F);
      grip_topp.setTextureSize(128, 64);
      grip_topp.mirror = true;
      setRotation(grip_topp, 0F, 0F, 0F);
      grip1 = new ModelRenderer(this, 66, 21);
      grip1.addBox(-0.5F, 4F, 4.3F, 1, 7, 2);
      grip1.setRotationPoint(0F, 12F, -1F);
      grip1.setTextureSize(128, 64);
      grip1.mirror = true;
      setRotation(grip1, 0.2617994F, 0F, 0F);
      side_piston_rght = new ModelRenderer(this, 39, 35);
      side_piston_rght.addBox(1F, 0.3F, -6F, 1, 2, 13);
      side_piston_rght.setRotationPoint(0F, 12F, -1F);
      side_piston_rght.setTextureSize(128, 64);
      side_piston_rght.mirror = true;
      setRotation(side_piston_rght, 0F, 0F, 0F);
      Side_left_piston = new ModelRenderer(this, 0, -1);
      Side_left_piston.addBox(-2F, 0.3F, -6F, 1, 2, 13);
      Side_left_piston.setRotationPoint(0F, 12F, -1F);
      Side_left_piston.setTextureSize(128, 64);
      Side_left_piston.mirror = true;
      setRotation(Side_left_piston, 0F, 0F, 0F);
      pin2 = new ModelRenderer(this, 0, 16);
      pin2.addBox(2F, 0F, -5F, 1, 1, 1);
      pin2.setRotationPoint(0F, 12F, -1F);
      pin2.setTextureSize(128, 64);
      pin2.mirror = true;
      setRotation(pin2, 0F, 0F, 0F);
      pin1 = new ModelRenderer(this, 12, 16);
      pin1.addBox(1F, 0F, -5F, 1, 1, 1);
      pin1.setRotationPoint(0F, 12F, -1F);
      pin1.setTextureSize(128, 64);
      pin1.mirror = true;
      setRotation(pin1, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 20, 40);
      Shape1.addBox(-0.5F, 4F, 0F, 1, 1, 5);
      Shape1.setRotationPoint(0F, 12F, -1F);
      Shape1.setTextureSize(128, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 7, 15);
      Shape2.addBox(-0.5F, -1.5F, -6.5F, 1, 1, 1);
      Shape2.setRotationPoint(0F, 12F, -1F);
      Shape2.setTextureSize(128, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 15, 55);
      Shape3.addBox(0.5F, -1.5F, 5F, 1, 2, 3);
      Shape3.setRotationPoint(0F, 12F, -1F);
      Shape3.setTextureSize(128, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 26, 51);
      Shape4.addBox(-1.5F, -1.5F, 5F, 1, 2, 3);
      Shape4.setRotationPoint(0F, 12F, -1F);
      Shape4.setTextureSize(128, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    base.render(f5);
    gas_chamber.render(f5);
    barrel.render(f5);
    magazine_catch.render(f5);
    magazine.render(f5);
    front_cube.render(f5);
    shaft.render(f5);
    butt.render(f5);
    grip_topp.render(f5);
    grip1.render(f5);
    side_piston_rght.render(f5);
    Side_left_piston.render(f5);
    pin2.render(f5);
    pin1.render(f5);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
	  model.rotateAngleX = x;
	  model.rotateAngleY = 0.7F;
	  model.rotateAngleZ = 40.8F;
  }
  
  public void render(float scale) {

	  	base.render(scale);
	    gas_chamber.render(scale);
	    barrel.render(scale);
	    magazine_catch.render(scale);
	    magazine.render(scale);
	    front_cube.render(scale);
	    shaft.render(scale);
	    butt.render(scale);
	    grip_topp.render(scale);
	    grip1.render(scale);
	    side_piston_rght.render(scale);
	    Side_left_piston.render(scale);
	    pin2.render(scale);
	    pin1.render(scale);
	    Shape1.render(scale);
	    Shape2.render(scale);
	    Shape3.render(scale);
	    Shape4.render(scale);
  }

}
