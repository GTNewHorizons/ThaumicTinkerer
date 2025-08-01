/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Dec 29, 2013, 10:15:39 PM (GMT)]
 */
package thaumic.tinkerer.common.item.kami;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemFocusPouch;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.core.proxy.TTCommonProxy;
import thaumic.tinkerer.common.lib.LibGuiIDs;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererItem;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

@Optional.Interface(iface = "baubles.api.expanded.IBaubleExpanded", modid = "Baubles|Expanded")
public class ItemIchorPouch extends ItemFocusPouch implements IBauble, IBaubleExpanded, ITTinkererItem {

    private static final boolean baublesExpandedLoaded = Loader.isModLoaded("Baubles|Expanded");

    public ItemIchorPouch() {
        super();
        setCreativeTab(ModCreativeTab.INSTANCE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        itemIcon = IconHelper.forItem(par1IconRegister, this);
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return itemIcon;
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return TTCommonProxy.kamiRarity;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par2World.isRemote)
            par3EntityPlayer.openGui(ThaumicTinkerer.instance, LibGuiIDs.GUI_ID_ICHOR_POUCH, par2World, 0, 0, 0);
        return par1ItemStack;
    }

    @Override
    public ItemStack[] getInventory(ItemStack item) {
        ItemStack[] inventory = new ItemStack[13 * 9];
        if (!item.hasTagCompound()) {
            return inventory;
        }

        NBTTagList tagList = item.stackTagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            int slot = tag.getByte("Slot") & 0xFF;

            if (slot < inventory.length) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

        return inventory;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List textLines, boolean showAdvancedInfo) {
        if (baublesExpandedLoaded) {
            BaubleItemHelper.addSlotInformation(textLines, getBaubleTypes(null));
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BELT;
    }

    @Override
    @Optional.Method(modid = "Baubles|Expanded")
    public String[] getBaubleTypes(ItemStack itemstack) {
        return new String[] { "belt", "focus_pouch" };
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return LibItemNames.ICHOR_POUCH;
    }

    @Override
    public boolean shouldRegister() {
        return ConfigHandler.enableKami;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!ConfigHandler.enableKami) return null;
        return (IRegisterableResearch) new KamiResearchItem(
                LibResearch.KEY_ICHOR_POUCH,
                new AspectList().add(Aspect.VOID, 2).add(Aspect.CLOTH, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MAN, 1),
                13,
                6,
                5,
                new ItemStack(this)).setParents(LibResearch.KEY_ICHOR_CLOTH)
                        .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR_POUCH));
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(
                LibResearch.KEY_ICHOR_POUCH,
                new ItemStack(this),
                9,
                new AspectList().add(Aspect.VOID, 64).add(Aspect.MAN, 32).add(Aspect.CLOTH, 32).add(Aspect.ELDRITCH, 32)
                        .add(Aspect.AIR, 64),
                new ItemStack(ConfigItems.itemFocusPouch),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 1),
                new ItemStack(ConfigItems.itemFocusPortableHole),
                new ItemStack(Items.diamond),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 1),
                new ItemStack(ConfigBlocks.blockChestHungry),
                new ItemStack(ConfigBlocks.blockJar, 1, 3));
    }
}
