package thaumic.tinkerer.common.compat;

import net.minecraft.item.Item;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class EMTCompat {

    public static final boolean EMTLoaded = Loader.isModLoaded("EMT");
    public static final Item electricHoe = GameRegistry.findItem("EMT", "ElectricHoeGrowth");
}
