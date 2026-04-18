package thaumic.tinkerer.client.nei;

import java.util.ArrayList;
import java.util.List;

import net.glease.tc4tweak.api.infusionrecipe.RecipeIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.aspectrecipeindex.nei.InfusionRecipeHandler;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.helper.EnumMobAspect;
import thaumic.tinkerer.common.item.ItemMobAspect;
import thaumic.tinkerer.common.item.ItemMobDisplay;

public class NEINecromancyHandler extends InfusionRecipeHandler {

    public static final String OVERLAY = "thaumictinkerer.necromancy";

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.spawner.name");
    }

    @Override
    public String getOverlayIdentifier() {
        return OVERLAY;
    }

    @Override
    public int getRecipeHeight(int recipe) {
        return 0;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getOverlayIdentifier())) {
            for (EnumMobAspect value : EnumMobAspect.values()) {
                new NecromancyCachedRecipe(value);
            }
        } else if (outputId.equals("item")) {
            this.loadCraftingRecipes((ItemStack) results[0]);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result.getItem() instanceof ItemMobDisplay display) {
            for (EnumMobAspect value : EnumMobAspect.values()) {
                if (value == display.getEntityType(result)) {
                    new NecromancyCachedRecipe(value);
                    break;
                }
            }
        }
        loadUsageRecipes(result);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (!(ingredient.getItem() instanceof ItemMobAspect)) {
            return;
        }
        Aspect target = ItemMobAspect.getAspect(ingredient);
        if (target == null) return;
        for (EnumMobAspect value : EnumMobAspect.values()) {
            for (Aspect aspect : value.aspects) {
                if (aspect == target) {
                    new NecromancyCachedRecipe(value);
                    break;
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        loadCraftingRecipes(inputId, ingredients);
    }

    @Override
    public void drawBackground(int recipeIndex) {
        GL11.glPushMatrix();
        GuiDraw.changeTexture(THAUM_OVERLAYS);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glScalef(1.75F, 1.75F, 1.0F);
        GL11.glTranslatef(-0.5F, -1F, 0);
        GuiDraw.drawTexturedModalRect(27, 8, 207, 78, 42, 42); // Runic matrix drawing
        GL11.glTranslatef(0.5F, 1F, 0);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public void drawInstability(int recipeIndex) {}

    @Override
    public void drawExtras(int recipeIndex) {
        drawSeeAllRecipesLabel(2);
    }

    protected class NecromancyCachedRecipe extends InfusionCachedRecipe {

        public NecromancyCachedRecipe(EnumMobAspect enumMobAspect) {
            super(0, true);
            setIngredients(enumMobAspect.aspects);
            setResult(enumMobAspect);
            this.aspects = new AspectList();
            addIfValid();
        }

        private void setResult(EnumMobAspect enumMobAspect) {
            ItemStack result = new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemMobDisplay.class));
            ((ItemMobDisplay) result.getItem()).setEntityType(result, enumMobAspect.toString());
            this.result = new PositionedStack(result, OUTPUT_X, 39, false);
        }

        private void setIngredients(Aspect[] aspects) {
            List<RecipeIngredient> outer = new ArrayList<>();
            for (Aspect aspect : aspects) {
                outer.add(RecipeIngredient.item(true, ItemMobAspect.getStackFromAspect(aspect)));
            }
            addSurroundingItems(outer);
        }

        @Override
        protected int itemOffset() {
            return -24;
        }
    }
}
