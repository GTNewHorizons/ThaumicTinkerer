package thaumic.tinkerer.common.potion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockForcefield;

/**
 * Created by pixlepix on 4/19/14.
 */
public final class PotionEffectHandlerServer {

    private final HashMap<Entity, Long> airPotionHitServer = new HashMap<>();
    private final HashMap<Entity, Long> firePotionHitServer = new HashMap<>();

    @SubscribeEvent
    public void onLivingHurt(LivingAttackEvent e) {
        if (e.source.getSourceOfDamage() instanceof EntityPlayer p && !p.worldObj.isRemote) {
            if (p.isPotionActive(ModPotions.potionAir)) {
                airPotionHitServer.put(e.entity, e.entity.worldObj.getTotalWorldTime());
            }
            if (p.isPotionActive(ModPotions.potionFire)) {
                firePotionHitServer.put(e.entity, e.entity.worldObj.getTotalWorldTime());
            }
            if (p.isPotionActive(ModPotions.potionEarth)) {
                boolean xAxis = Math.abs(e.entity.posZ - p.posZ) < Math.abs(e.entity.posX - p.posX);
                int centerX = (int) ((e.entity.posX + p.posX) / 2);

                int centerY = (int) (p.posY + 2);
                int centerZ = (int) ((e.entity.posZ + p.posZ) / 2);

                for (int i = -2; i < 3; i++) {
                    for (int j = -2; j < 3; j++) {
                        if (xAxis) {
                            if (p.worldObj.isAirBlock(centerX, centerY + i, centerZ + j)) {
                                p.worldObj.setBlock(
                                        centerX,
                                        centerY + i,
                                        centerZ + j,
                                        ThaumicTinkerer.registry.getFirstBlockFromClass(BlockForcefield.class));
                            }
                        } else {
                            if (p.worldObj.isAirBlock(centerX + j, centerY + i, centerZ)) {
                                p.worldObj.setBlock(
                                        centerX + j,
                                        centerY + i,
                                        centerZ,
                                        ThaumicTinkerer.registry.getFirstBlockFromClass(BlockForcefield.class));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.player.isPotionActive(ModPotions.potionWater)) {
            for (int x = (int) (e.player.posX - 2); x < e.player.posX + 2; x++) {
                for (int y = (int) (e.player.posY - 2); y < e.player.posY + 2; y++) {
                    for (int z = (int) (e.player.posZ - 2); z < e.player.posZ + 2; z++) {
                        if (e.player.worldObj.getBlock(x, y, z) == Blocks.lava
                                || e.player.worldObj.getBlock(x, y, z) == Blocks.flowing_lava) {
                            e.player.worldObj.setBlock(x, y, z, Blocks.obsidian);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTickServer(TickEvent.ServerTickEvent e) {

        Iterator<Entity> iter = airPotionHitServer.keySet().iterator();
        while (iter.hasNext()) {
            Entity target = iter.next();
            if (target.isEntityAlive()) {
                if (target.worldObj.getTotalWorldTime() % 5 == 0) {
                    Random rand = new Random();
                    target.setVelocity(rand.nextFloat() - .5, rand.nextFloat(), rand.nextFloat() - .5);
                }
            }
            if (target.worldObj.getTotalWorldTime() > airPotionHitServer.get(target) + 20) {
                iter.remove();
            }
        }

        // Fire Potion
        iter = firePotionHitServer.keySet().iterator();
        while (iter.hasNext()) {
            Entity target = iter.next();
            if (target.isEntityAlive()) {
                if (target.worldObj.getTotalWorldTime() % 5 == 0) {
                    target.setFire(6);
                }
            }
            if (target.worldObj.getTotalWorldTime() > firePotionHitServer.get(target) + 6000) {
                iter.remove();
            }
        }
    }
}
