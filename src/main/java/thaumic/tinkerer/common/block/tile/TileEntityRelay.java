package thaumic.tinkerer.common.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import appeng.api.movable.IMovableTile;
import cpw.mods.fml.common.Optional;
import thaumic.tinkerer.common.ThaumicTinkerer;

@Optional.Interface(iface = "appeng.api.movable.IMovableTile", modid = "appliedenergistics2")
public class TileEntityRelay extends TileEntity implements IMovableTile {

    public boolean hasPartner;
    public int partnerX;
    public int partnerZ;

    public TileEntityRelay() {}

    public void verifyPartner() {
        TileEntity te = worldObj.getTileEntity(partnerX, yCoord, partnerZ);
        if (!(hasPartner && te instanceof TileEntityRelay
                && ((TileEntityRelay) te).partnerX == this.xCoord
                && ((TileEntityRelay) te).partnerZ == this.zCoord)) {
            hasPartner = false;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setBoolean("HasPartner", hasPartner);

        nbt.setInteger("PartnerX", partnerX);
        nbt.setInteger("PartnerZ", partnerZ);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        hasPartner = nbt.getBoolean("HasPartner");

        partnerX = nbt.getInteger("PartnerX");
        partnerZ = nbt.getInteger("PartnerZ");
    }

    @Override
    public void updateEntity() {

        verifyPartner();
        if (hasPartner) {
            int i = xCoord;
            int j;
            do {
                j = zCoord;
                do {
                    float xInc = 0;
                    float zInc = 0;
                    if (partnerZ - zCoord != 0) {
                        zInc = ((float) Math.copySign(.05, partnerZ - zCoord)) * (worldObj.getTotalWorldTime() % 20);
                    }

                    if (partnerX - xCoord != 0) {
                        xInc = ((float) Math.copySign(.05, partnerX - xCoord)) * (worldObj.getTotalWorldTime() % 20);
                    }
                    ThaumicTinkerer.tcProxy.sparkle(
                            (float) (0.5 + i + xInc),
                            (float) (yCoord + 0.5),
                            (float) (j + 0.5 + zInc),
                            xCoord < partnerX || zCoord > partnerX ? 2 : 14);
                    j += Math.copySign(1, partnerZ - zCoord);
                } while (j < partnerZ);
                i += Math.copySign(1, partnerX - xCoord);

            } while (i < partnerX);
        }
        if (worldObj.getTotalWorldTime() % 40 == 0) {
            checkForPartner();
        }
        int i = xCoord;
        if (hasPartner && worldObj.getTotalWorldTime() % 40 == 0) {
            do {

                int j = zCoord;
                do {
                    if (worldObj.getTileEntity(i, yCoord, j) instanceof TileEntityMobilizer) {
                        TileEntityMobilizer te = (TileEntityMobilizer) worldObj.getTileEntity(i, yCoord, j);
                        te.verifyRelay();
                        if (!te.linked) {
                            te.firstRelayX = xCoord;
                            te.firstRelayZ = zCoord;

                            te.secondRelayX = partnerX;
                            te.secondRelayZ = partnerZ;

                            te.linked = true;

                            if (xCoord != partnerX) {
                                te.movementDirection = ForgeDirection.EAST;
                            } else {
                                te.movementDirection = ForgeDirection.NORTH;
                            }
                        }
                    }

                    j += Math.copySign(1, partnerZ - zCoord);
                } while (j < partnerZ);
                i += Math.copySign(1, partnerX - xCoord);
            } while (i < partnerX);
        }
    }

    public void checkForPartner() {
        if (!hasPartner) {
            for (int i = -32; i < 32; i++) {
                if (i != 0) {
                    TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord + i);
                    setPartners(te);

                    te = worldObj.getTileEntity(xCoord + i, yCoord, zCoord);
                    setPartners(te);
                }
            }
        }
    }

    private void setPartners(TileEntity te) {
        if (te instanceof TileEntityRelay) {
            ((TileEntityRelay) te).partnerX = this.xCoord;
            ((TileEntityRelay) te).partnerZ = this.zCoord;
            this.partnerX = te.xCoord;
            this.partnerZ = te.zCoord;

            this.hasPartner = true;
            ((TileEntityRelay) te).hasPartner = true;
        }
    }

    @Override
    @Optional.Method(modid = "appliedenergistics2")
    public boolean prepareToMove() {
        return true;
    }

    @Override
    @Optional.Method(modid = "appliedenergistics2")
    public void doneMoving() {}
}
