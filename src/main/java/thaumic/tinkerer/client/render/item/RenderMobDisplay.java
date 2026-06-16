package thaumic.tinkerer.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
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
        if (!(itemStack.getItem() instanceof ItemMobDisplay item)) return;
        EnumMobAspect aspect = item.getEntityType(itemStack);
        if (aspect == null) return;
        Entity entity = EnumMobAspect.getEntityFromCache(aspect, Minecraft.getMinecraft().theWorld);
        float scale = aspect.getScale();
        float offset = aspect.getVerticalOffset();
        GL11.glPushMatrix();
        GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(0, (-entity.height / 2) + offset, 0.0F);
        Render renderer = RenderManager.instance.getEntityRenderObject(entity);
        if (renderer != null && renderer.getFontRendererFromRenderManager() != null) {
            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
            renderer.doRender(entity, 0, 0, 0, 0, 0);
            GL11.glPopAttrib();
        }
        GL11.glPopMatrix();
    }
}
