package thaumic.tinkerer.common.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import thaumcraft.api.ThaumcraftApi;

public class EMTCompat {
    public static final boolean EMTLoaded = Loader.isModLoaded("EMT");
    public static final Item electricHoe = GameRegistry.findItem("EMT", "ElectricHoeGrowth");
}
