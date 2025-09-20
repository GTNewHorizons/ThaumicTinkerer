package thaumic.tinkerer.mixins.early;

import net.minecraft.server.management.ItemInWorldManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemInWorldManager.class)
public interface AccessorItemInWorldManager {

    @Accessor
    int getDurabilityRemainingOnBlock();
}
