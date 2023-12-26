/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [11 Sep 2013, 15:45:16 (GMT)]
 */
package thaumic.tinkerer.common.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.potions.PotionWarpWard;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.helper.ItemNBTHelper;
import thaumic.tinkerer.common.item.quartz.ItemDarkQuartz;
import thaumic.tinkerer.common.lib.LibFeatures;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ItemBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

public class ItemCleansingTalisman extends ItemBase implements IBauble {

    private static final String TAG_ENABLED = "enabled";

    private IIcon enabledIcon;

    private static final int EFFECT_BURNING = -1;
    // 0 = no effect, otherwise either EFFECT_BURNING or the ID of the effect removed.
    private HashMap<UUID, Integer> mLastEffectRemoved = new HashMap<>();
    private final HashMap<UUID, Collection<Integer>> mPermanentEffects = new HashMap<>();

    public ItemCleansingTalisman() {
        setMaxStackSize(1);
        setMaxDamage(LibFeatures.CLEANSING_TALISMAN_USES);
    }

    public static boolean isEnabled(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_ENABLED, false);
    }

    public static void flipEnabled(ItemStack stack) {
        ItemNBTHelper.setBoolean(stack, TAG_ENABLED, !isEnabled(stack));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.isSneaking()) {
            flipEnabled(par1ItemStack);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
        }

        return par1ItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        itemIcon = IconHelper.forItem(par1IconRegister, this, 0);
        enabledIcon = IconHelper.forItem(par1IconRegister, this, 1);
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_CLEANSING_TALISMAN,
                new AspectList().add(Aspect.HEAL, 2).add(Aspect.ORDER, 1).add(Aspect.POISON, 1),
                -3,
                4,
                3,
                new ItemStack(this)).setSecondary().setParents(LibResearch.KEY_DARK_QUARTZ).setPages(
                        new ResearchPage("0"),
                        ResearchHelper.infusionPage(LibResearch.KEY_CLEANSING_TALISMAN));
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(
                LibResearch.KEY_CLEANSING_TALISMAN,
                new ItemStack(this),
                5,
                new AspectList().add(Aspect.HEAL, 10).add(Aspect.TOOL, 10).add(Aspect.MAN, 20).add(Aspect.LIFE, 10),
                new ItemStack(Items.ender_pearl),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)),
                new ItemStack(Items.ghast_tear),
                new ItemStack(ConfigItems.itemResource, 1, 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (isEnabled(par1ItemStack)) par3List.add(StatCollector.translateToLocal("ttmisc.active"));
        else par3List.add(StatCollector.translateToLocal("ttmisc.inactive"));
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return isEnabled(stack) ? enabledIcon : itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.uncommon;
    }

    @Override
    public int getItemEnchantability() {
        // Equivalent to iron.
        return 10;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack par1ItemStack, EntityLivingBase player) {
        World par2World = player.worldObj;
        if (isEnabled(par1ItemStack) && !par2World.isRemote) {
            if (player.ticksExisted % 20 == 0) {
                if (player instanceof EntityPlayer) {

                    int lastEffectRemoved;
                    Collection<Integer> permanentEffects;

                    UUID uuid = player.getUniqueID();
                    if (mPermanentEffects.containsKey(uuid)) {
                        lastEffectRemoved = mLastEffectRemoved.get(uuid);
                        permanentEffects = mPermanentEffects.get(uuid);
                    } else {
                        lastEffectRemoved = 0;
                        permanentEffects = new HashSet<>();
                        mLastEffectRemoved.put(uuid, lastEffectRemoved);
                        mPermanentEffects.put(uuid, permanentEffects);
                    }

                    // All effects we try to remove in this operation, including permanent effects.
                    Collection<Integer> effectsToRemove = new HashSet<>();
                    // One new effect we remove this operation, with a durability cost.
                    int effectRemoved = 0;

                    if (player.isBurning()) {
                        if (permanentEffects.contains(EFFECT_BURNING)) {
                            // Remove permanent effects at no cost.
                            effectsToRemove.add(EFFECT_BURNING);
                        } else if (lastEffectRemoved == EFFECT_BURNING) {
                            // We removed burning last time, player is on fire permanently.
                            permanentEffects.add(EFFECT_BURNING);
                            effectsToRemove.add(EFFECT_BURNING);
                        } else if (effectRemoved == 0) {
                            // Actually remove burning.
                            effectsToRemove.add(EFFECT_BURNING);
                            effectRemoved = EFFECT_BURNING;
                        }
                    }

                    Collection<PotionEffect> potions = player.getActivePotionEffects();
                    for (PotionEffect potion : potions) {
                        // Skip effects without a duration; e.g., dolly, TiC cleaver, etc.
                        if (potion.duration < 20) {
                            continue;
                        }

                        int id = potion.getPotionID();
                        boolean badEffect;
                        badEffect = ReflectionHelper.getPrivateValue(
                                Potion.class,
                                Potion.potionTypes[id],
                                new String[] { "isBadEffect", "field_76418_K" });
                        if (Potion.potionTypes[id] instanceof PotionWarpWard) {
                            badEffect = false;
                        }

                        if (badEffect) {
                            if (permanentEffects.contains(id)) {
                                // Remove permanent effects at no cost.
                                effectsToRemove.add(id);
                            } else if (lastEffectRemoved == id) {
                                // We removed this effect last time, player is affected by this permanently.
                                effectsToRemove.add(id);
                                permanentEffects.add(id);
                            } else if (effectRemoved == 0) {
                                // Actually remove the effect.
                                effectsToRemove.add(id);
                                effectRemoved = id;
                            }
                        }
                    }

                    for (int effectID : effectsToRemove) {
                        if (effectID == EFFECT_BURNING) {
                            player.extinguish();
                        } else {
                            player.removePotionEffect(effectID);
                        }
                    }

                    // True if some permanent effects are no longer affecting the player.
                    boolean permanentRemoved = permanentEffects.retainAll(effectsToRemove);

                    if (effectRemoved != 0) {
                        int damage = 1;

                        if (effectRemoved == Config.potionBlurredID || effectRemoved == Config.potionDeathGazeID
                                || effectRemoved == Config.potionInfVisExhaustID
                                || effectRemoved == Config.potionSunScornedID
                                || effectRemoved == Config.potionUnHungerID) {
                            damage = 10;
                        }

                        par1ItemStack.damageItem(damage, player);
                        if (par1ItemStack.stackSize <= 0) {
                            // Slot 0 = talisman slot.
                            BaublesApi.getBaubles((EntityPlayer) player).setInventorySlotContents(0, null);
                        }
                    }

                    if (effectRemoved != 0 || permanentRemoved) {
                        par2World.playSoundAtEntity(player, "thaumcraft:wand", 0.3F, 0.1F);
                    }

                    lastEffectRemoved = effectRemoved;
                    mLastEffectRemoved.put(uuid, lastEffectRemoved);
                }
            }
        }
    }

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
    public String getItemName() {
        return LibItemNames.CLEANSING_TALISMAN;
    }
}
