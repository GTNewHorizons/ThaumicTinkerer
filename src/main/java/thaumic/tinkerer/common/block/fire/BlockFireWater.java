package thaumic.tinkerer.common.block.fire;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.helper.BlockTuple;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererCrucibleRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

public class BlockFireWater extends BlockFireBase {

    private static HashMap<BlockTuple, BlockTuple> blockTransformation = null;

    public BlockFireWater() {
        super();
    }

    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_WATER;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!ConfigHandler.enableFire) return null;
        return (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_FIRE_AQUA,
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.WATER, 5),
                2,
                -2,
                2,
                new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                        .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_AQUA))
                        .setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (!ConfigHandler.enableFire) return null;
        return new ThaumicTinkererCrucibleRecipe(
                LibResearch.KEY_FIRE_AQUA,
                new ItemStack(this),
                new ItemStack(ConfigItems.itemShard, 1, 2),
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.WATER, 5));
    }

    private static void initBlockTransformation() {
        blockTransformation = new HashMap<>();
        blockTransformation.put(new BlockTuple(Blocks.sand), new BlockTuple(Blocks.ice));
        blockTransformation.put(new BlockTuple(Blocks.netherrack), new BlockTuple(Blocks.snow));
        blockTransformation.put(new BlockTuple(Blocks.soul_sand), new BlockTuple(Blocks.ice));
        blockTransformation.put(new BlockTuple(Blocks.glowstone), new BlockTuple(Blocks.ice));
        blockTransformation.put(new BlockTuple(Blocks.lava), new BlockTuple(Blocks.obsidian));
        blockTransformation.put(new BlockTuple(Blocks.flowing_lava), new BlockTuple(Blocks.obsidian));
    }

    @Override
    public HashMap<BlockTuple, BlockTuple> getBlockTransformation() {
        if (blockTransformation == null) {
            initBlockTransformation();
        }

        return blockTransformation;
    }

    @Override
    public HashMap<thaumic.tinkerer.common.core.helper.BlockTuple, thaumic.tinkerer.common.core.helper.BlockTuple> getBlockTransformation(
            World w, int x, int y, int z) {
        return getBlockTransformation();
    }
}
