package thaumic.tinkerer.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import thaumic.tinkerer.common.core.helper.EnumMobAspect;
import thaumic.tinkerer.common.item.ItemMobDisplay;

public class RenderMobDisplay implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack,
            ItemRendererHelper itemRendererHelper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... objects) {
        ItemMobDisplay item = (ItemMobDisplay) itemStack.getItem();
        EnumMobAspect aspect = item.getEntityType(itemStack);
        Entity entity = null;
        float scale = 0.4f;
        float offset = 0.0f;
        if (aspect != null) {
            entity = EnumMobAspect.getEntityFromCache(
                    aspect,
                    (Minecraft.getMinecraft() != null) ? Minecraft.getMinecraft().theWorld : null);
            scale = aspect.getScale();
            offset = aspect.getVerticalOffset();
        }
        switch (itemRenderType) {
            case ENTITY -> {
                if (entity != null && entity.worldObj != null) {
                    GL11.glPushMatrix();
                    GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(0, (-entity.height / 2) + offset, 0.0F);
                    EntityItem eItem = (EntityItem) objects[1];
                    Render renderer = RenderManager.instance.getEntityRenderObject(entity);
                    entity.setWorld(eItem.worldObj);
                    entity.copyLocationAndAnglesFrom(eItem);
                    if (renderer != null && renderer.getFontRendererFromRenderManager() != null) {
                        renderer.doRender(entity, 0, 0, 0, 0, 0);
                    }
                    GL11.glPopMatrix();
                }
            }
            case INVENTORY -> {
                if (entity != null && entity.worldObj != null) {
                    GL11.glPushMatrix();
                    GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(0, (-entity.height / 2) + offset, 0.0F);
                    Render renderer = RenderManager.instance.getEntityRenderObject(entity);
                    if (renderer != null && renderer.getFontRendererFromRenderManager() != null) {
                        renderer.doRender(entity, 0, 0, 0, 0, 0);
                    }
                    GL11.glPopMatrix();
                }
            }
        }
    }
}
