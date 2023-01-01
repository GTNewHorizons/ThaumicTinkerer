package thaumic.tinkerer.common.dim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by pixlepix on 3/31/14.
 * <p/>
 * Numbers here used with permission of Vazkii
 */
public enum EnumOreFrequency {
    ALUMINUM("oreAluminum", 617),
    AMBER("oreAmber", 161),
    APATITE("oreApatite", 269),
    TOPAZ("oreTopaz", 238),
    SILICON("oreSilicon", 234),
    ILMENITE("oreIlmenite", 270),
    CINNABAR("oreCinnabar", 172),
    COAL("oreCoal", 2648),
    COPPER("oreCopper", 603),
    DIAMOND("oreDiamond", 67),
    EMERALD("oreEmerald", 48),
    TANZANITE("oreTanzanite", 61),
    GOLD("oreGold", 164),
    AIR("oreInfusedAir", 94),
    EARTH("oreInfusedEarth", 35),
    ENTROPY("oreInfusedEntropy", 53),
    FIRE("oreInfusedFire", 42),
    ORDER("oreInfusedOrder", 31),
    WATER("oreInfusedWater", 27),
    IRON("oreIron", 1503),
    LAPIS("oreLapis", 57),
    LEAD("oreLead", 335),
    // DRACONIUM("oreDraconium", 72),
    PERIDOT("orePeridot", 79),
    REDSTONE("oreRedstone", 364),
    RUBY("oreRuby", 57),
    // METEORICIRON("oreMeteoricIron", 86),
    SAPPHIRE("oreSapphire", 70),
    MALACHITE("oreMalachite", 416),
    DESH("oreDesh", 105),
    TIN("oreTin", 507),
    URANIUM("oreUranium", 112),
    // ELECTROTINE("oreElectrotine", 392),
    NETHERQUARTZ("oreNetherQuartz", 809),
    // CERTUS_QUARTZ("oreCertusQuartz", 234),
    CHARGED_CERTUS_QUARTZ("oreChargedCertusQuartz", 117),
    CHEESE("oreCheese", 1024);
    // NICKEL("oreNickel", 144),
    // SULFUR("oreSulfur", 288),
    // COBALT("oreCobalt", 96),
    // ORIHARUKON("oreOriharukon", 44),
    // TUNGSTEN("oreTungsten", 22),
    // MAGNESIUM("oreMagnesium", 96),
    // ANDAMANTIUM("oreAdamantium", 11),
    // PLATINUM("orePlatinum", 33),
    // MITHRIL("oreMithril", 44);

    public int freq;
    public String name;

    EnumOreFrequency(String name, int freq) {
        this.name = name;
        this.freq = freq;
    }

    public static int getSum() {
        int total = 0;
        for (EnumOreFrequency e : EnumOreFrequency.values()) {
            if (e.isValid()) {
                total += e.freq;
            }
        }
        return total;
    }

    public static ArrayList<EnumOreFrequency> getValidOres() {
        ArrayList<EnumOreFrequency> result = new ArrayList<EnumOreFrequency>();
        for (EnumOreFrequency e : EnumOreFrequency.values()) {
            if (e.isValid()) {
                result.add(e);
            }
        }
        return result;
    }

    public static ItemStack getRandomOre(Random rand) {
        int randInt = rand.nextInt(getSum());

        for (EnumOreFrequency e : getValidOres()) {
            randInt -= e.freq;
            if (randInt < 0) {
                return OreDictionary.getOres(e.name).get(0);
            }
        }
        return new ItemStack(Blocks.iron_ore);
    }

    public boolean isValid() {
        return !Arrays.asList(OreClusterGenerator.blacklist).contains(name)
                && !OreDictionary.getOres(name).isEmpty()
                && OreDictionary.getOres(name).get(0).getItem() instanceof ItemBlock;
    }
}
