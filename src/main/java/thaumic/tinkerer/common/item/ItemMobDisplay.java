package thaumic.tinkerer.common.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumic.tinkerer.common.core.helper.EnumMobAspect;
import thaumic.tinkerer.common.core.helper.ItemNBTHelper;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.registry.ItemBase;
import thaumic.tinkerer.common.research.IRegisterableResearch;

/**
 * Created by Katrina on 11/03/14.
 */
public class ItemMobDisplay extends ItemBase {

    public static final String TAG_TYPE = "type";

    public ItemMobDisplay() {
        super();
        setHasSubtypes(true); // This allows the item to be marked as a metadata item.
        setMaxDamage(0); // This makes it so your item doesn't have the damage bar at the bottom of its icon, when
                         // "damaged" like tools.
    }

    @Override
    public boolean shouldDisplayInTab() {
        return false;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    public EnumMobAspect getEntityType(ItemStack stack) {
        return EnumMobAspect.getMobAspectForType(ItemNBTHelper.getString(stack, TAG_TYPE, ""));
    }

    public void setEntityType(ItemStack stack, String type) {
        ItemNBTHelper.setString(stack, TAG_TYPE, type);
    }

    @Override
    public void getSubItems(Item par1Item, CreativeTabs par2CreativeTabs, List<ItemStack> list) {
        for (EnumMobAspect aspect : EnumMobAspect.values()) {
            Class<? extends Entity> aspClass = aspect.getEntityClass();
            String name = EntityList.classToStringMapping.get(aspClass);
            ItemStack item = new ItemStack(this);
            this.setEntityType(item, name);
            list.add(item);
        }
    }

    @Override
    public String getItemName() {
        return LibItemNames.MOB_DISPLAY;
    }

    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        String mob = ItemNBTHelper.getString(stack, TAG_TYPE, "");
        if (mob == null || mob.isEmpty()) return super.getItemStackDisplayName(stack);
        EnumMobAspect aspect = EnumMobAspect.getMobAspectForType(mob);
        if (aspect == null) return super.getItemStackDisplayName(stack);
        return aspect.getEntity(Minecraft.getMinecraft().theWorld).getCommandSenderName();
    }
}
