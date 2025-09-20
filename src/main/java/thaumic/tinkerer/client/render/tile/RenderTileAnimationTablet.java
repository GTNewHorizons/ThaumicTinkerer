/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [9 Sep 2013, 17:12:26 (GMT)]
 */
package thaumic.tinkerer.client.render.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumic.tinkerer.client.core.helper.ClientHelper;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.common.block.tile.tablet.TileAnimationTablet;

public class RenderTileAnimationTablet extends TileEntitySpecialRenderer {

    private static final ResourceLocation overlayCenter = new ResourceLocation(LibResources.MISC_AT_CENTER);
    private static final ResourceLocation overlayLeft = new ResourceLocation(LibResources.MISC_AT_LEFT);
    private static final ResourceLocation overlayRight = new ResourceLocation(LibResources.MISC_AT_RIGHT);
    private static final ResourceLocation overlayIndent = new ResourceLocation(LibResources.MISC_AT_INDENT);

    private static final float[][] TRANSLATIONS = new float[][] { { 0F, 0F, -1F }, { -1F, 0F, 0F }, { 0F, 0F, 0F },
            { -1F, 0F, -1F } };

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float partialTicks) {
        TileAnimationTablet tile = (TileAnimationTablet) tileentity;

        int meta = tile.getBlockMetadata() & 7;
        if (meta < 2) meta = 2; // Just in case

        int rotation = meta == 2 ? 270 : meta == 3 ? 90 : meta == 4 ? 0 : 180;

        GL11.glColor4f(1F, 1F, 1F, 1F);

        GL11.glPushMatrix();
        GL11.glTranslated(d0, d1, d2);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        renderOverlay(tile, overlayCenter, -1, false, 0.65, 0.13F, partialTicks);
        if (tile.leftClick) renderOverlay(tile, overlayLeft, 1, true, 1, 0.13F, partialTicks);
        else renderOverlay(tile, overlayRight, 1, true, 1, 0.131F, partialTicks);
        renderIndents(tile, rotation + 90F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);

        GL11.glRotatef(rotation, 0F, 1F, 0F);
        GL11.glTranslated(0.1, 0.2 + Math.cos(System.currentTimeMillis() / 600D) / 18F, 0.5);
        float[] translations = TRANSLATIONS[meta - 2];
        GL11.glTranslatef(translations[0], translations[1], translations[2]);
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        GL11.glTranslatef(0.5F, 0F, 0.5F);
        float swingProgress = tile.getRenderSwingProgress(partialTicks);
        GL11.glRotatef(swingProgress, 0F, 0F, 1F);
        GL11.glTranslatef(-0.5F, 0F, -0.5F);
        GL11.glTranslatef(-swingProgress / 250F, swingProgress / 1000F, 0F);
        GL11.glRotatef((float) Math.cos(System.currentTimeMillis() / 400F) * 5F, 1F, 0F, 1F);
        renderItem(tile);
        GL11.glPopMatrix();
    }

    private void renderItem(TileAnimationTablet tablet) {
        ItemStack stack = tablet.getStackInSlot(0);
        if (stack != null) {
            EntityItem entityitem = new EntityItem(tablet.getWorldObj(), 0.0D, 0.0D, 0.0D, stack);
            entityitem.worldObj = tablet.getWorldObj();
            int stackSize = entityitem.getEntityItem().stackSize;
            entityitem.getEntityItem().stackSize = 1;
            entityitem.hoverStart = 0.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.5F, 0.55F, 0F);
            if (stack.getItem() instanceof ItemBlock) GL11.glScalef(2.5F, 2.5F, 2.5F);
            else GL11.glScalef(1.5F, 1.5F, 1.5F);
            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;

            GL11.glPopMatrix();
            entityitem.getEntityItem().stackSize = stackSize;
        }
    }

    private void renderOverlay(TileAnimationTablet tablet, ResourceLocation texture, int rotationMod, boolean useBlend,
            double size, float height, float partialTicks) {
        Minecraft mc = ClientHelper.minecraft();
        mc.renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        if (useBlend) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
        GL11.glTranslatef(0.5F, height, 0.5F);
        float deg = (tablet.getTicksExisted(partialTicks) * rotationMod % 360F);
        GL11.glRotatef(deg, 0F, 1F, 0F);
        Tessellator tess = Tessellator.instance;
        double size1 = size / 2;
        double size2 = -size1;
        tess.startDrawingQuads();
        tess.addVertexWithUV(size2, 0, size1, 0, 1);
        tess.addVertexWithUV(size1, 0, size1, 1, 1);
        tess.addVertexWithUV(size1, 0, size2, 1, 0);
        tess.addVertexWithUV(size2, 0, size2, 0, 0);
        tess.draw();
        GL11.glPopMatrix();
    }

    private void renderIndents(TileAnimationTablet tablet, float forceDeg) {
        Minecraft mc = ClientHelper.minecraft();
        mc.renderEngine.bindTexture(overlayIndent);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, (float) 0.13, 0.5F);
        GL11.glRotatef(forceDeg, 0F, 1F, 0F);
        Tessellator tess = Tessellator.instance;
        final double size1 = 0.25;
        final double size2 = -0.25;
        tess.startDrawingQuads();
        tess.addVertexWithUV(size2, 0, size1, 0, 1);
        tess.addVertexWithUV(size1, 0, size1, 1, 1);
        tess.addVertexWithUV(size1, 0, size2, 1, 0);
        tess.addVertexWithUV(size2, 0, size2, 0, 0);
        tess.draw();
        int acceleration = tablet.getWorldAcceleratorBonus();
        if (acceleration != 0) {
            GL11.glRotatef(90, -1, 0, 0);
            GL11.glScalef(0.02f, -0.02f, 0.02f);
            final String s = 'x' + Integer.toString(acceleration);
            final int width = mc.fontRenderer.getStringWidth(s);
            GL11.glTranslatef(-width / 2f, 10f + mc.fontRenderer.FONT_HEIGHT / 2f, 0);
            mc.fontRenderer.drawString(s, 0, 0, 0xFFFFFF);
        }
        GL11.glPopMatrix();
    }
}
