package f4113n.Borderlands2Mod.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.StringTranslate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import f4113n.Borderlands2Mod.BL2Core;

public final class CreativeTabBL2 extends CreativeTabs
{
    
    public static CreativeTabs tabBL2;

    public CreativeTabBL2(String par2Str)
    {
      super(par2Str);
    }

    

    /**
     * the itemID for the item to be displayed on the tab
     */
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
        return BL2Core.temp.itemID;
    }
    
    /**
     * Gets the translated Label.
     */
    public String getTranslatedTabLabel()
    {
        return StringTranslate.getInstance().translateKey(this.getTabLabel());
    }
}
