package thaumic.tinkerer.client.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.gtnewhorizons.dashboard.api.HudBounds;
import com.gtnewhorizons.dashboard.api.HudElement;
import com.gtnewhorizons.dashboard.api.HudRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.config.Config;
import thaumic.tinkerer.client.core.handler.kami.SoulHeartClientHandler;
import thaumic.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

/**
 * Lets players move, resize and hide the Dislocation focus box, the Kami tool mode text and the soul hearts in
 * Dashboard's HUD editor. The only class using Dashboard, so only call it while Dashboard is loaded.
 */
@SideOnly(Side.CLIENT)
public final class DashboardHud {

    private static final int ITEM_SIZE = 18;
    private static final int TEXT_PADDING = 4;
    private static final int TOOL_MODE_OFFSET = 72;
    private static final int SOUL_HEARTS_WIDTH = 81;
    private static final int SOUL_HEARTS_HEIGHT = 9;
    private static final int SAMPLE_SOUL_HP = 20;
    private static final int BOX_COLOR = 0x66000000;
    private static final int TEXT_COLOR = 0xFFAA00;

    private static final HudElement DISLOCATION_FOCUS = new HudElement(
            "thaumictinkerer:dislocation_focus",
            DashboardHud::getDislocationBounds);

    private static final HudElement TOOL_MODE = new HudElement(
            "thaumictinkerer:tool_mode",
            DashboardHud::getToolModeBounds);

    private static final HudElement SOUL_HEARTS = new HudElement(
            "thaumictinkerer:soul_hearts",
            DashboardHud::getSoulHeartsBounds);

    private static final RenderItem RENDER_ITEM = new RenderItem();

    private DashboardHud() {}

    /** The tool mode text and soul hearts only exist with Kami turned on. */
    public static void register(boolean kami) {
        DISLOCATION_FOCUS.setPreview(
                (screenWidth, screenHeight) -> HudRegistry
                        .render(DISLOCATION_FOCUS, () -> drawDislocationPreview(screenWidth, screenHeight)));
        HudRegistry.register(DISLOCATION_FOCUS);
        if (!kami) {
            return;
        }
        TOOL_MODE.setPreview(
                (screenWidth, screenHeight) -> HudRegistry
                        .render(TOOL_MODE, () -> drawToolModePreview(screenWidth, screenHeight)));
        SOUL_HEARTS.setPreview(
                (screenWidth, screenHeight) -> HudRegistry.render(SOUL_HEARTS, DashboardHud::drawSoulHeartsPreview));
        HudRegistry.register(TOOL_MODE);
        HudRegistry.register(SOUL_HEARTS);
    }

    /** Next to Thaumcraft's wand dial: the block's icon, with the text in the small font beside it. */
    private static HudBounds getDislocationBounds(int screenWidth, int screenHeight) {
        int y = Config.dialBottom ? screenHeight - 70 : 50;
        int textWidth = getSmallTextWidth(StatCollector.translateToLocal("ttmisc.focusDislocation.tooltip"));
        return new HudBounds(TEXT_PADDING - 1, y - 1, ITEM_SIZE + 1 + textWidth + TEXT_PADDING, ITEM_SIZE + 1);
    }

    /** Centered above the hotbar, a little above the held item's name. */
    private static HudBounds getToolModeBounds(int screenWidth, int screenHeight) {
        int width = Minecraft.getMinecraft().fontRenderer.getStringWidth(getToolModeText());
        return new HudBounds((screenWidth - width) / 2, screenHeight - TOOL_MODE_OFFSET, width, 9);
    }

    /** Above the food bar, at soulHeart.height, higher while the air bubbles show. */
    private static HudBounds getSoulHeartsBounds(int screenWidth, int screenHeight) {
        int y = screenHeight - ConfigHandler.soulHeartHeight;
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player != null && player.getAir() < 300) {
            y -= 10;
        }
        return new HudBounds(screenWidth / 2 + 10, y, SOUL_HEARTS_WIDTH, SOUL_HEARTS_HEIGHT);
    }

    private static String getToolModeText() {
        String text = ToolModeHUDHandler.getTooltip();
        return text == null || text.isEmpty() ? StatCollector.translateToLocal("ttmisc.mode.pick.1") : text;
    }

    private static int getSmallTextWidth(String text) {
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);
        int width = font.getStringWidth(text);
        font.setUnicodeFlag(unicode);
        return width;
    }

    /** Like the real one: a chest in the box, the text in the small font beside it. */
    private static void drawDislocationPreview(int screenWidth, int screenHeight) {
        HudBounds bounds = getDislocationBounds(screenWidth, screenHeight);
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer font = mc.fontRenderer;
        String text = StatCollector.translateToLocal("ttmisc.focusDislocation.tooltip");
        int x = bounds.x + 1;
        int y = bounds.y + 1;
        Gui.drawRect(bounds.x, bounds.y, x + ITEM_SIZE, y + ITEM_SIZE, BOX_COLOR);
        Gui.drawRect(x + ITEM_SIZE, y, bounds.getRight(), y + 9, BOX_COLOR);
        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);
        font.drawStringWithShadow(text, x + ITEM_SIZE + 2, y, TEXT_COLOR);
        font.setUnicodeFlag(unicode);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        RENDER_ITEM.renderItemIntoGUI(font, mc.renderEngine, new ItemStack(Blocks.chest), x, y);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

    private static void drawToolModePreview(int screenWidth, int screenHeight) {
        HudBounds bounds = getToolModeBounds(screenWidth, screenHeight);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(getToolModeText(), bounds.x, bounds.y, TEXT_COLOR);
    }

    /** The real drawing, with sample soul hearts for the moment. */
    private static void drawSoulHeartsPreview() {
        int realHp = SoulHeartClientHandler.clientPlayerHP;
        SoulHeartClientHandler.clientPlayerHP = SAMPLE_SOUL_HP;
        try {
            SoulHeartClientHandler.renderHUD(HudRegistry.getResolution(), Minecraft.getMinecraft().thePlayer, null);
        } finally {
            SoulHeartClientHandler.clientPlayerHP = realHp;
        }
    }
}
