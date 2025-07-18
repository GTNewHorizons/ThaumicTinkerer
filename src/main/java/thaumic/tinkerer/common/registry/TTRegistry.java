package thaumic.tinkerer.common.registry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.research.IRegisterableResearch;

public class TTRegistry {

    private ArrayList<Class> itemClasses = new ArrayList<>();
    private HashMap<Class, ArrayList<Item>> itemRegistry = new HashMap<>();

    private ArrayList<Class> blockClasses = new ArrayList<>();
    private HashMap<Class, ArrayList<Block>> blockRegistry = new HashMap<>();

    public void registerClasses() {
        blockClasses.add(thaumic.tinkerer.common.block.BlockAnimationTablet.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockAspectAnalyzer.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockEnchanter.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockForcefield.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockFunnel.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockGaseousLight.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockGaseousShadow.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockGolemConnector.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockInfusedFarmland.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockInfusedGrain.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockMagnet.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockNitorGas.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockPlatform.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockRPlacer.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockRepairer.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockSummon.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockTravelSlab.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockTravelStairs.class);
        blockClasses.add(thaumic.tinkerer.common.block.BlockWardSlab.class);
        blockClasses.add(thaumic.tinkerer.common.block.fire.BlockFireAir.class);
        blockClasses.add(thaumic.tinkerer.common.block.fire.BlockFireChaos.class);
        blockClasses.add(thaumic.tinkerer.common.block.fire.BlockFireEarth.class);
        blockClasses.add(thaumic.tinkerer.common.block.fire.BlockFireIgnis.class);
        blockClasses.add(thaumic.tinkerer.common.block.fire.BlockFireOrder.class);
        blockClasses.add(thaumic.tinkerer.common.block.fire.BlockFireWater.class);
        blockClasses.add(thaumic.tinkerer.common.block.kami.BlockBedrockPortal.class);
        blockClasses.add(thaumic.tinkerer.common.block.kami.BlockWarpGate.class);
        blockClasses.add(thaumic.tinkerer.common.block.mobilizer.BlockMobilizer.class);
        blockClasses.add(thaumic.tinkerer.common.block.mobilizer.BlockMobilizerRelay.class);
        blockClasses.add(thaumic.tinkerer.common.block.quartz.BlockDarkQuartz.class);
        blockClasses.add(thaumic.tinkerer.common.block.quartz.BlockDarkQuartzSlab.class);
        blockClasses.add(thaumic.tinkerer.common.block.quartz.BlockDarkQuartzStairs.class);
        blockClasses.add(thaumic.tinkerer.common.block.transvector.BlockTransvectorDislocator.class);
        blockClasses.add(thaumic.tinkerer.common.block.transvector.BlockTransvectorInterface.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemBloodSword.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemBrightNitor.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemCleansingTalisman.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemConnector.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemGas.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemGasRemover.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemInfusedGrain.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemInfusedInkwell.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemInfusedPotion.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemInfusedSeeds.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemMobAspect.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemMobDisplay.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemRevealingHelm.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemShareBook.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemSoulMould.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemSpellCloth.class);
        itemClasses.add(thaumic.tinkerer.common.item.ItemXPTalisman.class);
        itemClasses.add(thaumic.tinkerer.common.item.foci.ItemFocusDeflect.class);
        itemClasses.add(thaumic.tinkerer.common.item.foci.ItemFocusDislocation.class);
        itemClasses.add(thaumic.tinkerer.common.item.foci.ItemFocusEnderChest.class);
        itemClasses.add(thaumic.tinkerer.common.item.foci.ItemFocusFlight.class);
        itemClasses.add(thaumic.tinkerer.common.item.foci.ItemFocusHeal.class);
        itemClasses.add(thaumic.tinkerer.common.item.foci.ItemFocusSmelt.class);
        itemClasses.add(thaumic.tinkerer.common.item.foci.ItemFocusTelekinesis.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.ItemBlockTalisman.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.ItemCatAmulet.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.ItemIchorPouch.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.ItemKamiResource.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.ItemPlacementMirror.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.ItemProtoclay.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.ItemSkyPearl.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.armor.ItemGemBoots.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.armor.ItemGemChest.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.armor.ItemGemHelm.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.armor.ItemGemLegs.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.armor.ItemIchorclothArmor.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.foci.ItemFocusRecall.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.foci.ItemFocusShadowbeam.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.foci.ItemFocusXPDrain.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.tool.ItemIchorAxe.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.tool.ItemIchorAxeAdv.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.tool.ItemIchorPick.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.tool.ItemIchorPickAdv.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.tool.ItemIchorShovel.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.tool.ItemIchorShovelAdv.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.tool.ItemIchorSword.class);
        itemClasses.add(thaumic.tinkerer.common.item.kami.tool.ItemIchorSwordAdv.class);
        itemClasses.add(thaumic.tinkerer.common.item.quartz.ItemDarkQuartz.class);
    }

