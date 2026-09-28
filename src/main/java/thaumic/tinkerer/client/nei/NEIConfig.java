package thaumic.tinkerer.client.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import thaumic.tinkerer.common.compat.LoadedMods;

public class NEIConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        if (!LoadedMods.aspectRecipeIndexLoaded) return;
        AspectRecipeIndexIntegration.register();

        NEIInfusedSeedRecipeHandler seedHandler = new NEIInfusedSeedRecipeHandler();
        API.registerRecipeHandler(seedHandler);
        API.registerUsageHandler(seedHandler);
    }

    @Override
    public String getName() {
        return "Thaumic Tinkerer NEI";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    // This needs to be an inner class to prevent a NoClassDefFoundError from class loading when ARI isn't loaded.
    private static class AspectRecipeIndexIntegration {

        public static void register() {
            TemplateRecipeHandler handler = new NEINecromancyHandler();
            GuiCraftingRecipe.craftinghandlers.add(handler);
            GuiUsageRecipe.usagehandlers.add(handler);
        }
    }
}
