package thaumic.tinkerer.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import thaumic.tinkerer.client.core.helper.ClientHelper;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.client.model.ModelVisweaver;
import thaumic.tinkerer.common.block.tile.TileVisweaver;

public class RenderTileVisweaver extends TileEntitySpecialRenderer {

    private static final ResourceLocation modelTex = new ResourceLocation(LibResources.MODEL_VISWEAVER);
    ModelVisweaver model = new ModelVisweaver();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {

        TileVisweaver visweaver = (TileVisweaver) tileentity;

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslatef((float) x, (float) y, (float) z);

        ClientHelper.minecraft().renderEngine.bindTexture(modelTex);

        GL11.glTranslatef(0.5F, 1.5F, 0.5F);
        GL11.glScalef(1F, -1F, -1F);

        model.render(visweaver.isWorking(), visweaver.getTickCounter(), visweaver.getCvType());

        GL11.glRotatef(90F, 1F, 0F, 0F);
        GL11.glTranslatef(0F, 0F, -0.6F);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        GL11.glPopMatrix();
    }
}
