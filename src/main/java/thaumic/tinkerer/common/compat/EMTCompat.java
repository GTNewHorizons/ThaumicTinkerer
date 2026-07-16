package thaumic.tinkerer.common.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.ElectricItem;

public class EMTCompat {

    public static Item electricHoe;

    public static void init() {
        electricHoe = GameRegistry.findItem("EMT", "ElectricHoeGrowth");
    }

    public static boolean validElectricHoe(ItemStack stack) {
        return LoadedMods.EMTLoaded && stack.getItem() == EMTCompat.electricHoe
                && ElectricItem.manager.canUse(stack, 250);
    }

    public static void useEnergy(ItemStack hoeOfGrowth, EntityPlayer player) {
        ElectricItem.manager.use(hoeOfGrowth, 250, player);
    }
}
