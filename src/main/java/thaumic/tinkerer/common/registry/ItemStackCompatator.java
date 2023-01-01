package thaumic.tinkerer.common.registry;

import java.util.Comparator;
import net.minecraft.item.ItemStack;

/**
 * Created by pixlepix on 8/3/14.
 * Used to sort ItemStacks alphabetically for the creative tab
 */
public class ItemStackCompatator implements Comparator<ItemStack> {
    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
