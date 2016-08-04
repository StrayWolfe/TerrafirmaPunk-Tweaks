package com.JAWolfe.terrafirmapunktweaks.blocks;

import com.JAWolfe.terrafirmapunktweaks.TFPFluids;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.Constant.Global;
import com.google.common.collect.ObjectArrays;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.steamcraft.SteamcraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TFPBlocks 
{
	
	public static Block tweakedboiler;
	public static Block tweakedboilerOn;
	public static Block tweakedFlashBoiler;
	public static Block[] tfpBrickOven;
	public static Block tfpBlood;
	public static Block oliveOil;
	public static Block tfpWhaleOil;
	public static Block tfpAltar;
	public static Block tfpAltarBlock;
	public static Block tfpLightningRod;
	public static Block blockWolframite;
	
	public static int brickOvenRenderID;
	
	public static void initialise()
	{
		String[] IgStone = ObjectArrays.concat(Global.STONE_IGEX, Global.STONE_IGIN, String.class);
		tfpBrickOven = new Block[IgStone.length];
		
		for (int i = 0; i < IgStone.length; i++)
		{
			tfpBrickOven[i] = new TFPBrickOven(i).setBlockName("TFPBrickOven " + IgStone[i]);
			GameRegistry.registerBlock(tfpBrickOven[i], "tfpBrickOven" + IgStone[i]);
		}
		
		oliveOil = new BlockOliveOil(TFCFluids.OLIVEOIL).setHardness(100.0F).setLightOpacity(3).setBlockName("OliveOil");
		GameRegistry.registerBlock(oliveOil,"OliveOil");
		
		blockWolframite = new BlockWolframite(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Wolframite");
		GameRegistry.registerBlock(blockWolframite, "OreWolferamite");
		
		if(Loader.isModLoaded("Steamcraft"))
		{
			tweakedboiler = new TweakedBoiler(false).setBlockName(SteamcraftBlocks.boiler.getUnlocalizedName().substring(5)).setHardness(5.0F).setResistance(10.0F);
			tweakedboilerOn = new TweakedBoiler(true).setBlockName(SteamcraftBlocks.boilerOn.getUnlocalizedName().substring(5)).setHardness(5.0F).setResistance(10.0F);
			tweakedFlashBoiler = new TweakedFlashBoiler().setBlockName(SteamcraftBlocks.flashBoiler.getUnlocalizedName().substring(5)).setHardness(5.0f).setResistance(10.0F);
			tfpLightningRod = new BlockTFPLigntningRod().setBlockName("tfpLightningRod");
			
			GameRegistry.registerBlock(tweakedboiler, "tweakedboiler");
			GameRegistry.registerBlock(tweakedboilerOn, "tweakedboilerOn");
			GameRegistry.registerBlock(tweakedFlashBoiler, "tweakedFlashBoiler");
			GameRegistry.registerBlock(tfpLightningRod, "tfpLightningRod");
		}
		
		if(Loader.isModLoaded("necromancy"))
		{			
			tfpBlood = new TFPCustomFluids(TFPFluids.tfpFluidBlood, Material.water).setBlockName("tfpBlood");			
			GameRegistry.registerBlock(tfpBlood, "tfpBlood");
			TFPFluids.tfpFluidBlood.setBlock(tfpBlood);
			
			tfpAltar = new BlockTFPAltar().setHardness(4).setBlockName("tfpSummoningAltar");
			GameRegistry.registerBlock(tfpAltar, "tfpSummoningAltar");
			
			tfpAltarBlock = new BlockTFPAltarBlock().setHardness(4).setBlockName("tfpAltarBuildingBlock");
			GameRegistry.registerBlock(tfpAltarBlock, "tfpAltarBuildingBlock");
		}
		
		if(Loader.isModLoaded("steamcraft2"))
		{
			tfpWhaleOil = new TFPCustomFluids(TFPFluids.tfpFluidWhaleOil, Material.water).setHardness(100.0F).setLightOpacity(3).setBlockName("WhaleOil");
			GameRegistry.registerBlock(tfpWhaleOil, "WhaleOil");
			TFPFluids.tfpFluidWhaleOil.setBlock(tfpWhaleOil);
		}
	}
}
