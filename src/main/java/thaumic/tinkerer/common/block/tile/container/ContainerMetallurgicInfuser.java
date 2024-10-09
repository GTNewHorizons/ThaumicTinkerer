package thaumic.tinkerer.common.block.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import thaumic.tinkerer.common.block.tile.TileMetallurgicInfuser;

public class ContainerMetallurgicInfuser extends ContainerPlayerInv {

    TileMetallurgicInfuser metallurgicInfuser;

    public ContainerMetallurgicInfuser(TileMetallurgicInfuser metallurgicInfuser, InventoryPlayer playerInv) {
        super(playerInv);
        this.metallurgicInfuser = metallurgicInfuser;

        addSlotToContainer(new Slot(metallurgicInfuser, 0, 20, 30));
        addSlotToContainer(new Slot(metallurgicInfuser, 1, 140, 30));

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
        return metallurgicInfuser.isUseableByPlayer(player);
    }
}
