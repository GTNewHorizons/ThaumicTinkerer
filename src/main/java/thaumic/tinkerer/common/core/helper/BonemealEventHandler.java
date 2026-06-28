package thaumic.tinkerer.common.core.helper;

import static thaumic.tinkerer.common.compat.EMTCompat.EMTLoaded;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
        Item item = stack.getItem();
        return item == ConfigItems.itemHoeElemental || EMTCompat.validElectricHoe(stack);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        World world = event.world;
        if (world.isRemote) return;
        if (!(world.getBlock(event.x, event.y, event.z) instanceof BlockInfusedGrain infusedGrain)) return;

        EntityPlayer player = event.entityPlayer;
        if (player == null) return;

        ItemStack hoeOfGrowth = player.getCurrentEquippedItem();
        if (!isMagicHoe(hoeOfGrowth)) return;

        if (!infusedGrain.func_149851_a(world, event.x, event.y, event.z, false)
                || !infusedGrain.func_149852_a(world, world.rand, event.x, event.y, event.z)) {
            return;
        }
        infusedGrain.func_149853_b(world, world.rand, event.x, event.y, event.z);
        damageHoe(hoeOfGrowth, player);
        Thaumcraft.proxy.blockSparkle(world, event.x, event.y, event.z, 0, 2);
        world.playSoundEffect(
                event.x + 0.5D,
                event.y + 0.5D,
                event.z + 0.5D,
                "thaumcraft:wand",
                0.75F,
                0.9F + world.rand.nextFloat() * 0.2F);
    }

    private static void damageHoe(ItemStack hoeOfGrowth, EntityPlayer player) {
        if (EMTLoaded && hoeOfGrowth.getItem() == EMTCompat.electricHoe) {
            EMTCompat.useEnergy(hoeOfGrowth, player);
        } else {
            hoeOfGrowth.damageItem(25, player);
        }
    }
}
