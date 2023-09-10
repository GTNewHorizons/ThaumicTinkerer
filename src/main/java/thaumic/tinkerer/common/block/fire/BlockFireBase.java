package thaumic.tinkerer.common.block.fire;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.item.ItemBlockFire;
import thaumic.tinkerer.common.registry.ITTinkererBlock;

public abstract class BlockFireBase extends BlockFire implements ITTinkererBlock {

    public static final String LARGESMOKE = "largesmoke";
    private IIcon[] icons;
    private IIcon itemIcon;

    private static final Random random = new Random();

    public abstract HashMap<Block, Block> getBlockTransformation();

    public boolean isTransmutationTarget(Block block, IBlockAccess w, int x, int y, int z) {
        return getBlockTransformation(w, x, y, z).containsKey(block);
    }

    public boolean isTransmutationResult(Block block, IBlockAccess w, int x, int y, int z) {
        return getBlockTransformation(w, x, y, z).containsValue(block);
    }

    public abstract HashMap<Block, Block> getBlockTransformation(IBlockAccess w, int x, int y, int z);

    public boolean isNeighborTarget(World w, int x, int y, int z) {
        for (final ForgeDirection f : ForgeDirection.VALID_DIRECTIONS) {
            final int xTar = x + f.offsetX;
            final int yTar = y + f.offsetY;
            final int zTar = z + f.offsetZ;
            if (!w.blockExists(xTar, yTar, zTar)) continue;

            if (isTransmutationTarget(w.getBlock(xTar, yTar, zTar), w, xTar, yTar, zTar)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int tickRate(World p_149738_1_) {
        return ConfigHandler.enableFireMechanics ? 200 : 0x7ffffff0;
    }

    public void setBlockWithTransmutationTarget(World world, int x, int y, int z, int meta, Block block) {
        final Block targetBlock = world.getBlock(x, y, z);
        if (isTransmutationTarget(targetBlock, world, x, y, z)) {
            final Block targetTransform = getBlockTransformation(world, x, y, z).get(targetBlock);
            world.setBlock(x, y, z, targetTransform, 0, 3);
        } else {
            world.setBlock(x, y, z, block, meta, 3);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {

        if (!(world.getGameRules().getGameRuleBooleanValue("doFireTick") && ConfigHandler.enableFire
                && ConfigHandler.enableFireMechanics)) {
            return;
        }

        if (world.isRaining() && (world.canLightningStrikeAt(x, y, z) || world.canLightningStrikeAt(x - 1, y, z)
                || world.canLightningStrikeAt(x + 1, y, z)
                || world.canLightningStrikeAt(x, y, z - 1)
                || world.canLightningStrikeAt(x, y, z + 1))) {
            // Check if it's raining and if it can be put out by rain
            world.setBlockToAir(x, y, z);
            return;
        }
        final int blockMeta = world.getBlockMetadata(x, y, z);

        // Source block doesn't burn out
        if (blockMeta != 0 && !isNeighborTarget(world, x, y, z)) {
            // Extinguish if there's no valid fuel around
            world.setBlockToAir(x, y, z);
            return;

        }

        // Check Transforms
        if (rand.nextInt(20) == 0 && (isNeighborTarget(world, x, y, z))) {
            for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                final int xT = x + dir.offsetX;
                final int yT = y + dir.offsetY;
                final int zT = z + dir.offsetZ;
                final Block targetBlock = world.getBlock(xT, yT, zT);

                final Block targetTransform = getBlockTransformation(world, xT, yT, zT).get(targetBlock);
                if (targetTransform == null) continue;

                world.setBlock(xT, yT, zT, targetTransform, 0, 3);
            }
        }

        if (blockMeta < 15 && blockMeta > 0) {
            // Randomly increase the age of a fire
            // 0 meta is considered a source and never increases, for any other meta we have a chance to increase
            final int newMeta = Math.min(blockMeta + rand.nextInt(3) / 2, 15);
            if (newMeta > blockMeta) {
                world.setBlockMetadataWithNotify(x, y, z, newMeta, 4);
            }
        } else if (blockMeta >= 15) {
            // No spreading if we're at max meta
            return;
        }

        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + rand.nextInt(3));

        final boolean highHumidity = world.isBlockHighHumidity(x, y, z);
        byte strMod = 0;

        if (highHumidity) {
            strMod = -50;
        }

        // Spread
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            this.tryCatchFire(
                    world,
                    x + dir.offsetX,
                    y + dir.offsetY,
                    z + dir.offsetZ,
                    300 + strMod,
                    rand,
                    blockMeta,
                    dir);
        }

        for (int xt = x - 1; xt <= x + 1; xt++) {
            for (int zt = z - 1; zt <= z + 1; zt++) {
                for (int yt = y - 1; yt <= y + 2; yt++) {
                    if (xt == x && yt == y && zt == z) {
                        continue;
                    }
                    int fireChance = this.getChanceOfNeighborsEncouragingFire(world, xt, yt, zt);

                    if (fireChance <= 0) {
                        continue;
                    }
                    fireChance = ((fireChance + 70) / (blockMeta + 30)) + 70;
                    if (highHumidity) {
                        fireChance /= 2;
                    }

                    if (fireChance <= 0 || rand.nextInt(100) > fireChance
                            || (world.isRaining() && world.canLightningStrikeAt(xt, yt, zt))
                            || world.canLightningStrikeAt(xt - 1, yt, z)
                            || world.canLightningStrikeAt(xt + 1, yt, zt)
                            || world.canLightningStrikeAt(xt, yt, zt - 1)
                            || world.canLightningStrikeAt(xt, yt, zt + 1)) {
                        continue;
                    }
                    final int targetMeta = Math.min(blockMeta + 1, 15);
                    setBlockWithTransmutationTarget(world, xt, yt, zt, targetMeta, this);
                }
            }
        }
    }

    public int getBlockFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        final Block block = world.getBlock(x, y, z);
        if (isTransmutationTarget(block, world, x, y, z)) {
            return 100;
        }
        if (isTransmutationResult(block, world, x, y, z)) {
            return 0;
        }

        return world.getBlock(x, y, z).getFlammability(world, x, y, z, face);
    }

    private void tryCatchFire(World world, int x, int y, int z, int strength, Random rand, int meta,
            ForgeDirection dir) {
        final int blockFlammability = getBlockFlammability(world, x, y, z, dir);

        if (rand.nextInt(strength) >= blockFlammability) {
            return;
        }
        final boolean isTnt = world.getBlock(x, y, z) == Blocks.tnt;

        if (rand.nextInt(meta + 10) < 5 && !world.canLightningStrikeAt(x, y, z)) {
            // Newer fires have a higher chance of transmuting
            final int nextMeta = Math.max(Math.min(meta + rand.nextInt(5) / 4, 15), 1);
            setBlockWithTransmutationTarget(world, x, y, z, nextMeta, this);
        } else {
            setBlockWithTransmutationTarget(world, x, y, z, 0, Blocks.air);
        }

        if (isTnt) {
            Blocks.tnt.onBlockDestroyedByPlayer(world, x, y, z, 1);
        }
    }

    /**
     * Returns true if at least one block next to this one can burn.
     */
    private boolean canNeighborBurn(World world, int x, int y, int z) {
        return isNeighborTarget(world, x, y, z);
    }

    /**
     * Gets the highest chance of a neighbor block encouraging this block to catch fire
     */
    private int getChanceOfNeighborsEncouragingFire(World world, int x, int y, int z) {
        if (!world.isAirBlock(x, y, z)) {
            return 0;
        } else {
            if (isNeighborTarget(world, x, y, z)) {
                return 100;
            }
            return 0;
        }
    }

    /**
     * Checks the specified block coordinate to see if it can catch fire. Args: blockAccess, x, y, z
     */
    @Override
    @Deprecated
    public boolean canBlockCatchFire(IBlockAccess w, int x, int y, int z) {
        return canCatchFire(w, x, y, z, UP);
    }

    @Override
    @Deprecated
    public int func_149846_a(World w, int x, int y, int z, int chance) {
        return getChanceToEncourageFire(w, x, y, z, chance, UP);
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        if (entity instanceof EntityPlayer)
            ThaumicTinkerer.log.info("Player: " + ((EntityPlayer) entity).getDisplayName() + " placed TT Fire");
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World w, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canCatchFire(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return getBlockFlammability(world, x, y, z, face) > 0;
    }

    @Override
    public int getChanceToEncourageFire(IBlockAccess world, int x, int y, int z, int oldChance, ForgeDirection face) {
        final int newChance = world.getBlock(x, y, z).getFireSpreadSpeed(world, x, y, z, face);

        return (Math.max(newChance, oldChance));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && !this.canNeighborBurn(world, x, y, z)) {
            world.setBlockToAir(x, y, z);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.provider.dimensionId > 0 || !Blocks.portal.func_150000_e(world, x, y, z)) {
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + world.rand.nextInt(10));
        }
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(24) == 0) {
            world.playSound(
                    x + 0.5F,
                    y + 0.5F,
                    z + 0.5F,
                    "fire.fire",
                    1.0F + rand.nextFloat(),
                    rand.nextFloat() * 0.7F + 0.3F,
                    false);
        }

