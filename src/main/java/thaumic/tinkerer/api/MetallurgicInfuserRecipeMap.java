package thaumic.tinkerer.api;

import net.minecraft.item.ItemStack;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.common.core.helper.ItemHashStrategy;

public class MetallurgicInfuserRecipeMap {

    /*
     * Hashmap using a hashing strategy suited to comparing items
     */
    private static final Object2ObjectOpenCustomHashMap<ItemStack, MetallurgicInfuserRecipe> recipeMap = new Object2ObjectOpenCustomHashMap<>(
            new ItemHashStrategy());

    private MetallurgicInfuserRecipeMap() {}

    public static MetallurgicInfuserRecipe lookup(ItemStack input) {
        return recipeMap.get(input);
    }

    public static void putRecipe(int centivisCost, Aspect centivisType, ItemStack input, ItemStack output) {
        MetallurgicInfuserRecipe recipe = new MetallurgicInfuserRecipe(centivisCost, centivisType, input, output);
        recipeMap.put(input, recipe);
    }
}
