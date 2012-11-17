package BL2.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.StringTranslate;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

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
        return BL2Core.temp.shiftedIndex;
    }
    
    /**
     * Gets the translated Label.
     */
    public String getTranslatedTabLabel()
    {
        return StringTranslate.getInstance().translateKey(this.getTabLabel());
    }
}
