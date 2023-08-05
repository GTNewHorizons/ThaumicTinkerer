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

public class BlockFireEarth extends BlockFireBase {

    private static HashMap<BlockTuple, BlockTuple> blockTransformation = null;

    public BlockFireEarth() {
        super();
    }

    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_EARTH;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!ConfigHandler.enableFire) return null;
        return (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_FIRE_TERRA,
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.EARTH, 5),
                4,
                -6,
                2,
                new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                        .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_TERRA))
                        .setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (!ConfigHandler.enableFire) return null;
        return new ThaumicTinkererCrucibleRecipe(
                LibResearch.KEY_FIRE_TERRA,
                new ItemStack(this),
                new ItemStack(ConfigItems.itemShard, 1, 3),
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.EARTH, 5));
    }

    private static void initBlockTransformation() {
        blockTransformation = new HashMap<>();
        blockTransformation = new HashMap<>();
        blockTransformation.put(new BlockTuple(Blocks.sand), new BlockTuple(Blocks.dirt));
        blockTransformation.put(new BlockTuple(Blocks.gravel), new BlockTuple(Blocks.clay));
        blockTransformation.put(new BlockTuple(Blocks.nether_brick), new BlockTuple(Blocks.planks));
        blockTransformation.put(new BlockTuple(Blocks.nether_brick_fence), new BlockTuple(Blocks.fence));
        blockTransformation.put(new BlockTuple(Blocks.nether_brick_stairs), new BlockTuple(Blocks.oak_stairs));
        blockTransformation.put(new BlockTuple(Blocks.cactus), new BlockTuple(Blocks.log));
        blockTransformation.put(new BlockTuple(Blocks.snow_layer), new BlockTuple(Blocks.tallgrass));
        blockTransformation.put(new BlockTuple(Blocks.stone), new BlockTuple(Blocks.dirt));
        blockTransformation.put(new BlockTuple(Blocks.mob_spawner), new BlockTuple(Blocks.iron_block));
        blockTransformation.put(new BlockTuple(Blocks.log), new BlockTuple(Blocks.dirt));

        blockTransformation.put(new BlockTuple(Blocks.log2), new BlockTuple(Blocks.dirt));

        blockTransformation.put(new BlockTuple(Blocks.leaves), new BlockTuple(Blocks.dirt));
        blockTransformation.put(new BlockTuple(Blocks.leaves2), new BlockTuple(Blocks.dirt));
        blockTransformation.put(new BlockTuple(Blocks.cobblestone), new BlockTuple(Blocks.dirt));
        blockTransformation.put(new BlockTuple(Blocks.planks), new BlockTuple(Blocks.dirt));
        blockTransformation.put(new BlockTuple(Blocks.glass), new BlockTuple(Blocks.dirt));
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
