package thaumic.tinkerer.mixins.early.renderer;

import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderBlocks.class)
public class MixinRenderBlocks {

    private BlockFire fireBlock = null;

    @Inject(method = "renderBlockFire(Lnet/minecraft/block/BlockFire;III)Z", at = @At("HEAD"))
    private void ttinker$getFireBlock(BlockFire block, int x, int t, int z, CallbackInfoReturnable<Boolean> cir) {
        fireBlock = block;
    }

    @Inject(method = "renderBlockFire(Lnet/minecraft/block/BlockFire;III)Z", at = @At("TAIL"))
    private void ttinker$clearFireBlock(BlockFire block, int x, int t, int z, CallbackInfoReturnable<Boolean> cir) {
        fireBlock = null;
    }

    /*
     * Use the block's canCatchFire method instead of the base BlockFire's - Fixes Inbued Fire burning on water.
     */
    @Redirect(
            method = "renderBlockFire(Lnet/minecraft/block/BlockFire;III)Z",
            at = @At(
                    value = "INVOKE",
                    remap = false,
                    target = "Lnet/minecraft/block/BlockFire;canCatchFire(Lnet/minecraft/world/IBlockAccess;IIILnet/minecraftforge/common/util/ForgeDirection;)Z"))
    private boolean ttinker$renderBlockFire(BlockFire instance, IBlockAccess world, int x, int y, int z,
            ForgeDirection face) {
        return (fireBlock != null ? fireBlock : instance).canCatchFire(world, x, y, z, face);
    }
}
