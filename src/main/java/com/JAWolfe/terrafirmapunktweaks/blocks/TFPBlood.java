package com.JAWolfe.terrafirmapunktweaks.blocks;

import java.util.Random;

import com.JAWolfe.terrafirmapunktweaks.reference.References;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class TFPBlood extends BlockFluidClassic
{
	protected IIcon[] icons;
	protected Fluid fluid;
	
	public TFPBlood(Fluid fluid)
	{
		super(fluid, Material.water);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side != 0 && side != 1 ? this.icons[1] : this.icons[0];
	}
	
	@Override
    public void registerBlockIcons(IIconRegister register) 
	{
		this.getFluid().setIcons(register.registerIcon(References.ModID + ":blood_still"), register.registerIcon(References.ModID + ":blood_flow"));
        icons = new IIcon[]{getFluid().getStillIcon(), getFluid().getFlowingIcon()};
    }
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		if(block.getMaterial() == this.getMaterial())
			return false;

		return super.shouldSideBeRendered(world, x, y, z, side);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess iblockaccess, int x, int y, int z)
    {
        return 0xD90000;
    }
	
	@Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) 
	{
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) 
            	return false;
            return super.canDisplace(world, x, y, z);
    }
    
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) 
    {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid())
            	return false;
            return super.displaceIfPossible(world, x, y, z);
    }
    
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) 
    {
        super.randomDisplayTick(world, x, y, z, rand);
        if (rand.nextInt(10) == 0
                && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
                && !world.getBlock(x, y - 2, z).getMaterial().blocksMovement()) {
            
            double px = (double) ((float) x + rand.nextFloat());
            double py = (double) y - 1.05D;
            double pz = (double) ((float) z + rand.nextFloat());
            world.spawnParticle("dripLava", px, py, pz, 0, 0, 0);
        }
    }
}
