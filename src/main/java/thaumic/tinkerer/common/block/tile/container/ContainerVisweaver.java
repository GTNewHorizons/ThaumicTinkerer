package thaumic.tinkerer.common.block.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import thaumic.tinkerer.common.block.tile.TileVisweaver;

public class ContainerVisweaver extends ContainerPlayerInv {

    TileVisweaver visweaver;

    public ContainerVisweaver(TileVisweaver visweaver, InventoryPlayer playerInv) {
        super(playerInv);
        this.visweaver = visweaver;

        addSlotToContainer(new Slot(visweaver, 0, 20, 30));
        addSlotToContainer(new Slot(visweaver, 1, 140, 30));

        initPlayerInv();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            itemstack = stackInSlot.copy();

            if (index == 0 || index == 1) {
                if (!this.mergeItemStack(stackInSlot, 2, 38, true)) {
                    return null;
                }
                slot.onSlotChange(stackInSlot, itemstack);
            }
            else {
                if (!this.mergeItemStack(stackInSlot, 0, 1, false)) {
                    return null;
                }
            }

            if (stackInSlot.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
            if (stackInSlot.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, stackInSlot);
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return visweaver.isUseableByPlayer(player);
    }
}
