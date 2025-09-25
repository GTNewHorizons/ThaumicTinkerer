package thaumic.tinkerer.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by localmacaccount on 6/11/14.
 */
public interface ITTinkererBlock extends ITTinkererRegisterable {

    String getBlockName();

    boolean shouldRegister();

    boolean shouldDisplayInTab();

    Class<? extends ItemBlock> getItemBlock();

    Class<? extends TileEntity> getTileEntity();

    default Block[] getMetaBlocks() {
        return null;
    }
}
