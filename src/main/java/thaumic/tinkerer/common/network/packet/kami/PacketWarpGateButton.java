/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 5:59:27 PM (GMT)]
 */
package thaumic.tinkerer.common.network.packet.kami;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.container.kami.ContainerWarpGate;
import thaumic.tinkerer.common.block.tile.kami.TileWarpGate;

public class PacketWarpGateButton implements IMessage, IMessageHandler<PacketWarpGateButton, IMessage> {

    boolean locked;

    public PacketWarpGateButton() {}

    public PacketWarpGateButton(TileWarpGate tile) {
        locked = tile.locked;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(locked);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        locked = byteBuf.readBoolean();
    }

    @Override
    public IMessage onMessage(PacketWarpGateButton message, MessageContext ctx) {
        final var player = ctx.getServerHandler().playerEntity;
        if (player.openContainer instanceof ContainerWarpGate container) {
            container.gate.setLocked(locked);
        }
        return null;
    }
}
