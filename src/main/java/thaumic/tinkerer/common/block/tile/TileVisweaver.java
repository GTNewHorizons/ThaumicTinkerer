package thaumic.tinkerer.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;
import thaumic.tinkerer.api.VisweaverRecipe;
import thaumic.tinkerer.api.VisweaverRecipeMap;
import thaumic.tinkerer.common.lib.LibBlockNames;

public class TileVisweaver extends TileEntity implements IInventory {

    private static final String TAG_INTERNAL_VIS = "internalVis";

    private int tickCounter;
    private int internalVis = 0;
    private boolean working = false;
    private ItemStack currentOutput;
    private Aspect cvType;
    private int requiredVis;

    ItemStack[] inventorySlots = new ItemStack[2];

    @Override
    public void updateEntity() {
        tickCounter++;
        if (tickCounter % 20 != 0) return;
        if (!working) {
            VisweaverRecipe recipe = VisweaverRecipeMap.lookup(getStackInSlot(0));
            if (recipe != null) {
                working = true;
                cvType = recipe.getCentivisType();
                requiredVis = recipe.getCentivisCost();
                currentOutput = recipe.getOutput();
            }
        } else {
            internalVis += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, cvType, 5);
            if (internalVis >= requiredVis) {
                decrStackSize(0, 1);
                setInventorySlotContents(1, currentOutput);
                internalVis = 0;
                working = false;
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return inventorySlots.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIn) {
        return inventorySlots[slotIn];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (inventorySlots[i] != null) {
            ItemStack stackAt;

            if (inventorySlots[i].stackSize <= j) {
                stackAt = inventorySlots[i];
                inventorySlots[i] = null;
                return stackAt;
            } else {
                stackAt = inventorySlots[i].splitStack(j);

                if (inventorySlots[i].stackSize == 0) inventorySlots[i] = null;

                return stackAt;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return getStackInSlot(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack) {
        inventorySlots[i] = stack;
    }

    @Override
    public String getInventoryName() {
        return LibBlockNames.VISWEAVER;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
                && entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readCustomNBT(pkt.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);

        readCustomNBT(par1NBTTagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

        writeCustomNBT(par1NBTTagCompound);
    }

    @Override
    public S35PacketUpdateTileEntity getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeCustomNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbttagcompound);
    }

    public void readCustomNBT(NBTTagCompound par1NBTTagCompound) {
        internalVis = par1NBTTagCompound.getInteger(TAG_INTERNAL_VIS);

        NBTTagList var2 = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        inventorySlots = new ItemStack[getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < inventorySlots.length) inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4);
        }
    }

    public void writeCustomNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger(TAG_INTERNAL_VIS, internalVis);

        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < inventorySlots.length; ++var3) {
            if (inventorySlots[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                inventorySlots[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }
}
