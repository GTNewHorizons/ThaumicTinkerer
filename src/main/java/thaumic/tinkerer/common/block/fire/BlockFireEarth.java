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

public class BlockFireEarth extends BlockFireBase {

    private static HashMap<Block, Block> blockTransformation = null;

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
        blockTransformation.put(Blocks.sand, Blocks.dirt);
        blockTransformation.put(Blocks.gravel, Blocks.clay);
        blockTransformation.put(Blocks.nether_brick, Blocks.planks);
        blockTransformation.put(Blocks.nether_brick_fence, Blocks.fence);
        blockTransformation.put(Blocks.nether_brick_stairs, Blocks.oak_stairs);
        blockTransformation.put(Blocks.cactus, Blocks.log);
        blockTransformation.put(Blocks.snow_layer, Blocks.tallgrass);
        blockTransformation.put(Blocks.stone, Blocks.dirt);
        blockTransformation.put(Blocks.mob_spawner, Blocks.iron_block);
        blockTransformation.put(Blocks.log, Blocks.dirt);

        blockTransformation.put(Blocks.log2, Blocks.dirt);

        blockTransformation.put(Blocks.leaves, Blocks.dirt);
        blockTransformation.put(Blocks.leaves2, Blocks.dirt);
        blockTransformation.put(Blocks.cobblestone, Blocks.dirt);
        blockTransformation.put(Blocks.planks, Blocks.dirt);
        blockTransformation.put(Blocks.glass, Blocks.dirt);
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
