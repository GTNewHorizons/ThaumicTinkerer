package thaumic.tinkerer.common.block.tile;

import net.minecraft.tileentity.TileEntity;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;

public class TileMetallurgicInfuser extends TileEntity {

    @Override
    public void updateEntity() {
        System.out.println(
                "drained "
                        + VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.FIRE, 1)
                        + " from the vis network");
    }
}
