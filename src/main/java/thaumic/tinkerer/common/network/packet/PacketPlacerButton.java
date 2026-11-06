package thaumic.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.TileRPlacer;
import thaumic.tinkerer.common.block.tile.container.ContainerRemotePlacer;

/**
 * Created by nekosune on 30/06/14.
 */
public class PacketPlacerButton implements IMessage, IMessageHandler<PacketPlacerButton, IMessage> {

    public int blocks;

    public PacketPlacerButton() {}

    public PacketPlacerButton(TileRPlacer tile) {
        blocks = tile.blocks;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(blocks);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        blocks = byteBuf.readInt();
    }

    @Override
    public IMessage onMessage(PacketPlacerButton message, MessageContext ctx) {
        final var player = ctx.getServerHandler().playerEntity;
        if (!(player.openContainer instanceof ContainerRemotePlacer container)) return null;
        final var tile = container.placer;

        tile.setDistance(blocks);
        return null;
    }
}
