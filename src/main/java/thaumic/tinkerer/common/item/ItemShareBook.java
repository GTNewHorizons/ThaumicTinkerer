package thaumic.tinkerer.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.research.PlayerKnowledge;
import thaumcraft.common.lib.research.ResearchManager;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.helper.ItemNBTHelper;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ItemBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

public class ItemShareBook extends ItemBase {

    private static final String TAG_PLAYER = "player";
    private static final String TAG_RESEARCH = "research";
    private static final String TAG_ASPECTS = "aspects";
    private static final String NON_ASSIGNED = "[none]";

    public ItemShareBook() {
        super();
        setMaxStackSize(1);
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        IRegisterableResearch research = (TTResearchItem) new TTResearchItem(
                LibResearch.KEY_SHARE_TOME,
                new AspectList(),
                0,
                -1,
                0,
                new ItemStack(this)).setStub().setAutoUnlock().setRound();
        if (ConfigHandler.enableSurvivalShareTome) ((TTResearchItem) research)
                .setPages(new ResearchPage("0"), ResearchHelper.recipePage(LibResearch.KEY_SHARE_TOME));
        else((TTResearchItem) research).setPages(new ResearchPage("0"));
        return research;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (ConfigHandler.enableSurvivalShareTome) {
            return new ThaumicTinkererCraftingBenchRecipe(
                    LibResearch.KEY_SHARE_TOME,
                    new ItemStack(this),
                    " S ",
                    "PTP",
                    " P ",
                    'S',
                    new ItemStack(ConfigItems.itemInkwell),
                    'T',
                    new ItemStack(ConfigItems.itemThaumonomicon),
                    'P',
                    new ItemStack(Items.paper));
        }
        return null;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack bookStack, World world, EntityPlayer player) {
        String name = getPlayerName(bookStack);
        if (name.endsWith(NON_ASSIGNED)) {
            setPlayerName(bookStack, player.getGameProfile().getName());
            savePlayerResearch(ItemNBTHelper.getNBT(bookStack), player.getGameProfile().getName());
            if (!world.isRemote) {
                player.addChatMessage(new ChatComponentTranslation("ttmisc.shareTome.write"));
            }
        } else {
            // Try to load latest research for the saved player, fall back to the stored NBT if it fails.
            final NBTTagCompound researchSource;
            if (ResearchManager.getResearchForPlayer(name) != null) {
                researchSource = new NBTTagCompound();
                savePlayerResearch(researchSource, name);
            } else {
                researchSource = ItemNBTHelper.getNBT(bookStack);
            }

            loadPlayerResearch(researchSource, player);

            if (!world.isRemote) {
                player.addChatMessage(new ChatComponentTranslation("ttmisc.shareTome.sync"));
            }
        }

        return bookStack;
    }

    private void loadPlayerResearch(NBTTagCompound cmp, EntityPlayer player) {
        // Ensure full TC player data gets loaded
        final String playerName = player.getGameProfile().getName();
        ResearchManager.getResearchForPlayer(playerName);
        if (cmp.hasKey(TAG_RESEARCH)) {
            final NBTTagList list = cmp.getTagList(TAG_RESEARCH, Constants.NBT.TAG_STRING);
            for (int i = 0; i < list.tagCount(); i++) {
                final String research = list.getStringTagAt(i);
                ThaumicTinkerer.tcProxy.getResearchManager().completeResearch(player, research);
            }
        }
        if (cmp.hasKey(TAG_ASPECTS)) {
            final PlayerKnowledge pk = ThaumicTinkerer.tcProxy.getPlayerKnowledge();
            final NBTTagList list = cmp.getTagList(TAG_ASPECTS, Constants.NBT.TAG_STRING);
            for (int i = 0; i < list.tagCount(); i++) {
                final String aspectTag = list.getStringTagAt(i);
                final Aspect aspect = Aspect.getAspect(aspectTag);
                if (aspect != null) {
                    pk.addDiscoveredAspect(playerName, aspect);
                }
            }
            ResearchManager.scheduleSave(player);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        String name = getPlayerName(par1ItemStack);
        par3List.add(
                name.equals(NON_ASSIGNED) ? StatCollector.translateToLocal("ttmisc.shareTome.noAssign")
                        : String.format(StatCollector.translateToLocal("ttmisc.shareTome.playerName"), name));
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    private String getPlayerName(ItemStack stack) {
        return ItemNBTHelper.getString(stack, TAG_PLAYER, NON_ASSIGNED);
    }

    private void setPlayerName(ItemStack stack, String playerName) {
        ItemNBTHelper.setString(stack, TAG_PLAYER, playerName);
    }

    private void savePlayerResearch(NBTTagCompound target, String playername) {
        // also loads the aspect list
        final List<String> researchesDone = ResearchManager.getResearchForPlayer(playername);
        // Save all unlocked research notes.
        final NBTTagList researchList = new NBTTagList();
        for (final String tag : researchesDone) {
            researchList.appendTag(new NBTTagString(tag));
        }
        target.setTag(TAG_RESEARCH, researchList);
        // Save all discovered aspect types.
        final PlayerKnowledge pk = Thaumcraft.proxy.getPlayerKnowledge();
        final NBTTagList aspectsToSave = new NBTTagList();
        final AspectList aspectsDiscovered = pk.getAspectsDiscovered(playername);
        if (aspectsDiscovered == null) {
            return;
        }
        aspectsDiscovered.aspects.keySet().stream().map(Aspect::getTag).sorted().map(NBTTagString::new)
                .forEach(aspectsToSave::appendTag);
        target.setTag(TAG_ASPECTS, aspectsToSave);
    }

    @Override
    public String getItemName() {
        return LibItemNames.SHARE_BOOK;
    }
}
