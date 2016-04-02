package com.JAWolfe.terrafirmapunktweaks.blocks;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.steamcraft.SteamcraftBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TFPBlocks 
{
	public static Block tweakedboiler;
	public static Block tweakedboilerOn;
	public static Block tweakedFlashBoiler;
	public static Block tfpBlood;
	public static Fluid tfpFluidBlood;
	
	public static void initialise()
	{
		if(Loader.isModLoaded("Steamcraft"))
		{
			tweakedboiler = new TweakedBoiler(false).setBlockName(SteamcraftBlocks.boiler.getUnlocalizedName().substring(5)).setHardness(5.0F).setResistance(10.0F);
			tweakedboilerOn = new TweakedBoiler(true).setBlockName(SteamcraftBlocks.boilerOn.getUnlocalizedName().substring(5)).setHardness(5.0F).setResistance(10.0F);
			tweakedFlashBoiler = new TweakedFlashBoiler().setBlockName(SteamcraftBlocks.flashBoiler.getUnlocalizedName().substring(5)).setHardness(5.0f).setResistance(10.0F);
			
			GameRegistry.registerBlock(tweakedboiler, "tweakedboiler");
			GameRegistry.registerBlock(tweakedboilerOn, "tweakedboilerOn");
			GameRegistry.registerBlock(tweakedFlashBoiler, "tweakedFlashBoiler");
		}
		
		if(Loader.isModLoaded("necromancy"))
		{
			tfpFluidBlood = new Fluid("tfpBlood");
			FluidRegistry.registerFluid(tfpFluidBlood);
			
			tfpBlood = new TFPBlood(tfpFluidBlood).setBlockName("tfpBlood");			
			GameRegistry.registerBlock(tfpBlood, "tfpBlood");
			tfpFluidBlood.setBlock(tfpBlood);
		}
	}
}
