package thaumic.tinkerer.common.item.kami.foci;

import java.awt.Color;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.helper.ExperienceHelper;
import thaumic.tinkerer.common.core.proxy.TTCommonProxy;
import thaumic.tinkerer.common.item.ItemXPTalisman;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

public class ItemFocusXPDrain extends ItemModKamiFocus {

    AspectList visUsage = new AspectList();

    @Override
    public boolean isVisCostPerTick(ItemStack stack) {
        return true;
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "TTKXP" + super.getSortingHelper(itemstack);
    }

    @Override
    public void onUsingFocusTick(ItemStack stack, EntityPlayer player, int count) {
        if (player.worldObj.isRemote) return;

        ItemWandCasting wand = (ItemWandCasting) stack.getItem();
        AspectList aspects = wand.getAllVis(stack);

        Aspect lowestAspect = null;
        int lowestAmount = Integer.MAX_VALUE;
        int maxVis = wand.getMaxVis(stack);

        for (Aspect aspect : Aspect.getPrimalAspects()) {
            int amount = aspects.getAmount(aspect);

            if (amount < maxVis && amount < lowestAmount) {
                lowestAmount = amount;
                lowestAspect = aspect;
            }
        }

        if (lowestAspect != null) {
            int potency = wand.getFocusPotency(stack);
            float multiplier = 1.0F + (0.2F * potency);

            int intendedAdd = Math.round(500 * multiplier);
            int actualAdd = Math.min(intendedAdd, maxVis - lowestAmount);

            int xpUse = getXpUse(stack);
            int scaledXp = Math.max(1, Math.round(xpUse * (actualAdd / (float) intendedAdd)));

            if (player.experienceTotal >= scaledXp) {
                ExperienceHelper.drainPlayerXP(player, scaledXp);
                wand.storeVis(stack, lowestAspect, lowestAmount + actualAdd);
            }
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return getFocusColor(par1ItemStack);
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        EntityPlayer player = ThaumicTinkerer.proxy.getClientPlayer();
        return player == null ? 0xFFFFFF : Color.HSBtoRGB(player.ticksExisted * 2 % 360 / 360F, 1F, 1F);
    }

    int getXpUse(ItemStack stack) {
        return 15 - this.getUpgradeLevel(stack, FocusUpgradeType.frugal);
    }

    @Override
    protected void addVisCostTooltip(AspectList cost, ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("item.Focus.cost2"));
        list.add(
                " " + EnumChatFormatting.GREEN
                        + StatCollector.translateToLocal("ttmisc.experience")
                        + EnumChatFormatting.WHITE
                        + " x "
                        + getXpUse(stack));
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        return visUsage;
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank) {
        if (rank <= 5) {
            return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency };
        }
        return null;
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return TTCommonProxy.kamiRarity;
    }

    @Override
    public String getItemName() {
        return LibItemNames.FOCUS_XP_DRAIN;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!ConfigHandler.enableKami) return null;
        return (IRegisterableResearch) new KamiResearchItem(
                LibResearch.KEY_FOCUS_XP_DRAIN,
                new AspectList().add(Aspect.MIND, 2).add(Aspect.MAGIC, 1).add(Aspect.AURA, 1).add(Aspect.MAN, 1),
                12,
                3,
                5,
                new ItemStack(this)).setParents(LibResearch.KEY_ICHORCLOTH_ROD)
                        .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_XP_DRAIN));
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(
                LibResearch.KEY_FOCUS_XP_DRAIN,
                new ItemStack(this),
                12,
                new AspectList().add(Aspect.MIND, 65).add(Aspect.TAINT, 16).add(Aspect.MAGIC, 50).add(Aspect.AURA, 32),
                new ItemStack(Items.ender_pearl),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)),
                new ItemStack(Items.experience_bottle),
                new ItemStack(Items.diamond),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemXPTalisman.class)),
                new ItemStack(Blocks.enchanting_table),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)));
    }
}
