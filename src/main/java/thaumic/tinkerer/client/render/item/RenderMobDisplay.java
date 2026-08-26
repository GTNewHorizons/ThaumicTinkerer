package thaumic.tinkerer.client.render.item;

import java.util.EnumMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.event.world.WorldEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import thaumic.tinkerer.common.core.helper.EnumMobAspect;
import thaumic.tinkerer.common.item.ItemMobDisplay;

public class RenderMobDisplay implements IItemRenderer {

    private static final int ATTRIB_MASK = GL11.GL_ENABLE_BIT | GL11.GL_CURRENT_BIT
            | GL11.GL_DEPTH_BUFFER_BIT
            | GL11.GL_COLOR_BUFFER_BIT
            | GL11.GL_LIGHTING_BIT
            | GL11.GL_TEXTURE_BIT;

    private final EnumMap<EnumMobAspect, EntityLiving> cache = new EnumMap<>(EnumMobAspect.class);
    private World cachedWorld;

    private void setCachedWorld(World world) {
        cache.clear();
        cachedWorld = world;
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (event.world == cachedWorld) setCachedWorld(null);
    }

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

        final World world = Minecraft.getMinecraft().theWorld;
        if (world != cachedWorld) setCachedWorld(world);
        if (world == null) return;

        EntityLiving entity = cache.get(aspect);
        if (entity == null || entity.worldObj != world) {
            entity = aspect.createEntity(world);
            cache.put(aspect, entity);
        }

        final Render renderer = RenderManager.instance.getEntityRenderObject(entity);
        if (renderer == null || renderer.getFontRendererFromRenderManager() == null) return;

        float scale = aspect.getScale();
        GL11.glPushMatrix();
        GL11.glPushAttrib(ATTRIB_MASK);
        try {
            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, (-entity.height / 2) + aspect.getVerticalOffset(), 0.0F);
            renderer.doRender(entity, 0, 0, 0, 0, 0);
        } finally {
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    }
}
