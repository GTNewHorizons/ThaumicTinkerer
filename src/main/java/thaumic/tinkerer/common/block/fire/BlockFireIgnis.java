package thaumic.tinkerer.common.block.fire;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererCrucibleRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

public class BlockFireIgnis extends BlockFireBase {

    private static HashMap<Block, Block> blockTransformation = null;

    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_FIRE;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!ConfigHandler.enableFire) return null;
        return (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_FIRE_IGNIS,
                new AspectList().add(Aspect.FIRE, 10),
                4,
                -4,
                2,
                new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                        .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_IGNIS))
                        .setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (!ConfigHandler.enableFire) return null;
        return new ThaumicTinkererCrucibleRecipe(
                LibResearch.KEY_FIRE_IGNIS,
                new ItemStack(this),
                new ItemStack(ConfigItems.itemShard, 1, 1),
                new AspectList().add(Aspect.FIRE, 10).add(Aspect.AIR, 5));
    }

    private static void initBlockTransformation() {
        blockTransformation = new HashMap<>();
        blockTransformation.put(Blocks.grass, Blocks.netherrack);
        blockTransformation.put(Blocks.dirt, Blocks.netherrack);
        blockTransformation.put(Blocks.sand, Blocks.soul_sand);
        blockTransformation.put(Blocks.gravel, Blocks.soul_sand);
        blockTransformation.put(Blocks.clay, Blocks.glowstone);
        blockTransformation.put(Blocks.coal_ore, Blocks.quartz_ore);
        blockTransformation.put(Blocks.iron_ore, Blocks.quartz_ore);
        blockTransformation.put(Blocks.diamond_ore, Blocks.quartz_ore);
        blockTransformation.put(Blocks.emerald_ore, Blocks.quartz_ore);
        blockTransformation.put(Blocks.gold_block, Blocks.quartz_ore);
        blockTransformation.put(Blocks.lapis_ore, Blocks.quartz_ore);
        blockTransformation.put(Blocks.redstone_ore, Blocks.quartz_ore);
        blockTransformation.put(Blocks.lit_redstone_ore, Blocks.quartz_ore);
        blockTransformation.put(Blocks.water, Blocks.lava);
        blockTransformation.put(Blocks.wheat, Blocks.nether_wart);
        blockTransformation.put(Blocks.potatoes, Blocks.nether_wart);
        blockTransformation.put(Blocks.carrots, Blocks.nether_wart);
        blockTransformation.put(Blocks.red_flower, Blocks.brown_mushroom);
        blockTransformation.put(Blocks.yellow_flower, Blocks.yellow_flower);
    }

    @Override
    public HashMap<Block, Block> getBlockTransformation() {
        if (blockTransformation == null) {
            initBlockTransformation();
        }

        return blockTransformation;
    }

    @Override
    public HashMap<Block, Block> getBlockTransformation(IBlockAccess w, int x, int y, int z) {
        return getBlockTransformation();
    }
}
