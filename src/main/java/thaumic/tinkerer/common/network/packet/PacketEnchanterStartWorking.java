/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [16 Sep 2013, 21:49:24 (GMT)]
 */
package thaumic.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.container.ContainerEnchanter;

public class PacketEnchanterStartWorking implements IMessage, IMessageHandler<PacketEnchanterStartWorking, IMessage> {

    public PacketEnchanterStartWorking() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(PacketEnchanterStartWorking message, MessageContext ctx) {
        final var player = ctx.getServerHandler().playerEntity;
        if (!(player.openContainer instanceof ContainerEnchanter container)) return null;
        final var tile = container.enchanter;

        if (!tile.working && !tile.enchantments.isEmpty() && !tile.levels.isEmpty()) {
            tile.working = true;
            tile.markDirty();
            tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
        }
        return null;
    }
}
