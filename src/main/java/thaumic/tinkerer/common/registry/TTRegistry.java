package thaumic.tinkerer.common.registry;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.research.IRegisterableResearch;

public class TTRegistry {

    private final HashMap<Class, Item[]> itemRegistry = new HashMap<>();

    private final HashMap<Class, Block[]> blockRegistry = new HashMap<>();

    public static Item itemPlacementMirror;
    public static Item itemBlackHoleTalisman;

    /**
     * <warning>THIS NO LONGER USES REFLECTION. IF YOU WANT TO ADD A BLOCK/ITEM, ADD THE CLASS HERE.</warning>
     */
    public void preInit() {
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockAnimationTablet.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockAspectAnalyzer.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockEnchanter.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockForcefield.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockFunnel.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockGaseousLight.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockGaseousShadow.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockGolemConnector.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockInfusedFarmland.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockInfusedGrain.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockMagnet.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockNitorGas.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockPlatform.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockRPlacer.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockRepairer.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockSummon.class);
        loadMetaBlock(thaumic.tinkerer.common.block.BlockTravelSlab.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.BlockTravelStairs.class);
        loadMetaBlock(thaumic.tinkerer.common.block.BlockWardSlab.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.fire.BlockFireAir.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.fire.BlockFireChaos.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.fire.BlockFireEarth.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.fire.BlockFireIgnis.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.fire.BlockFireOrder.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.fire.BlockFireWater.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.kami.BlockBedrockPortal.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.kami.BlockWarpGate.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.mobilizer.BlockMobilizer.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.mobilizer.BlockMobilizerRelay.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.quartz.BlockDarkQuartz.class);
        loadMetaBlock(thaumic.tinkerer.common.block.quartz.BlockDarkQuartzSlab.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.quartz.BlockDarkQuartzStairs.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.transvector.BlockTransvectorDislocator.class);
        loadSimpleBlock(thaumic.tinkerer.common.block.transvector.BlockTransvectorInterface.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemBloodSword.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemBrightNitor.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemCleansingTalisman.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemConnector.class);
        loadMetaItem(thaumic.tinkerer.common.item.ItemGas.class); // Note: this has to load AFTER BlockGaseousLight
        loadSimpleItem(thaumic.tinkerer.common.item.ItemGasRemover.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemInfusedGrain.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemInfusedInkwell.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemInfusedPotion.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemInfusedSeeds.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemMobAspect.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemMobDisplay.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemRevealingHelm.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemShareBook.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemSoulMould.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemSpellCloth.class);
        loadSimpleItem(thaumic.tinkerer.common.item.ItemXPTalisman.class);
        loadSimpleItem(thaumic.tinkerer.common.item.foci.ItemFocusDeflect.class);
        loadSimpleItem(thaumic.tinkerer.common.item.foci.ItemFocusDislocation.class);
        loadSimpleItem(thaumic.tinkerer.common.item.foci.ItemFocusEnderChest.class);
        loadSimpleItem(thaumic.tinkerer.common.item.foci.ItemFocusFlight.class);
        loadSimpleItem(thaumic.tinkerer.common.item.foci.ItemFocusHeal.class);
        loadSimpleItem(thaumic.tinkerer.common.item.foci.ItemFocusSmelt.class);
        loadSimpleItem(thaumic.tinkerer.common.item.foci.ItemFocusTelekinesis.class);
        itemBlackHoleTalisman = loadSimpleItem(thaumic.tinkerer.common.item.kami.ItemBlockTalisman.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.ItemCatAmulet.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.ItemIchorPouch.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.ItemKamiResource.class);
        itemPlacementMirror = loadSimpleItem(thaumic.tinkerer.common.item.kami.ItemPlacementMirror.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.ItemProtoclay.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.ItemSkyPearl.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.armor.ItemGemBoots.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.armor.ItemGemChest.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.armor.ItemGemHelm.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.armor.ItemGemLegs.class);
        loadMetaItem(thaumic.tinkerer.common.item.kami.armor.ItemIchorclothArmor.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.foci.ItemFocusRecall.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.foci.ItemFocusShadowbeam.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.foci.ItemFocusXPDrain.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.tool.ItemIchorAxe.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.tool.ItemIchorAxeAdv.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.tool.ItemIchorPick.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.tool.ItemIchorPickAdv.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.tool.ItemIchorShovel.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.tool.ItemIchorShovelAdv.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.tool.ItemIchorSword.class);
        loadSimpleItem(thaumic.tinkerer.common.item.kami.tool.ItemIchorSwordAdv.class);
        loadSimpleItem(thaumic.tinkerer.common.item.quartz.ItemDarkQuartz.class);
    }

    public void init() {
        for (Item[] itemArrayList : itemRegistry.values()) {
            for (Item item : itemArrayList) {
                registerRecipe((ITTinkererRegisterable) item);

                if (!(item instanceof ItemBlock)) {
                    GameRegistry.registerItem(item, ((ITTinkererItem) item).getItemName());

                    if (((ITTinkererItem) item).shouldDisplayInTab()
                            && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                        ModCreativeTab.INSTANCE.addItem(item);
                    }
                }
            }
        }

        for (Block[] blockArrayList : blockRegistry.values()) {
            for (Block block : blockArrayList) {
                registerRecipe((ITTinkererRegisterable) block);
            }
        }
        ModCreativeTab.INSTANCE.addAllItemsAndBlocks();
    }

    private void registerResearch(ITTinkererRegisterable nextItem) {
        IRegisterableResearch registerableResearch = nextItem.getResearchItem();
        if (registerableResearch != null) {
            registerableResearch.registerResearch();
        }
    }

    private void registerRecipe(ITTinkererRegisterable nextItem) {
        ThaumicTinkererRecipe thaumicTinkererRecipe = nextItem.getRecipeItem();
        if (thaumicTinkererRecipe != null) {
            thaumicTinkererRecipe.registerRecipe();
        }
    }

    private Block loadSimpleBlock(Class<?> clazz) {
        try {
            final Block newBlock = (Block) clazz.newInstance();
            final ITTinkererBlock ittBlock = (ITTinkererBlock) newBlock;
            if (ittBlock.shouldRegister()) {
                newBlock.setBlockName(ittBlock.getBlockName());
                blockRegistry.put(clazz, new Block[] { newBlock });
                registerBlock(newBlock, ittBlock);

                Class<? extends ItemBlock> itemBlock = ittBlock.getItemBlock();
                if (itemBlock != null) {
                    Item newItem = itemBlock.getConstructor(Block.class).newInstance(newBlock);
                    newItem.setUnlocalizedName(((ITTinkererItem) newItem).getItemName());
                    itemRegistry.put(itemBlock, new Item[] { newItem });
                }
                return newBlock;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.log(Level.WARN, "Failed to load Block " + clazz.getSimpleName() + ". This shouldn't happen!");
        }

        return null;
    }

    private Block[] loadMetaBlock(Class<?> clazz) {
        try {
            final Block newBlock = (Block) clazz.newInstance();
            final ITTinkererBlock ittBlock = (ITTinkererBlock) newBlock;
            if (ittBlock.shouldRegister()) {
                newBlock.setBlockName(ittBlock.getBlockName());

                Block[] metaBlocks = ittBlock.getMetaBlocks();

                Block[] blockList = new Block[1 + metaBlocks.length];
                blockRegistry.put(clazz, blockList);
                blockList[0] = newBlock;

                int index = 1;
                registerBlock(newBlock, ittBlock);
                for (Block metaBlock : metaBlocks) {
                    ITTinkererBlock metaIttBlock = (ITTinkererBlock) metaBlock;
                    metaBlock.setBlockName(metaIttBlock.getBlockName());
                    blockList[index++] = metaBlock;
                    registerBlock(metaBlock, metaIttBlock);
                }

                Class<? extends ItemBlock> itemBlock = ittBlock.getItemBlock();
                if (itemBlock != null) {
                    Item newItem = itemBlock.getConstructor(Block.class).newInstance(newBlock);
                    newItem.setUnlocalizedName(((ITTinkererItem) newItem).getItemName());
                    itemRegistry.put(itemBlock, new Item[] { newItem });
                }

                return blockList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.log(Level.WARN, "Failed to load Block " + clazz.getSimpleName() + ". This shouldn't happen!");
        }
        return null;
    }

    private Item loadSimpleItem(Class<?> clazz) {
        try {
            final Item newItem = (Item) clazz.newInstance();
            final ITTinkererItem ittItem = (ITTinkererItem) newItem;
            if (ittItem.shouldRegister()) {
                newItem.setUnlocalizedName(ittItem.getItemName());
                itemRegistry.put(clazz, new Item[] { newItem });

                return newItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.log(Level.WARN, "Failed to load Item " + clazz.getSimpleName() + ". This shouldn't happen!");
        }
        return null;
    }

    private Item[] loadMetaItem(Class<?> clazz) {
        try {
            final Item newItem = (Item) clazz.newInstance();
            final ITTinkererItem ittItem = (ITTinkererItem) newItem;
            if (ittItem.shouldRegister()) {
                newItem.setUnlocalizedName(ittItem.getItemName());

                Item[] metaItems = ittItem.getMetaItems();

                Item[] itemList = new Item[1 + metaItems.length];
                itemList[0] = newItem;

                int index = 1;
                for (Item metaItem : metaItems) {
                    metaItem.setUnlocalizedName(((ITTinkererItem) metaItem).getItemName());
                    itemList[index++] = metaItem;
                }
                itemRegistry.put(clazz, itemList);
                return itemList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.log(Level.WARN, "Failed to load Item " + clazz.getSimpleName() + ". This shouldn't happen!");
        }
        return null;
    }

    private void registerBlock(Block block, ITTinkererBlock ittBlock) {
        String blockName = ittBlock.getBlockName();
        Class<? extends ItemBlock> itemBlock = ittBlock.getItemBlock();
        if (itemBlock != null) {
            GameRegistry.registerBlock(block, itemBlock, blockName);
        } else {
            GameRegistry.registerBlock(block, blockName);
        }
        Class<? extends TileEntity> tileEntity = ittBlock.getTileEntity();
        if (tileEntity != null) {
            GameRegistry.registerTileEntity(tileEntity, LibResources.PREFIX_MOD + blockName);
        }
        if (block instanceof IMultiTileEntityBlock) {
            for (Map.Entry<Class<? extends TileEntity>, String> tile : ((IMultiTileEntityBlock) block)
                    .getAdditionalTileEntities().entrySet()) {
                GameRegistry.registerTileEntity(tile.getKey(), tile.getValue());
            }
        }
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            if (ittBlock.shouldDisplayInTab()) {
                ModCreativeTab.INSTANCE.addBlock(block);
            }
        }
    }

    public Item[] getItemArrayFromClass(Class clazz) {
        return itemRegistry.get(clazz);
    }

    public Item getItemFromClass(Class clazz, int index) {
        return itemRegistry.get(clazz)[index];
    }

    public Item getFirstItemFromClass(Class clazz) {
        final Item[] items = itemRegistry.get(clazz);
        return items != null ? items[0] : null;
    }

    public Item getItemFromClassAndName(Class clazz, String s) {
        final Item[] itemsFromClass = itemRegistry.get(clazz);
        if (itemsFromClass != null) {
            for (Item i : itemsFromClass) {
                if (((ITTinkererItem) i).getItemName().equals(s)) {
                    return i;
                }
            }
        }
        return null;
    }

    public Block getBlockFromClassAndName(Class clazz, String s) {
        final Block[] blocksFromClass = blockRegistry.get(clazz);
        if (blocksFromClass != null) {
            for (Block i : blocksFromClass) {
                if (((ITTinkererBlock) i).getBlockName().equals(s)) {
                    return i;
                }
            }
        }
        return null;
    }

    public Block[] getBlockArrayFromClass(Class clazz) {
        return blockRegistry.get(clazz);
    }

    public Block getBlockFromClass(Class clazz, int index) {
        return blockRegistry.get(clazz)[index];
    }

    public Block getFirstBlockFromClass(Class clazz) {
        final Block[] blocks = blockRegistry.get(clazz);
        return blocks != null ? blocks[0] : null;
    }

    public void postInit() {
        for (Item[] itemArrayList : itemRegistry.values()) {
            for (Item item : itemArrayList) {
                registerResearch((ITTinkererRegisterable) item);
            }
        }
        for (Block[] blockArrayList : blockRegistry.values()) {
            for (Block block : blockArrayList) {
                registerResearch((ITTinkererRegisterable) block);
            }
        }
    }
}