    public void registerResearch(ITTinkererRegisterable nextItem) {
        IRegisterableResearch registerableResearch = nextItem.getResearchItem();
        if (registerableResearch != null) {
            registerableResearch.registerResearch();
        }
    }

    public void registerRecipe(ITTinkererRegisterable nextItem) {
        ThaumicTinkererRecipe thaumicTinkererRecipe = nextItem.getRecipeItem();
        if (thaumicTinkererRecipe != null) {
            thaumicTinkererRecipe.registerRecipe();
        }
    }

    public void preInit() {
        registerClasses();
        for (Class clazz : blockClasses) {
            try {
                Block newBlock = (Block) clazz.newInstance();
                if (((ITTinkererBlock) newBlock).shouldRegister()) {
                    newBlock.setBlockName(((ITTinkererBlock) newBlock).getBlockName());
                    ArrayList<Block> blockList = new ArrayList<>();
                    blockList.add(newBlock);
                    if (((ITTinkererBlock) newBlock).getSpecialParameters() != null) {
                        for (Object param : ((ITTinkererBlock) newBlock).getSpecialParameters()) {
                            for (Constructor constructor : clazz.getConstructors()) {
                                if (constructor.getParameterTypes().length > 0
                                        && constructor.getParameterTypes()[0].isAssignableFrom(param.getClass())) {
                                    Block nextBlock = (Block) clazz.getConstructor(param.getClass()).newInstance(param);
                                    nextBlock.setBlockName(((ITTinkererBlock) nextBlock).getBlockName());
                                    blockList.add(nextBlock);
                                    break;
                                }
                            }
                        }
                    }
                    blockRegistry.put(clazz, blockList);

                    if (((ITTinkererBlock) newBlock).getItemBlock() != null) {
                        Item newItem = ((ITTinkererBlock) newBlock).getItemBlock().getConstructor(Block.class)
                                .newInstance(newBlock);
                        newItem.setUnlocalizedName(((ITTinkererItem) newItem).getItemName());
                        ArrayList<Item> itemList = new ArrayList<>();
                        itemList.add(newItem);
                        itemRegistry.put(((ITTinkererBlock) newBlock).getItemBlock(), itemList);
                    }
                }
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException
                    | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (Class clazz : itemClasses) {
            try {
                Item newItem = (Item) clazz.newInstance();
                if (((ITTinkererItem) newItem).shouldRegister()) {
                    newItem.setUnlocalizedName(((ITTinkererItem) newItem).getItemName());
                    ArrayList<Item> itemList = new ArrayList<>();
                    itemList.add(newItem);
                    if (newItem == null) {
                        ThaumicTinkerer.log.debug(clazz.getName() + " Returned a null item upon registration");
                        continue;
                    }
                    if (((ITTinkererItem) newItem).getSpecialParameters() != null) {
                        for (Object param : ((ITTinkererItem) newItem).getSpecialParameters()) {
                            for (Constructor constructor : clazz.getConstructors()) {
                                if (constructor.getParameterTypes().length > 0
                                        && constructor.getParameterTypes()[0].isAssignableFrom(param.getClass())) {
                                    Item nextItem = (Item) constructor.newInstance(param);
                                    nextItem.setUnlocalizedName(((ITTinkererItem) nextItem).getItemName());
                                    itemList.add(nextItem);
                                    break;
                                }
                            }
                        }
                    }
                    itemRegistry.put(clazz, itemList);
                }
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (ArrayList<Block> blockArrayList : blockRegistry.values()) {
            for (Block block : blockArrayList) {
                if (((ITTinkererBlock) block).getItemBlock() != null) {
                    GameRegistry.registerBlock(
                            block,
                            ((ITTinkererBlock) block).getItemBlock(),
                            ((ITTinkererBlock) block).getBlockName());
                } else {
                    GameRegistry.registerBlock(block, ((ITTinkererBlock) block).getBlockName());
                }
                if (((ITTinkererBlock) block).getTileEntity() != null) {
                    GameRegistry.registerTileEntity(
                            ((ITTinkererBlock) block).getTileEntity(),
                            LibResources.PREFIX_MOD + ((ITTinkererBlock) block).getBlockName());
                }
                if (block instanceof IMultiTileEntityBlock) {
                    for (Map.Entry<Class<? extends TileEntity>, String> tile : ((IMultiTileEntityBlock) block)
                            .getAdditionalTileEntities().entrySet()) {
                        GameRegistry.registerTileEntity(tile.getKey(), tile.getValue());
                    }
                }
                if (((ITTinkererBlock) block).shouldDisplayInTab()
                        && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                    ModCreativeTab.INSTANCE.addBlock(block);
                }
            }
        }
    }

    public ArrayList<Item> getItemFromClass(Class clazz) {
        return itemRegistry.get(clazz);
    }

    public Item getFirstItemFromClass(Class clazz) {
        return itemRegistry.get(clazz) != null ? itemRegistry.get(clazz).get(0) : null;
    }

    public Item getItemFromClassAndName(Class clazz, String s) {
        if (itemRegistry.get(clazz) == null) {
            return null;
        }
        for (Item i : getItemFromClass(clazz)) {
            if (((ITTinkererItem) i).getItemName().equals(s)) {
                return i;
            }
        }
        return null;
    }

    public Block getBlockFromClassAndName(Class clazz, String s) {
        if (blockRegistry.get(clazz) == null) {
            return null;
        }
        for (Block i : getBlockFromClass(clazz)) {
            if (((ITTinkererBlock) i).getBlockName().equals(s)) {
                return i;
            }
        }
        return null;
    }

    public ArrayList<Block> getBlockFromClass(Class clazz) {
        return blockRegistry.get(clazz);
    }

    public Block getFirstBlockFromClass(Class clazz) {
        return blockRegistry.get(clazz) != null ? blockRegistry.get(clazz).get(0) : null;
    }

    public void init() {

        for (ArrayList<Item> itemArrayList : itemRegistry.values()) {
            for (Item item : itemArrayList) {
                registerRecipe((ITTinkererRegisterable) item);
            }
        }

        for (ArrayList<Block> blockArrayList : blockRegistry.values()) {
            for (Block block : blockArrayList) {
                registerRecipe((ITTinkererRegisterable) block);
            }
        }
        for (ArrayList<Item> itemArrayList : itemRegistry.values()) {
            for (Item item : itemArrayList) {
                if (!(item instanceof ItemBlock)) {
                    GameRegistry.registerItem(item, ((ITTinkererItem) item).getItemName());

                    if (((ITTinkererItem) item).shouldDisplayInTab()
                            && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                        ModCreativeTab.INSTANCE.addItem(item);
                    }
                }
            }
        }
        ModCreativeTab.INSTANCE.addAllItemsAndBlocks();
    }

    public void postInit() {
        for (ArrayList<Item> itemArrayList : itemRegistry.values()) {
            for (Item item : itemArrayList) {
                registerResearch((ITTinkererRegisterable) item);
            }
        }
        for (ArrayList<Block> blockArrayList : blockRegistry.values()) {
            for (Block block : blockArrayList) {
                registerResearch((ITTinkererRegisterable) block);
            }
        }
    }
}
