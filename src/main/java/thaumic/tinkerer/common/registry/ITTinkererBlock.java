package thaumic.tinkerer.common.registry;

import java.util.ArrayList;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by localmacaccount on 6/11/14.
 */
public interface ITTinkererBlock extends ITTinkererRegisterable {

    ArrayList<Object> getSpecialParameters();

    String getBlockName();

    boolean shouldRegister();

    boolean shouldDisplayInTab();

    Class<? extends ItemBlock> getItemBlock();

    Class<? extends TileEntity> getTileEntity();
}
