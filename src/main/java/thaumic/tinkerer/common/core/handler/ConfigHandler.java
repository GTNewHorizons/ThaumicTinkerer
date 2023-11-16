/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [4 Sep 2013, 16:53:24 (GMT)]
 */
package thaumic.tinkerer.common.core.handler;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import thaumic.tinkerer.common.dim.OreClusterGenerator;
import thaumic.tinkerer.common.lib.LibEnchantIDs;
import thaumic.tinkerer.common.lib.LibEnchantNames;

public final class ConfigHandler {

    public static boolean enableFlight;
    public static boolean useTootlipIndicators;
    public static boolean enableSurvivalShareTome;
    public static boolean enableDebugCommands;
    public static boolean useOreDictMetal;
    public static boolean repairTConTools;

    public static String[] forbiddenDimensions;
    public static String[] blockBlacklist;

    public static boolean enableFire;
    public static boolean enableCake;
    public static boolean enableFireMechanics;
    public static boolean cropsAllowBonemeal;

    public static int potionFireId;
    public static int potionWaterId;
    public static int potionEarthId;
    public static int potionAirId;

    public static boolean enableKami;
    public static boolean showPlacementMirrorBlocks = true;
    public static int netherDimensionID;
    public static int endDimensionID;
    public static int bedrockDimensionID = 19;

    public static int soulHeartHeight;

    public static int visShadowbeamOrder;
    public static int visShadowbeamAir;
    public static int visShadowbeamEntropy;
    public static int baseDamageShadowbeam;

    private static Configuration config;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        new ConfigCategory("potions");
        new ConfigCategory("enchantments");
        new ConfigCategory("general.kami");

        new ConfigCategory("balance.shadowbeam");

        config.addCustomCategoryComment("general.kami", "These will only be used if KAMI is enabled.");

        config.load();

        forbiddenDimensions = config.getStringList(
                "Forbidden Dimensions",
                "general",
                new String[] { "" },
                "Disallow Bottomless Pouch inventory for certain dimension ID's");

        blockBlacklist = config.getStringList(
                "Focus of Dislocation Blacklist",
                "general",
                new String[] { "avaritiaddons:CompressedChest", "avaritiaddons:InfinityChest" },
                "These blocks will be disallowed for Focus of Dislocation");

        enableKami = config.getBoolean("kami.forceenabled", "general", true, "Set to true to enable all KAMI stuff");

        useTootlipIndicators = config.getBoolean(
                "tooltipIndicators.enabled",
                "general",
                true,
                "Set to false to disable the [TT] tooltips in the thauminomicon.");

        enableSurvivalShareTome = config.getBoolean(
                "shareTome.survival.enabled",
                "general",
                true,
                "Set to false to disable the crafting recipe for the Tome of Research Sharing.");

        enableDebugCommands = config
                .getBoolean("debugCommands.enabled", "general", false, "Set to true to enable debugging commands.");

        enableFlight = config
                .getBoolean("modFlight.enabled", "general", true, "Set to true to enable flight in this mod.");

        repairTConTools = config.getBoolean(
                "repairTconTools.enabled",
                "general",
                false,
                "Can Thaumic Tinkerer repair Tinkers Construct tools.");

        useOreDictMetal = config.getBoolean(
                "oreDictMetal.enabled",
                "general",
                true,
                "Set to false to disable usage of ore dictionary metals (tin and copper).");

        enableFire = config.getBoolean("imbuedFire.enabled", "general", true, "Set to false to disable imbued fire.");

        enableFireMechanics = config.getBoolean(
                "imbuedFireSpread.enabled",
                "general",
                true,
                "Set to false to disable imbued fire spreading/acting mechanism.");

        enableCake = config.getBoolean(
                "imbuedFire.cake.enabled",
                "general",
                true,
                "Set to false to disable imbued fire making cake. For those people who don't like cake");

        cropsAllowBonemeal = config.getBoolean(
                "cropsAllowBonemeal.enabled",
                "general",
                false,
                "Allows crops to be grown using bonemeal. Useful for debug purposes.");

        soulHeartHeight = config.getInt(
                "soulHeart.height",
                "general",
                49,
                0,
                256,
                "The height of the Soul Heart bar. You can change this if you have a mod that adds a bar in that spot.");

        // Potions
        potionFireId = config.getInt("Fire Potion id", "potions", 88, 30, 1023, "Set to the potion id for fire potion");
        potionEarthId = config
                .getInt("Earth Potion id", "potions", 87, 30, 1023, "Set to the potion id for earth potion");
        potionWaterId = config
                .getInt("Water Potion id", "potions", 89, 30, 1023, "Set to the potion id for water potion");
        potionAirId = config.getInt("Air Potion id", "potions", 86, 30, 1023, "Set to the potion id for air potion");

