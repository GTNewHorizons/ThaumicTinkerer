package thaumic.tinkerer.common.registry;

import net.minecraft.item.Item;

/**
 * Created by localmacaccount on 6/9/14.
 */
public interface ITTinkererItem extends ITTinkererRegisterable {

    String getItemName();

    boolean shouldRegister();

    boolean shouldDisplayInTab();

    default Item[] getMetaItems() {
        return null;
    }
}
