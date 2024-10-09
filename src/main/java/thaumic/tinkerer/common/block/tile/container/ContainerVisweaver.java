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
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            if (stackInSlot.stackSize == 0) slotObject.putStack(null);
            else slotObject.onSlotChanged();
        }

        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return visweaver.isUseableByPlayer(player);
    }
}
