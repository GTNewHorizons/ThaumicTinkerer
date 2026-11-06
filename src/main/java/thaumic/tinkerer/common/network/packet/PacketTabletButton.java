/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [9 Sep 2013, 17:02:18 (GMT)]
 */
package thaumic.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.container.ContainerAnimationTablet;
import thaumic.tinkerer.common.block.tile.tablet.TileAnimationTablet;

public class PacketTabletButton implements IMessage, IMessageHandler<PacketTabletButton, IMessage> {
    boolean leftClick, redstone;

    public PacketTabletButton() {}

    public PacketTabletButton(TileAnimationTablet tile) {
        leftClick = tile.leftClick;
        redstone = tile.redstone;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(leftClick);
        byteBuf.writeBoolean(redstone);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        leftClick = byteBuf.readBoolean();
        redstone = byteBuf.readBoolean();
    }

    @Override
    public IMessage onMessage(PacketTabletButton message, MessageContext ctx) {
        final var player = ctx.getServerHandler().playerEntity;
        if (!(player.openContainer instanceof ContainerAnimationTablet container)) return null;
        final var tile = container.tablet;

        tile.leftClick = message.leftClick;
        tile.redstone = message.redstone;
        tile.markDirty();
        tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);

        return null;
    }
}
