package thaumic.tinkerer.common.block.fire;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererCrucibleRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

public class BlockFireChaos extends BlockFireBase {

    private static HashMap<Block, Block> blockTransformation = null;

    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_CHAOS;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!ConfigHandler.enableFire) return null;
        return (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_FIRE_PERDITIO,
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 5),
                2,
                -8,
                3,
                new ItemStack(this))
                        .setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                        .setPages(
                                new ResearchPage("0"),
                                ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_PERDITIO))
                        .setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (!ConfigHandler.enableFire) return null;
        return new ThaumicTinkererCrucibleRecipe(
                LibResearch.KEY_FIRE_PERDITIO,
                new ItemStack(this),
                new ItemStack(ConfigItems.itemShard, 1, 5),
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.ENTROPY, 5));
    }

    @Override
    public int tickRate(World p_149738_1_) {
        return 1;
    }

    private static void initBlockTransformation() {
        blockTransformation = new HashMap<>();
        blockTransformation.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireAir.class), Blocks.fire);
        blockTransformation.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireWater.class), Blocks.fire);
        blockTransformation.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireEarth.class), Blocks.fire);
        blockTransformation.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireIgnis.class), Blocks.fire);
        blockTransformation.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireOrder.class), Blocks.fire);
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
