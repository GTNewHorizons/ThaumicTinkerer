package thaumic.tinkerer.common.block;

import static thaumic.tinkerer.common.lib.LibBlockNames.METALLURGIC_INFUSER;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.block.tile.TileMetallurgicInfuser;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

public class BlockMetallurgicInfuser extends BlockModContainer {

    IIcon iconBottom;
    IIcon iconTop;
    IIcon iconSides;

    protected BlockMetallurgicInfuser(Material par2Material) {
        super(Material.iron);
        setHardness(5F);
        setResistance(10F);
    }

    public BlockMetallurgicInfuser() {
        super(Material.iron);

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
        return new TileMetallurgicInfuser();
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return METALLURGIC_INFUSER;
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
