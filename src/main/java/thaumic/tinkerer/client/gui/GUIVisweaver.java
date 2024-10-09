package thaumic.tinkerer.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumic.tinkerer.client.core.helper.ClientHelper;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.common.block.tile.TileVisweaver;
import thaumic.tinkerer.common.block.tile.container.ContainerVisweaver;

public class GUIVisweaver extends GuiContainer {

    private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_VISWEAVER);
    public TileVisweaver visweaver;
    public List<String> tooltip = new ArrayList<>();
    int x, y;
    ItemStack lastTickStack;
    ItemStack currentStack;

    public GUIVisweaver(TileVisweaver visweaver, InventoryPlayer inv) {
        super(new ContainerVisweaver(visweaver, inv));
        this.visweaver = visweaver;
        lastTickStack = visweaver.getStackInSlot(0);
        currentStack = visweaver.getStackInSlot(0);
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
    }

    @Override
    public void updateScreen() {
        currentStack = visweaver.getStackInSlot(0);
        lastTickStack = currentStack;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(gui);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        if (!tooltip.isEmpty()) ClientHelper.renderTooltip(par1 - x, par2 - y, tooltip);
        tooltip.clear();
    }
}
