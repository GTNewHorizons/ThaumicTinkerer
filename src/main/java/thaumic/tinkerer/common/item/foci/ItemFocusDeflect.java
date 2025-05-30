/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Dec 8, 2013, 6:26:09 PM (GMT)]
 */
package thaumic.tinkerer.common.item.foci;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.common.Loader;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.compat.BloodMagic;
import thaumic.tinkerer.common.compat.BotaniaFunctions;
import thaumic.tinkerer.common.core.helper.ProjectileHelper;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.common.entity.EntityMagicMissile;

public class ItemFocusDeflect extends ItemModFocus {

    public static List<Class<?>> DeflectBlacklist = new ArrayList<>();
    private static final AspectList visUsage = new AspectList().add(Aspect.ORDER, 8).add(Aspect.AIR, 4);

    public static void setupBlackList() {
        DeflectBlacklist.add(EntityExpBottle.class);
        if (Loader.isModLoaded("BloodMagic")) {
            BloodMagic.setupClass();
        }
    }

    public static void protectFromProjectiles(EntityPlayer p, ItemStack stack) {
        int range = 0;
        if (stack != null) {
            ItemWandCasting wand = (ItemWandCasting) stack.getItem();
            range = wand.getFocusEnlarge(stack);
        }

        List<Entity> projectiles = p.worldObj.getEntitiesWithinAABB(
                IProjectile.class,
                AxisAlignedBB.getBoundingBox(
                        p.posX - (4 + range),
                        p.posY - (4 + range),
                        p.posZ - (4 + range),
                        p.posX + (3 + range),
                        p.posY + (3 + range),
                        p.posZ + (3 + range)));

        for (Entity e : projectiles) {
            if (CheckBlackList(e) || ProjectileHelper.getOwner(e) == p) continue;
            Vector3 motionVec = new Vector3(e.motionX, e.motionY, e.motionZ).normalize().multiply(
                    Math.sqrt(
                            (e.posX - p.posX) * (e.posX - p.posX) + (e.posY - p.posY) * (e.posY - p.posY)
                                    + (e.posZ - p.posZ) * (e.posZ - p.posZ))
                            * 2);

            for (int i = 0; i < 6; i++)
                ThaumicTinkerer.tcProxy.sparkle((float) e.posX, (float) e.posY, (float) e.posZ, 6);

            e.posX += motionVec.x;
            e.posY += motionVec.y;
            e.posZ += motionVec.z;

            checkBotaniaMagicMissile(e);
        }
    }

    private static void checkBotaniaMagicMissile(Entity e) {
        if (!Loader.isModLoaded("Botania")) return;
        if (e instanceof EntityMagicMissile) {
            e.setDead();
        }
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank) {
        switch (rank) {
            case 1:
                return new FocusUpgradeType[] { FocusUpgradeType.frugal };
            case 2:
                return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
            case 3:
                return new FocusUpgradeType[] { FocusUpgradeType.frugal };
            case 4:
                return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
            case 5:
                return new FocusUpgradeType[] { FocusUpgradeType.frugal };
        }
        return null;
    }

    private static boolean CheckBlackList(Entity entity) {
        Class<? extends Entity> aClass = entity.getClass();
        if (DeflectBlacklist.contains(aClass)) return true;
        if (Loader.isModLoaded("Botania") && entity instanceof IManaBurst) {
            return BotaniaFunctions.isEntityHarmless(entity);
        }
        for (Class<?> testClass : DeflectBlacklist) {
            if (testClass.isInterface() && testClass.isAssignableFrom(aClass)) return true;
        }
        return false;
    }

    @Override
    public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int ticks) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();

        if (wand.consumeAllVis(stack, p, getVisCost(stack), true, false)) protectFromProjectiles(p, stack);
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "TTDF" + super.getSortingHelper(itemstack);
    }

    @Override
    public boolean isVisCostPerTick(ItemStack stack) {
        return true;
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0xFFFFFF;
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        return visUsage;
    }

    @Override
    public String getItemName() {
        return LibItemNames.FOCUS_DEFLECT;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!Config.allowMirrors) {
            return null;
        }
        return (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_FOCUS_DEFLECT,
                new AspectList().add(Aspect.MOTION, 2).add(Aspect.AIR, 1).add(Aspect.ORDER, 1).add(Aspect.DEATH, 1),
                -4,
                -3,
                3,
                new ItemStack(this)).setConcealed().setParents(LibResearch.KEY_FOCUS_SMELT)
                        .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_DEFLECT))
                        .setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(
                LibResearch.KEY_FOCUS_DEFLECT,
                new ItemStack(this),
                5,
                new AspectList().add(Aspect.AIR, 15).add(Aspect.ARMOR, 5).add(Aspect.ORDER, 20),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusFlight.class)),
                new ItemStack(ConfigItems.itemResource, 1, 10),
                new ItemStack(ConfigItems.itemResource, 1, 10),
                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 3),
                new ItemStack(ConfigItems.itemShard, 1, 4));
    }
}
