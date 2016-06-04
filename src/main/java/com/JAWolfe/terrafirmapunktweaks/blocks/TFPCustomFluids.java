package com.JAWolfe.terrafirmapunktweaks.blocks;

import java.util.Random;

import com.JAWolfe.terrafirmapunktweaks.TFPFluids;
import com.JAWolfe.terrafirmapunktweaks.reference.References;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import steamcraft.common.lib.ModInfo;

public class TFPCustomFluids extends BlockDynamicLiquid implements IFluidBlock
{
	protected Fluid fluidType;
	protected IIcon[] icons;

	protected TFPCustomFluids(Fluid fluid, Material material) 
	{
		super(material);
		fluidType = fluid;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{	
		if(this.getFluid() == TFPFluids.tfpFluidBlood)
		{
			this.getFluid().setIcons(register.registerIcon(References.ModID + ":blood_still"),
									 register.registerIcon(References.ModID + ":blood_flow"));
		}
		else if(this.getFluid() == TFPFluids.tfpFluidWhaleOil)
		{
			this.getFluid().setIcons(register.registerIcon(ModInfo.PREFIX + "whaleoil"), 
									 register.registerIcon(ModInfo.PREFIX + "whaleoil_flow"));
		}
        
		icons = new IIcon[]{getFluid().getStillIcon(), getFluid().getFlowingIcon()};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side != 0 && side != 1 ? this.icons[1] : this.icons[0];
	}
	
	@Override
	public Fluid getFluid() 
	{
		return fluidType;
	}
	
	@Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) 
    {
        super.randomDisplayTick(world, x, y, z, rand);
        if(this.getFluid() == TFPFluids.tfpFluidBlood)
        {
	        if (rand.nextInt(10) == 0
	                && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
	                && !world.getBlock(x, y - 2, z).getMaterial().blocksMovement()) 
	        {
	            
	            double px = (double) ((float) x + rand.nextFloat());
	            double py = (double) y - 1.05D;
	            double pz = (double) ((float) z + rand.nextFloat());
	            world.spawnParticle("dripLava", px, py, pz, 0, 0, 0);
	        }
        }
    }

	@Override
	public FluidStack drain(World world, int x, int y, int z, boolean doDrain) 
	{
		return null;
	}

	@Override
	public boolean canDrain(World world, int x, int y, int z) 
	{
		return false;
	}

	@Override
	public float getFilledPercentage(World world, int x, int y, int z) 
	{
		return 1;
	}
}
