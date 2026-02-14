package thaumic.tinkerer.common.registry;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static Block dynamismTablet;
    public static Item itemPlacementMirror;
    public static Item itemBlackHoleTalisman;

    /**
     * <strong>THIS NO LONGER USES REFLECTION. IF YOU WANT TO ADD A BLOCK/ITEM, ADD THE CLASS HERE.</strong>
     */
    public void preInit() {
        dynamismTablet = loadSimpleBlock(new thaumic.tinkerer.common.block.BlockAnimationTablet());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockAspectAnalyzer());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockEnchanter());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockForcefield());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockFunnel());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockGaseousLight());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockGaseousShadow());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockGolemConnector());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockInfusedFarmland());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockInfusedGrain());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockMagnet());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockNitorGas());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockPlatform());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockRepairer());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockRPlacer());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockSummon());
        loadMetaBlock(new thaumic.tinkerer.common.block.BlockTravelSlab());
        loadSimpleBlock(new thaumic.tinkerer.common.block.BlockTravelStairs());
        loadMetaBlock(new thaumic.tinkerer.common.block.BlockWardSlab());
        loadSimpleBlock(new thaumic.tinkerer.common.block.fire.BlockFireAir());
        loadSimpleBlock(new thaumic.tinkerer.common.block.fire.BlockFireChaos());
        loadSimpleBlock(new thaumic.tinkerer.common.block.fire.BlockFireEarth());
        loadSimpleBlock(new thaumic.tinkerer.common.block.fire.BlockFireIgnis());
        loadSimpleBlock(new thaumic.tinkerer.common.block.fire.BlockFireOrder());
        loadSimpleBlock(new thaumic.tinkerer.common.block.fire.BlockFireWater());
        loadSimpleBlock(new thaumic.tinkerer.common.block.kami.BlockBedrockPortal());
        loadSimpleBlock(new thaumic.tinkerer.common.block.kami.BlockWarpGate());
        loadSimpleBlock(new thaumic.tinkerer.common.block.mobilizer.BlockMobilizer());
        loadSimpleBlock(new thaumic.tinkerer.common.block.mobilizer.BlockMobilizerRelay());
        loadSimpleBlock(new thaumic.tinkerer.common.block.quartz.BlockDarkQuartz());
        loadMetaBlock(new thaumic.tinkerer.common.block.quartz.BlockDarkQuartzSlab());
        loadSimpleBlock(new thaumic.tinkerer.common.block.quartz.BlockDarkQuartzStairs());
        loadSimpleBlock(new thaumic.tinkerer.common.block.transvector.BlockTransvectorDislocator());
        loadSimpleBlock(new thaumic.tinkerer.common.block.transvector.BlockTransvectorInterface());
        loadSimpleItem(new thaumic.tinkerer.common.item.foci.ItemFocusDeflect());
        loadSimpleItem(new thaumic.tinkerer.common.item.foci.ItemFocusDislocation());
        loadSimpleItem(new thaumic.tinkerer.common.item.foci.ItemFocusEnderChest());
        loadSimpleItem(new thaumic.tinkerer.common.item.foci.ItemFocusFlight());
        loadSimpleItem(new thaumic.tinkerer.common.item.foci.ItemFocusHeal());
        loadSimpleItem(new thaumic.tinkerer.common.item.foci.ItemFocusSmelt());
        loadSimpleItem(new thaumic.tinkerer.common.item.foci.ItemFocusTelekinesis());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemBloodSword());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemBrightNitor());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemCleansingTalisman());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemConnector());
        loadMetaItem(new thaumic.tinkerer.common.item.ItemGas());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemGasRemover());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemInfusedGrain());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemInfusedInkwell());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemInfusedPotion());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemInfusedSeeds());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemMobAspect());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemMobDisplay());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemRevealingHelm());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemShareBook());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemSoulMould());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemSpellCloth());
        loadSimpleItem(new thaumic.tinkerer.common.item.ItemXPTalisman());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.armor.ItemGemBoots());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.armor.ItemGemChest());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.armor.ItemGemHelm());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.armor.ItemGemLegs());
        loadMetaItem(new thaumic.tinkerer.common.item.kami.armor.ItemIchorclothArmor());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.foci.ItemFocusRecall());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.foci.ItemFocusShadowbeam());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.foci.ItemFocusXPDrain());
        itemBlackHoleTalisman = loadSimpleItem(new thaumic.tinkerer.common.item.kami.ItemBlockTalisman());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.ItemCatAmulet());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.ItemIchorPouch());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.ItemKamiResource());
        itemPlacementMirror = loadSimpleItem(new thaumic.tinkerer.common.item.kami.ItemPlacementMirror());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.ItemProtoclay());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.ItemSkyPearl());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.tool.ItemIchorAxe());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.tool.ItemIchorAxeAdv());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.tool.ItemIchorPick());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.tool.ItemIchorPickAdv());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.tool.ItemIchorShovel());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.tool.ItemIchorShovelAdv());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.tool.ItemIchorSword());
        loadSimpleItem(new thaumic.tinkerer.common.item.kami.tool.ItemIchorSwordAdv());
        loadSimpleItem(new thaumic.tinkerer.common.item.quartz.ItemDarkQuartz());
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

    private Block loadSimpleBlock(Block newBlock) {
        try {
            final ITTinkererBlock ittBlock = (ITTinkererBlock) newBlock;
            if (ittBlock.shouldRegister()) {
                newBlock.setBlockName(ittBlock.getBlockName());
                blockRegistry.put(newBlock.getClass(), new Block[] { newBlock });
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
            FMLLog.log(
                    Level.WARN,
                    "Failed to load Block " + newBlock.getClass().getSimpleName() + ". This shouldn't happen!");
        }

        return null;
    }

    private Block[] loadMetaBlock(Block newBlock) {
        try {
            final ITTinkererBlock ittBlock = (ITTinkererBlock) newBlock;
            if (ittBlock.shouldRegister()) {
                newBlock.setBlockName(ittBlock.getBlockName());

                Block[] metaBlocks = ittBlock.getMetaBlocks();

                Block[] blockList = new Block[1 + metaBlocks.length];
                blockRegistry.put(newBlock.getClass(), blockList);
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
            FMLLog.log(
                    Level.WARN,
                    "Failed to load Block " + newBlock.getClass().getSimpleName() + ". This shouldn't happen!");
        }
        return null;
    }

    private Item loadSimpleItem(Item newItem) {
        try {
            final ITTinkererItem ittItem = (ITTinkererItem) newItem;
            if (ittItem.shouldRegister()) {
                newItem.setUnlocalizedName(ittItem.getItemName());
                itemRegistry.put(newItem.getClass(), new Item[] { newItem });

                return newItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.log(
                    Level.WARN,
                    "Failed to load Item " + newItem.getClass().getSimpleName() + ". This shouldn't happen!");
        }
        return null;
    }

    private Item[] loadMetaItem(Item newItem) {
        try {
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
                itemRegistry.put(newItem.getClass(), itemList);
                return itemList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.log(
                    Level.WARN,
                    "Failed to load Item " + newItem.getClass().getSimpleName() + ". This shouldn't happen!");
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

    @Deprecated // Use getItemArrayFromClass instead
    public ArrayList<Item> getItemFromClass(Class clazz) {
        return new ArrayList<>(Arrays.asList(getItemArrayFromClass(clazz)));
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

    @Deprecated // Use getBlockArrayFromClass instead
    public ArrayList<Block> getBlockFromClass(Class clazz) {
        return new ArrayList<>(Arrays.asList(getBlockArrayFromClass(clazz)));
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