        int l;
        float f;
        float f1;
        float f2;

        if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
                && !Blocks.fire.canCatchFire(world, x, y - 1, z, UP)) {
            if (Blocks.fire.canCatchFire(world, x - 1, y, z, EAST)) {
                for (l = 0; l < 2; ++l) {
                    f = x + rand.nextFloat() * 0.1F;
                    f1 = y + rand.nextFloat();
                    f2 = z + rand.nextFloat();
                    world.spawnParticle(LARGESMOKE, f, f1, f2, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Blocks.fire.canCatchFire(world, x + 1, y, z, WEST)) {
                for (l = 0; l < 2; ++l) {
                    f = (x + 1) - rand.nextFloat() * 0.1F;
                    f1 = y + rand.nextFloat();
                    f2 = z + rand.nextFloat();
                    world.spawnParticle(LARGESMOKE, f, f1, f2, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Blocks.fire.canCatchFire(world, x, y, z - 1, SOUTH)) {
                for (l = 0; l < 2; ++l) {
                    f = x + rand.nextFloat();
                    f1 = y + rand.nextFloat();
                    f2 = z + rand.nextFloat() * 0.1F;
                    world.spawnParticle(LARGESMOKE, f, f1, f2, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Blocks.fire.canCatchFire(world, x, y, z + 1, NORTH)) {
                for (l = 0; l < 2; ++l) {
                    f = x + rand.nextFloat();
                    f1 = y + rand.nextFloat();
                    f2 = (z + 1) - rand.nextFloat() * 0.1F;
                    world.spawnParticle(LARGESMOKE, f, f1, f2, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Blocks.fire.canCatchFire(world, x, y + 1, z, DOWN)) {
                for (l = 0; l < 2; ++l) {
                    f = x + rand.nextFloat();
                    f1 = (y + 1) - rand.nextFloat() * 0.1F;
                    f2 = z + rand.nextFloat();
                    world.spawnParticle(LARGESMOKE, f, f1, f2, 0.0D, 0.0D, 0.0D);
                }
            }
        } else {
            for (l = 0; l < 3; ++l) {
                f = x + rand.nextFloat();
                f1 = y + rand.nextFloat() * 0.5F + 0.5F;
                f2 = z + rand.nextFloat();
                world.spawnParticle(LARGESMOKE, f, f1, f2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public MapColor getMapColor(int p_149728_1_) {
        return MapColor.tntColor;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return null;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        return ItemBlockFire.class;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[] { IconHelper.forName(iconRegister, this.getBlockName() + "_layer_0"),
                IconHelper.forName(iconRegister, this.getBlockName() + "_layer_1") };

        String s = "";
        if (this instanceof BlockFireAir) {
            s = "aer";
        }
        if (this instanceof BlockFireEarth) {
            s = "terra";
        }
        if (this instanceof BlockFireWater) {
            s = "aqua";
        }
        if (this instanceof BlockFireIgnis) {
            s = "ignis";
        }
        if (this instanceof BlockFireOrder) {
            s = "ordo";
        }
        if (this instanceof BlockFireChaos) {
            s = "perditio";
        }
        s += "Fire";
        itemIcon = IconHelper.forName(iconRegister, s);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getFireIcon(int p_149840_1_) {
        return this.icons[p_149840_1_];
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return itemIcon;
    }

}
