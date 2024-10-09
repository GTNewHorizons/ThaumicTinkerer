package thaumic.tinkerer.api;

import net.minecraft.item.ItemStack;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import thaumic.tinkerer.common.core.helper.ItemHashStrategy;

public class MetallurgicInfuserRecipeMap {

    private static final Object2ObjectOpenCustomHashMap<ItemStack, ItemStack> recipeMap = new Object2ObjectOpenCustomHashMap<>(
            new ItemHashStrategy());

    private MetallurgicInfuserRecipeMap() {}

    public static ItemStack lookup(ItemStack input) {
        return recipeMap.get(input);
    }

    public static void putRecipe(ItemStack input, ItemStack output) {
        recipeMap.put(input, output);
    }
}
