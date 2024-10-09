package thaumic.tinkerer.api;

import net.minecraft.item.ItemStack;

import thaumcraft.api.aspects.Aspect;

public class MetallurgicInfuserRecipe {

    private final int centivisCost;
    private final Aspect centivisType;
    private final ItemStack input;
    private final ItemStack output;

    MetallurgicInfuserRecipe(int centivisCost, Aspect centivisType, ItemStack input, ItemStack output) {
        this.centivisCost = centivisCost;
        this.centivisType = centivisType;
        this.input = input;
        this.output = output;
    }

    public int getCentivisCost() {
        return centivisCost;
    }

    public Aspect getCentivisType() {
        return centivisType;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
}