        // Shadowbeam Balance
        visShadowbeamAir = config.getInt(
                "aer vis cost",
                "balance.shadowbeam",
                15,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                "vis cost for the aer aspect, 1/100th in-game");

        visShadowbeamOrder = config.getInt(
                "ordo vis cost",
                "balance.shadowbeam",
                25,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                "vis cost for the ordo aspect, 1/100th in-game");

        visShadowbeamEntropy = config.getInt(
                "perditio vis cost",
                "balance.shadowbeam",
                25,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                "vis cost for the perditio aspect, 1/100th in-game");

        baseDamageShadowbeam = config.getInt(
                "shadowbeam base damage",
                "balance.shadowbeam",
                8,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                "base damage of the shadowbeam focus");

        if (enableKami) {

            bedrockDimensionID = config.getInt(
                    "Bedrock dimension id",
                    "general.kami",
                    -19,
                    -1023,
                    1023,
                    "Set to the dimension id wished for bedrock dimension, or 0 to disable");

            OreClusterGenerator.blacklist = config.getStringList(
                    "Bedrock dimension ore Blacklist",
                    "general.kami",
                    new String[] { "oreFirestone" },
                    "These ores will not be spawned in the bedrock dimension");

            OreClusterGenerator.density = config.getInt(
                    "Bedrock Dimension ore density",
                    "general",
                    1,
                    0,
                    1023,
                    "The number of vertical veins of ore per chunk. Default: 1");

            showPlacementMirrorBlocks = config.getBoolean(
                    "placementMirror.blocks.show",
                    "general.kami",
                    true,
                    "Set to false to remove the phantom blocks displayed by the Worldshaper's Seeing Glass.");

            netherDimensionID = config.getInt(
                    "dimension.nether.id",
                    "general.kami",
                    -1,
                    -1023,
                    1023,
                    "The Dimension ID for the Nether, leave at -1 if you don't modify it with another mod/plugin.");

            endDimensionID = config.getInt(
                    "dimension.end.id",
                    "general.kami",
                    1,
                    -1023,
                    1023,
                    "The Dimension ID for the End, leave at 1 if you don't modify it with another mod/plugin.");
        }

        LibEnchantIDs.idAscentBoost = loadEnchant(LibEnchantNames.ASCENT_BOOST, LibEnchantIDs.idAscentBoost);
        LibEnchantIDs.idSlowFall = loadEnchant(LibEnchantNames.SLOW_FALL, LibEnchantIDs.idSlowFall);
        LibEnchantIDs.idAutoSmelt = loadEnchant(LibEnchantNames.AUTO_SMELT, LibEnchantIDs.idAutoSmelt);
        LibEnchantIDs.idDesintegrate = loadEnchant(LibEnchantNames.DESINTEGRATE, LibEnchantIDs.idDesintegrate);
        LibEnchantIDs.idQuickDraw = loadEnchant(LibEnchantNames.QUICK_DRAW, LibEnchantIDs.idQuickDraw);
        LibEnchantIDs.idVampirism = loadEnchant(LibEnchantNames.VAMPIRISM, LibEnchantIDs.idVampirism);

        LibEnchantIDs.focusedStrike = loadEnchant(LibEnchantNames.focusedStrike, LibEnchantIDs.focusedStrike);
        LibEnchantIDs.dispersedStrikes = loadEnchant(LibEnchantNames.dispersedStrikes, LibEnchantIDs.dispersedStrikes);
        LibEnchantIDs.finalStrike = loadEnchant(LibEnchantNames.finalStrike, LibEnchantIDs.finalStrike);
        LibEnchantIDs.valiance = loadEnchant(LibEnchantNames.valiance, LibEnchantIDs.valiance);
        LibEnchantIDs.pounce = loadEnchant(LibEnchantNames.pounce, LibEnchantIDs.pounce);
        LibEnchantIDs.shockwave = loadEnchant(LibEnchantNames.shockwave, LibEnchantIDs.shockwave);
        LibEnchantIDs.shatter = loadEnchant(LibEnchantNames.shatter, LibEnchantIDs.shatter);
        LibEnchantIDs.tunnel = loadEnchant(LibEnchantNames.tunnel, LibEnchantIDs.tunnel);

        config.save();
    }

    private static int loadEnchant(String label, int defaultID) {
        return config.get("enchantments", "id_enchant." + label, defaultID).getInt(defaultID);
    }
}
