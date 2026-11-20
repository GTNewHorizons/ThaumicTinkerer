/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [12 Sep 2013, 18:44:52 (GMT)]
 */
package thaumic.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.TileMobMagnet;
import thaumic.tinkerer.common.block.tile.container.ContainerMobMagnet;

public class PacketMobMagnetButton implements IMessage, IMessageHandler<PacketMobMagnetButton, IMessage> {

    public boolean adult;

    public PacketMobMagnetButton() {}

    public PacketMobMagnetButton(TileMobMagnet tile) {
        adult = tile.adult;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(adult);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        adult = byteBuf.readBoolean();
    }

    @Override
    public IMessage onMessage(PacketMobMagnetButton message, MessageContext ctx) {
        final var player = ctx.getServerHandler().playerEntity;
        if (player.openContainer instanceof ContainerMobMagnet container) {
            container.mobMagnet.setAdult(adult);
        }
        return null;
    }
}
