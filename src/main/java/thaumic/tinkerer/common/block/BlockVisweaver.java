package thaumic.tinkerer.common.block;

import static thaumic.tinkerer.common.lib.LibBlockNames.VISWEAVER;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.api.VisweaverRecipeMap;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.TileVisweaver;
import thaumic.tinkerer.common.lib.LibGuiIDs;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

public class BlockVisweaver extends BlockModContainer {

    IIcon iconBottom;
    IIcon iconTop;
    IIcon iconSides;

    public BlockVisweaver() {
        super(Material.iron);
        setHardness(5F);
        setResistance(10F);

        VisweaverRecipeMap.putRecipe(25, Aspect.FIRE, new ItemStack(Items.bed), new ItemStack(Items.spider_eye));
        VisweaverRecipeMap.putRecipe(50, Aspect.EARTH, new ItemStack(Items.brick), new ItemStack(Items.netherbrick));
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return TileVisweaver.class;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer,
            int par6, float par7, float par8, float par9) {
        if (!par1World.isRemote) {
            TileEntity tile = par1World.getTileEntity(par2, par3, par4);
            if (tile != null) {
                par1World.markBlockForUpdate(par2, par3, par4);
                par5EntityPlayer
                        .openGui(ThaumicTinkerer.instance, LibGuiIDs.GUI_ID_VISWEAVER, par1World, par2, par3, par4);
            }
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        iconBottom = IconHelper.forBlock(par1IconRegister, this, 0);
        iconTop = IconHelper.forBlock(par1IconRegister, this, 1);
        iconSides = IconHelper.forBlock(par1IconRegister, this, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return par1 == ForgeDirection.UP.ordinal() ? iconTop
                : par1 == ForgeDirection.DOWN.ordinal() ? iconBottom : iconSides;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2) {
        return new TileVisweaver();
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return VISWEAVER;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }
}
