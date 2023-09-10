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

public class BlockFireOrder extends BlockFireBase {

    public HashMap<Block, Block> fireResults = new HashMap<>();
    private HashMap<Block, Block> oreDictinaryOresCache;

    public BlockFireOrder() {
        super();
        fireResults.put(Blocks.stone, null);
        fireResults.put(Blocks.glass, null);
        fireResults.put(Blocks.sand, null);
        fireResults.put(Blocks.gravel, null);
    }

    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_ORDER;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!ConfigHandler.enableFire) return null;
        return (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_FIRE_ORDO,
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.ORDER, 5),
                3,
                -3,
                2,
                new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                        .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_ORDO))
                        .setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (!ConfigHandler.enableFire) return null;
        return new ThaumicTinkererCrucibleRecipe(
                LibResearch.KEY_FIRE_ORDO,
                new ItemStack(this),
                new ItemStack(ConfigItems.itemShard, 1, 4),
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.ORDER, 5));
    }

    @Override
    public HashMap<Block, Block> getBlockTransformation() {
        return fireResults;
    }

    @Override
    public boolean isTransmutationResult(Block block, IBlockAccess w, int x, int y, int z) {
        return getBlockTransformation(w, x, y, z).containsValue(block);
    }

    @Override
    public HashMap<Block, Block> getBlockTransformation(IBlockAccess w, int x, int y, int z) {
        return getBlockTransformation();
    }

    @Override
    public boolean isTransmutationTarget(Block block, IBlockAccess w, int x, int y, int z) {
        return getBlockTransformation(w, x, y, z).containsKey(block) && getBlockTransformation().get(block) != null;
    }
}
