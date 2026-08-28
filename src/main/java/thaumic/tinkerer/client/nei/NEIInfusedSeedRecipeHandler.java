package thaumic.tinkerer.client.nei;

import static thaumic.tinkerer.client.lib.LibResources.NEI_RECIPE_ARROW;

import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.common.core.helper.AspectCropLootManager;
import thaumic.tinkerer.common.core.helper.AspectCropLootManager.LootEntry;
import thaumic.tinkerer.common.item.ItemInfusedSeeds;

public class NEIInfusedSeedRecipeHandler extends TemplateRecipeHandler {

    private static final int SLOT_NUM_X = 9;
    private static final DecimalFormat CHANCE_FORMAT = new DecimalFormat("#.##");

    public static class DropInfo {

        public final ItemStack stack;
        public final float chance;

        public DropInfo(ItemStack stack, float chance) {
            this.stack = stack;
            this.chance = chance;
        }
    }

    public static class SeedDropRecipeWrapper {

        public final ItemStack seed;
        public final List<DropInfo> drops;

        public SeedDropRecipeWrapper(ItemStack seed, List<DropInfo> drops) {
            this.seed = seed;
            this.drops = drops;
        }
    }

    public class CachedSeedRecipe extends CachedRecipe {

        private final List<PositionedStack> input = new ArrayList<>();
        private final List<PositionedStack> outputs = new ArrayList<>();
        public final SeedDropRecipeWrapper wrapper;
        public int rows = 0;

        public CachedSeedRecipe(SeedDropRecipeWrapper recipe) {
            this.wrapper = recipe;
            this.input.add(new PositionedStack(recipe.seed, 74, 4));

            int row = 0;
            int col = 0;

            for (DropInfo drop : recipe.drops) {
                int xPos = 3 + 18 * col;
                int yPos = 36 + 18 * row;

                this.outputs.add(new PositionedStack(drop.stack, xPos, yPos));

                col++;
                if (col >= SLOT_NUM_X) {
                    col = 0;
                    row++;
                }
            }

            this.rows = col == 0 ? row - 1 : row;
            if (this.rows < 0) this.rows = 0;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return input;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return outputs;
        }
    }

    private List<SeedDropRecipeWrapper> getSeedRecipes() {
        List<SeedDropRecipeWrapper> recipes = new ArrayList<>();
        Map<Aspect, List<LootEntry>> allLoot = AspectCropLootManager.getAllLoot();

        for (Map.Entry<Aspect, List<LootEntry>> entry : allLoot.entrySet()) {
            Aspect aspect = entry.getKey();
            List<LootEntry> lootList = entry.getValue();

            if (lootList == null || lootList.isEmpty()) continue;

            int totalWeight = 0;
            for (LootEntry loot : lootList) {
                totalWeight += loot.weight;
            }

            List<DropInfo> drops = new ArrayList<>();
            for (LootEntry loot : lootList) {
                if (loot.stack != null) {
                    float chance = ((float) loot.weight / totalWeight) * 100f;
                    drops.add(new DropInfo(loot.stack.copy(), chance));
                }
            }

            ItemStack seedStack = ItemInfusedSeeds.getStackFromAspect(aspect);

            recipes.add(new SeedDropRecipeWrapper(seedStack, drops));
        }

        return recipes;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(73, 22, 18, 12), getOverlayIdentifier()));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getOverlayIdentifier()) && getClass() == NEIInfusedSeedRecipeHandler.class) {
            for (SeedDropRecipeWrapper recipe : getSeedRecipes()) {
                arecipes.add(new CachedSeedRecipe(recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (SeedDropRecipeWrapper recipe : getSeedRecipes()) {
            for (DropInfo drop : recipe.drops) {
                if (NEIServerUtils.areStacksSameTypeCrafting(result, drop.stack)) {
                    arecipes.add(new CachedSeedRecipe(recipe));
                    break;
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient != null && ingredient.getItem() instanceof ItemInfusedSeeds) {
            Aspect aspectIn = ItemInfusedSeeds.getAspect(ingredient);

            if (aspectIn != null) {
                for (SeedDropRecipeWrapper recipe : getSeedRecipes()) {
                    Aspect aspectRecipe = ItemInfusedSeeds.getAspect(recipe.seed);
                    if (aspectIn == aspectRecipe || aspectIn.getTag().equals(aspectRecipe.getTag())) {
                        arecipes.add(new CachedSeedRecipe(recipe));
                    }
                }
            }
        }
    }

    @Override
    public int getRecipeHeight(int recipeIndex) {
        return 36 + 18 * (((CachedSeedRecipe) this.arecipes.get(recipeIndex)).rows + 1) + 6;
    }

    @Override
    public void drawBackground(int recipeIndex) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GuiDraw.changeTexture(getGuiTexture());

        GuiDraw.drawTexturedModalRect(74, 3, 7, 83, 18, 18);

        CachedSeedRecipe recipe = (CachedSeedRecipe) this.arecipes.get(recipeIndex);
        int totalDrops = recipe.outputs.size();

        for (int i = 0; i < totalDrops; i++) {
            int col = i % SLOT_NUM_X;
            int row = i / SLOT_NUM_X;
            int xPos = 2 + 18 * col;
            int yPos = 35 + 18 * row;

            GuiDraw.drawTexturedModalRect(xPos, yPos, 7, 83, 18, 18);
        }

        GuiDraw.changeTexture(NEI_RECIPE_ARROW);

        GuiDraw.drawTexturedModalRect(74, 20, 0, 0, 18, 14);
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe<?> gui, ItemStack stack, List<String> currenttip, int recipeIndex) {
        CachedSeedRecipe recipe = (CachedSeedRecipe) this.arecipes.get(recipeIndex);

        for (DropInfo drop : recipe.wrapper.drops) {
            if (NEIServerUtils.areStacksSameTypeCrafting(stack, drop.stack)) {

                String chancePrefix = StatCollector.translateToLocal("tt.nei.chance");

                String chanceStr = CHANCE_FORMAT.format(drop.chance) + "%";
                currenttip.add(EnumChatFormatting.GRAY + chancePrefix + ": " + EnumChatFormatting.YELLOW + chanceStr);

                break;
            }
        }

        return currenttip;
    }

    @Override
    public String getGuiTexture() {
        return "minecraft:textures/gui/container/inventory.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return "tt_infused_seeds_drops";
    }

    @Override
    public String getHandlerId() {
        return "tt_infused_seeds_drops";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("tt.nei.category.infusedseeds");
    }
}
