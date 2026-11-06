/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 7:21:05 PM (GMT)]
 */
package thaumic.tinkerer.common.network.packet.kami;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.kami.TileWarpGate;

public class PacketWarpGateTeleport implements IMessage, IMessageHandler<PacketWarpGateTeleport, IMessage> {

    int x, y, z;
    int index;

    public PacketWarpGateTeleport() {}

    public PacketWarpGateTeleport(TileWarpGate tile, int index) {
        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.index = index;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(y);
        byteBuf.writeInt(z);
        byteBuf.writeInt(index);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        x = byteBuf.readInt();
        y = byteBuf.readInt();
        z = byteBuf.readInt();
        index = byteBuf.readInt();
    }

    @Override
    public IMessage onMessage(PacketWarpGateTeleport message, MessageContext ctx) {
        final var player = ctx.getServerHandler().playerEntity;
        final var world = player.worldObj;

        if (world.blockExists(message.x, message.y, message.z)
                && world.getTileEntity(message.x, message.y, message.z) instanceof TileWarpGate warpGate
                && player.boundingBox.intersectsWith(warpGate.getTeleportRange().expand(0.1, 0.1, 0.1))) {
            warpGate.teleportPlayer(player, message.index);
        }

        return null;
    }
}
