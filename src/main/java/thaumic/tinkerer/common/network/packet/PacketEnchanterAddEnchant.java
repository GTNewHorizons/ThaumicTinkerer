/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [16 Sep 2013, 15:45:54 (GMT)]
 */
package thaumic.tinkerer.common.network.packet;

import net.minecraft.enchantment.Enchantment;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.container.ContainerEnchanter;
import thaumic.tinkerer.common.enchantment.core.EnchantmentManager;

public class PacketEnchanterAddEnchant implements IMessage, IMessageHandler<PacketEnchanterAddEnchant, IMessage> {

    int enchant;
    int level;

    public PacketEnchanterAddEnchant() {}

    public PacketEnchanterAddEnchant(int enchant, int level) {
        this.enchant = enchant;
        this.level = level;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(enchant);
        byteBuf.writeInt(level);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        enchant = byteBuf.readInt();
        level = byteBuf.readInt();
    }

    @Override
    public IMessage onMessage(PacketEnchanterAddEnchant message, MessageContext ctx) {
        final var player = ctx.getServerHandler().playerEntity;
        if (!(player.openContainer instanceof ContainerEnchanter container)) return null;
        final var tile = container.enchanter;

        if (tile.working) return null;

        if (message.level == -1) {
            int index = tile.enchantments.indexOf(message.enchant);
            if (index != -1) {
                tile.removeLevel(index);
                tile.removeEnchant(index);
            }
        } else {
            if (!tile.enchantments.contains(message.enchant)) {
                if (EnchantmentManager.canApply(
                        tile.getStackInSlot(0),
                        Enchantment.enchantmentsList[message.enchant],
                        tile.enchantments)
                        && EnchantmentManager.canEnchantmentBeUsed(
                                player.getGameProfile().getName(),
                                Enchantment.enchantmentsList[message.enchant])) {
                    tile.appendEnchant(message.enchant);
                    tile.appendLevel(1);
                }
            } else {
                int maxLevel = Enchantment.enchantmentsList[message.enchant].getMaxLevel();
                tile.setLevel(
                        tile.enchantments.indexOf(message.enchant),
                        Math.max(1, Math.min(maxLevel, message.level)));
            }
        }
        tile.updateAspectList();
        tile.markDirty();

        tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
        return null;
    }
}
