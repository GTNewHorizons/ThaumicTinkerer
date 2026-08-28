package thaumic.tinkerer.common.core.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.commons.lang3.text.WordUtils;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemWispEssence;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.quartz.BlockDarkQuartz;
import thaumic.tinkerer.common.item.ItemBrightNitor;
import thaumic.tinkerer.common.item.ItemInfusedGrain;

/**
 * Created by pixlepix on 8/7/14, reworked by Spaghetti on 2026-08-21 These very lines of code will cause great trouble
 * in the future. However, they have proven to be less chaotic than anticipated in the past, so they live on ... even if
 * that means nerfing them to the ground.
 */
public class AspectCropLootManager {

    public static class LootEntry {

        public final ItemStack stack;
        public final int weight;

        public LootEntry(ItemStack stack, int weight) {
            this.stack = stack;
            this.weight = weight;
        }
    }

    private static final Random RAND = new Random();

    private static HashMap<Aspect, List<LootEntry>> lootMap = new HashMap<>();

    public static ItemStack getLootForAspect(Aspect aspect) {
        List<LootEntry> aspectLootList = lootMap.get(aspect);

        if (aspectLootList == null || aspectLootList.isEmpty()) {
            return null;
        }

        int sum = 0;
        for (LootEntry entry : aspectLootList) {
            sum += entry.weight;
        }

        if (sum > 0) {
            int randInt = RAND.nextInt(sum);
            for (LootEntry entry : aspectLootList) {
                if (randInt < entry.weight) {
                    return entry.stack.copy();
                }
                randInt -= entry.weight;
            }
        }
        return null;
    }

    public static void addAspectLoot(Aspect aspect, ItemStack stack) {
        addAspectLoot(aspect, stack, 1);
    }

    public static void addAspectLoot(Aspect aspect, String target) {
        addAspectLoot(aspect, target, 1);
    }

