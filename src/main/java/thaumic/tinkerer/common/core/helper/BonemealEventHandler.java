package thaumic.tinkerer.common.core.helper;

import static thaumic.tinkerer.common.compat.EMTCompat.EMTLoaded;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.block.BlockInfusedGrain;
import thaumic.tinkerer.common.compat.EMTCompat;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

/**
 * Created by pixlepix on 8/28/14.
 * <p/>
 * Prevents bonemeal being used on TT crops
 */
public class BonemealEventHandler {

    @SubscribeEvent
    public void onBonemeal(BonemealEvent event) {
        if (event.world.getBlock(event.x, event.y, event.z) instanceof BlockInfusedGrain) {
            if (!ConfigHandler.cropsAllowBonemeal) {
                event.setCanceled(true);
            }
        }
    }

    public static boolean isMagicHoe(ItemStack stack) {
        if (stack == null) return false;
        var item = stack.getItem();
        return item == ConfigItems.itemHoeElemental || (EMTLoaded && item == EMTCompat.electricHoe);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        var world = event.world;
        if (world.isRemote) return;
        if (!(world.getBlock(event.x, event.y, event.z) instanceof BlockInfusedGrain igrowable)) return;

        var player = event.entityPlayer;
        if (player == null) return;

        var hoeOfGrowth = player.getCurrentEquippedItem();
        if (!isMagicHoe(hoeOfGrowth)) return;

        if (igrowable.func_149851_a(world, event.x, event.y, event.z, false)) {
            if (igrowable.func_149852_a(world, world.rand, event.x, event.y, event.z)) {
                igrowable.func_149853_b(world, world.rand, event.x, event.y, event.z);
            }
        }

        hoeOfGrowth.damageItem(25, player);
        Thaumcraft.proxy.blockSparkle(world, event.x, event.y, event.z, 0, 2);
    }
}
