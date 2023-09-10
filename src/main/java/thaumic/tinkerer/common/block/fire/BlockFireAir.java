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

public class BlockFireAir extends BlockFireBase {

    private static HashMap<Block, Block> blockTransformation = null;

    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_AIR;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!ConfigHandler.enableFire) return null;
        return (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_FIRE_AER,
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.AIR, 5),
                3,
                -7,
                2,
                new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                        .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_AER))
                        .setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (!ConfigHandler.enableFire) return null;
        return new ThaumicTinkererCrucibleRecipe(
                LibResearch.KEY_FIRE_AER,
                new ItemStack(this),
                new ItemStack(ConfigItems.itemShard, 1, 0),
                new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.AIR, 5));
    }

    private static void initBlockTransformation() {
        blockTransformation = new HashMap<>();
        blockTransformation.put(Blocks.log, Blocks.sand);
        blockTransformation.put(Blocks.leaves, Blocks.sandstone);
        blockTransformation.put(Blocks.leaves2, Blocks.sandstone);
        blockTransformation.put(Blocks.log2, Blocks.sand);
        blockTransformation.put(Blocks.ice, Blocks.glass);
        if (ConfigHandler.enableCake) {
            blockTransformation.put(Blocks.water, Blocks.cake);
        }
        blockTransformation.put(Blocks.dirt, Blocks.sand);
        blockTransformation.put(Blocks.grass, Blocks.sand);
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
