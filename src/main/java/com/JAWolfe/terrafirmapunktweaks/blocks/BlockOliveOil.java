package com.JAWolfe.terrafirmapunktweaks.blocks;

import com.bioxx.tfc.Blocks.Liquids.BlockCustomLiquid;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class BlockOliveOil extends BlockCustomLiquid
{
	public BlockOliveOil(Fluid fluid)
	{
		super(fluid, Material.water);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		icons = new IIcon[]{getFluid().getStillIcon(), getFluid().getFlowingIcon()};
	}
}