    public static void addAspectLoot(Aspect aspect, String target, int count) {
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.contains(WordUtils.capitalizeFully(target)) || ore.contains(target)) {
                for (ItemStack stack : OreDictionary.getOres(ore)) {
                    ItemStack newStack = stack.copy();
                    if (newStack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                        newStack.setItemDamage(0);
                    }
                    newStack.stackSize = count;
                    addAspectLoot(aspect, newStack);
                }
            }
        }
    }

    public static void addAspectLoot(Aspect aspect, ItemStack stack, int power) {
        if (stack == null || stack.getItem() == null) {
            return;
        }

        lootMap.putIfAbsent(aspect, new ArrayList<>());
        lootMap.get(aspect).add(new LootEntry(stack.copy(), power));
    }

    public static Map<Aspect, List<LootEntry>> getAllLoot() {
        return lootMap;
    }

    public static void populateLootMap() {
        for (Aspect a : Aspect.aspects.values()) {
            lootMap.put(a, new ArrayList<>());
        }
        addAspectLoot(
                Aspect.AIR,
                new ItemStack(
                        ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class),
                        1,
                        ItemInfusedGrain.getMetaForAspect(Aspect.AIR)));
        addAspectLoot(
                Aspect.FIRE,
                new ItemStack(
                        ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class),
                        1,
                        ItemInfusedGrain.getMetaForAspect(Aspect.FIRE)));
        addAspectLoot(
                Aspect.EARTH,
                new ItemStack(
                        ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class),
                        1,
                        ItemInfusedGrain.getMetaForAspect(Aspect.EARTH)));
        addAspectLoot(
                Aspect.WATER,
                new ItemStack(
                        ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class),
                        1,
                        ItemInfusedGrain.getMetaForAspect(Aspect.WATER)));

        addAspectLoot(Aspect.ORDER, new ItemStack(Blocks.glass, 16));
        addAspectLoot(Aspect.ENTROPY, new ItemStack(Blocks.sand, 16));

        addAspectLoot(Aspect.ELDRITCH, new ItemStack(Items.ender_pearl, 4), 10);
        addAspectLoot(Aspect.ELDRITCH, new ItemStack(Items.ender_eye, 4), 5);
        addAspectLoot(Aspect.ELDRITCH, "bucketEnder");

        addAspectLoot(Aspect.TREE, "logWood");

        for (Aspect tag : Aspect.aspects.values()) {
            ItemStack i = new ItemStack(ConfigItems.itemWispEssence, 1, 0);
            ((ItemWispEssence) ConfigItems.itemWispEssence).setAspects(i, new AspectList().add(tag, 2));
            addAspectLoot(Aspect.AURA, i);
        }

        addAspectLoot(Aspect.MIND, new ItemStack(Items.paper, 64), 15);
        addAspectLoot(Aspect.MIND, new ItemStack(Items.book, 32), 10);
        addAspectLoot(Aspect.MIND, new ItemStack(Blocks.bookshelf, 16), 5);
        addAspectLoot(Aspect.MIND, new ItemStack(ConfigItems.itemResource, 4, 9), 5);

        addAspectLoot(Aspect.FLESH, new ItemStack(ConfigItems.itemResource, 16, 5), 1);

        addAspectLoot(Aspect.UNDEAD, new ItemStack(Items.rotten_flesh, 32));
        addAspectLoot(Aspect.UNDEAD, new ItemStack(Items.bone, 24));

        addAspectLoot(
                Aspect.CRAFT,
                new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), 32));
        addAspectLoot(Aspect.CRAFT, new ItemStack(ConfigBlocks.blockStoneDevice, 16));
        addAspectLoot(Aspect.CRAFT, new ItemStack(Blocks.crafting_table, 4));

        addAspectLoot(Aspect.HUNGER, new ItemStack(Items.nether_wart, 16));

        addAspectLoot(Aspect.COLD, new ItemStack(Blocks.ice, 2));
        addAspectLoot(Aspect.COLD, new ItemStack(Items.snowball, 64));
        addAspectLoot(Aspect.COLD, new ItemStack(Blocks.snow, 32));
        addAspectLoot(Aspect.COLD, new ItemStack(Blocks.snow_layer, 8));

        addAspectLoot(Aspect.PLANT, "grass");
        addAspectLoot(Aspect.PLANT, "sapling");

        for (int i = 0; i < 12; i++) {
            addAspectLoot(Aspect.MAN, new ItemStack(ConfigItems.itemGolemCore, 1, i));
        }

        addAspectLoot(Aspect.ARMOR, new ItemStack(Items.diamond_boots));
        addAspectLoot(Aspect.ARMOR, new ItemStack(Items.diamond_leggings));
        addAspectLoot(Aspect.ARMOR, new ItemStack(Items.diamond_chestplate));
        addAspectLoot(Aspect.ARMOR, new ItemStack(Items.diamond_helmet));

        addAspectLoot(Aspect.MINE, new ItemStack(ConfigItems.itemPickThaumium));
        addAspectLoot(Aspect.HARVEST, new ItemStack(ConfigItems.itemHoeThaumium));
        addAspectLoot(Aspect.WEAPON, new ItemStack(ConfigItems.itemSwordThaumium));
        addAspectLoot(Aspect.TOOL, new ItemStack(ConfigItems.itemShovelThaumium));
        addAspectLoot(Aspect.TOOL, new ItemStack(ConfigItems.itemAxeThaumium));

        addAspectLoot(Aspect.SLIME, "slime", 4);

        addAspectLoot(Aspect.GREED, new ItemStack(Items.gold_ingot, 4));
        addAspectLoot(Aspect.GREED, new ItemStack(Items.emerald, 2));

        addAspectLoot(Aspect.LIGHT, new ItemStack(Items.glowstone_dust, 16), 5);
        addAspectLoot(Aspect.LIGHT, new ItemStack(ConfigItems.itemResource, 4, 1));
        addAspectLoot(
                Aspect.LIGHT,
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)));

        addAspectLoot(Aspect.MECHANISM, new ItemStack(Blocks.piston, 8));
        addAspectLoot(Aspect.MECHANISM, new ItemStack(Blocks.hopper, 4));
        addAspectLoot(Aspect.MECHANISM, new ItemStack(Blocks.dropper, 8));
        addAspectLoot(Aspect.MECHANISM, new ItemStack(Blocks.dispenser, 8));

        addAspectLoot(Aspect.CROP, new ItemStack(Items.wheat, 32));
        addAspectLoot(Aspect.CROP, "seed");

        addAspectLoot(Aspect.METAL, new ItemStack(Items.iron_ingot, 4), 100);

        addAspectLoot(Aspect.DEATH, new ItemStack(Items.bone, 32));

        addAspectLoot(Aspect.MOTION, new ItemStack(Blocks.rail), 10);

        addAspectLoot(Aspect.MOTION, new ItemStack(Blocks.activator_rail));

        addAspectLoot(Aspect.CLOTH, new ItemStack(Blocks.wool, 16), 30);
        addAspectLoot(Aspect.CLOTH, new ItemStack(Items.string, 15), 10);
        for (int i = 0; i < 16; i++) {
            addAspectLoot(Aspect.CLOTH, new ItemStack(Blocks.wool, 4, i));
        }

        addAspectLoot(Aspect.EXCHANGE, "ingotCopper", 4);
        addAspectLoot(Aspect.EXCHANGE, new ItemStack(ConfigBlocks.blockCustomOre, 4));

        addAspectLoot(Aspect.ENERGY, new ItemStack(ConfigItems.itemResource, 12));

        addAspectLoot(Aspect.MAGIC, new ItemStack(ConfigItems.itemShard, 1, 0));
        addAspectLoot(Aspect.MAGIC, new ItemStack(ConfigItems.itemShard, 1, 1));
        addAspectLoot(Aspect.MAGIC, new ItemStack(ConfigItems.itemShard, 1, 2));
        addAspectLoot(Aspect.MAGIC, new ItemStack(ConfigItems.itemShard, 1, 3));
        addAspectLoot(Aspect.MAGIC, new ItemStack(ConfigItems.itemShard, 1, 4));
        addAspectLoot(Aspect.MAGIC, new ItemStack(ConfigItems.itemShard, 1, 5));
        addAspectLoot(Aspect.MAGIC, new ItemStack(ConfigItems.itemShard, 1, 6));

        addAspectLoot(Aspect.HEAL, new ItemStack(Items.golden_apple));
        addAspectLoot(Aspect.HEAL, new ItemStack(Items.cake));
        addAspectLoot(Aspect.HEAL, new ItemStack(Items.potionitem, 1, 8261));

        for (int i = 0; i < 16; i++) {
            addAspectLoot(Aspect.SENSES, new ItemStack(Items.dye, 1, i));
            addAspectLoot(Aspect.SENSES, new ItemStack(Blocks.stained_glass, 1, i));
            addAspectLoot(Aspect.SENSES, new ItemStack(Blocks.stained_glass_pane, 2, i));
        }

        addAspectLoot(Aspect.SOUL, new ItemStack(Blocks.soul_sand, 16), 2);

        addAspectLoot(Aspect.WEATHER, "cloud", 64);

        addAspectLoot(Aspect.DARKNESS, new ItemStack(Blocks.obsidian, 10));

        addAspectLoot(Aspect.VOID, new ItemStack(Items.bucket));
        addAspectLoot(Aspect.VOID, "bucket");
        addAspectLoot(Aspect.VOID, "bowl");
        addAspectLoot(Aspect.VOID, new ItemStack(ConfigItems.itemResource, 1, 17)); // void seed

        addAspectLoot(Aspect.POISON, new ItemStack(ConfigItems.itemResource, 16, 3));
        addAspectLoot(Aspect.POISON, new ItemStack(Items.spider_eye, 8, 1));

        addAspectLoot(Aspect.LIFE, new ItemStack(Items.egg, 8));

        addAspectLoot(Aspect.TRAP, new ItemStack(Blocks.web, 4));
        addAspectLoot(Aspect.TRAP, "trapdoorWood");
        addAspectLoot(Aspect.TRAP, new ItemStack(ConfigItems.itemResource, 16, 6)); // amber

        addAspectLoot(Aspect.TRAVEL, new ItemStack(ConfigBlocks.blockCosmeticSolid, 4, 2));

        addAspectLoot(Aspect.TAINT, new ItemStack(ConfigItems.itemResource, 4, 11));

        addAspectLoot(Aspect.CRYSTAL, new ItemStack(Items.diamond, 1));
        addAspectLoot(Aspect.CRYSTAL, new ItemStack(Items.quartz, 1));
        addAspectLoot(Aspect.CRYSTAL, new ItemStack(Blocks.glass, 32), 4);
    }
}
