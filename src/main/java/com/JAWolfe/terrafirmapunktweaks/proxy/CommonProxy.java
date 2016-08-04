package com.JAWolfe.terrafirmapunktweaks.proxy;

import static com.bioxx.tfc.WorldGen.Generators.WorldGenOre.oreList;

import com.JAWolfe.terrafirmapunktweaks.TFPFluids;
import com.JAWolfe.terrafirmapunktweaks.items.TFPItems;
import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEAltar;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBoiler;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBrickOven;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEFlashBoiler;
import com.bioxx.tfc.Core.Config.TFC_ConfigFiles;
import com.bioxx.tfc.WorldGen.Generators.OreSpawnData;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.google.common.collect.ObjectArrays;
import com.sirolf2009.necromancy.item.ItemGeneric;

import buildcraft.BuildCraftEnergy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class CommonProxy 
{
	private static Configuration oresConfig;
	
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TEBrickOven.class, "TEBrickOven");
				
		if(Loader.isModLoaded("Steamcraft"))
		{
			GameRegistry.registerTileEntity(TEBoiler.class, "TEBoiler");
			GameRegistry.registerTileEntity(TEFlashBoiler.class, "TEFlashBoiler");
		}
		if(Loader.isModLoaded("necromancy"))
		{
			GameRegistry.registerTileEntity(TEAltar.class, "TEAltar");
		}
	}
	
	public void registerFluids()
	{
		FluidRegistry.registerFluid(TFPFluids.tfpFluidBlood);
		FluidRegistry.registerFluid(TFPFluids.tfpFluidWhaleOil);
	}
	
	public void setupFluids()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.OLIVEOIL.getName()), new ItemStack(TFPItems.CustomBucketOliveOil), new ItemStack(TFCItems.woodenBucketEmpty));
		
		if(Loader.isModLoaded("BuildCraft|Core"))
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(BuildCraftEnergy.fluidOil.getName()), new ItemStack(TFPItems.CustomBucketOil), new ItemStack(TFCItems.woodenBucketEmpty));
		}
		
		if(Loader.isModLoaded("necromancy"))
		{
			FluidContainerRegistry.registerFluidContainer(new FluidStack(TFPFluids.tfpFluidBlood, 1000), new ItemStack(TFPItems.CustomBucketBlood), new ItemStack(TFCItems.woodenBucketEmpty));
			FluidContainerRegistry.registerFluidContainer(new FluidStack(TFPFluids.tfpFluidBlood, 250), ItemGeneric.getItemStackFromName("Jar of Blood"), new ItemStack(TFCItems.glassBottle));
		}
		
		if(Loader.isModLoaded("steamcraft2"))
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFPFluids.tfpFluidWhaleOil.getName()), new ItemStack(TFPItems.CustomBucketWhaleOil), new ItemStack(TFCItems.woodenBucketEmpty));
		}
	}
	
	public void registerRenderInformation()
	{
		//Not server-sided
	}
	
	public void registerOreGen()
	{
		oreList.put("Wolframite", getOreData("Wolframite", "default", "medium", References.ModID + ":OreWolferamite", 0, 120, new String[] {"granite", "quartzite"}, 5, 128, 80, 60));
		
		for (String s : oresConfig.getCategoryNames()) {
			// If this is a new entry, otherwise it has already been added by the previous bit of code.
			if (!oreList.containsKey(s))
				oreList.put(s, getOreData(s, "default", "small", "Ore", 0, 100, new String[] { "granite", "basalt", "sedimentary" }, 5, 128, 50, 50));
		}

		if (oresConfig.hasChanged())
			oresConfig.save();
	}
	
	private static OreSpawnData getOreData(String category, String type, String size, String blockName, int meta, int rarity, String[] rocks, int min, int max, int v, int h)
	{
		oresConfig = TFC_ConfigFiles.getOresConfig();
		String[] ALLOWED_TYPES = new String[] { "default", "veins" };
		String[] ALLOWED_SIZES = new String[] { "small", "medium", "large" };
		String[] ALLOWED_BASE_ROCKS = ObjectArrays.concat(Global.STONE_ALL, new String[] { "igneous intrusive", "igneous extrusive", "sedimentary", "metamorphic" }, String.class);
		
		return new OreSpawnData(
				oresConfig.get(category, "type", type).setValidValues(ALLOWED_TYPES).getString(),
				oresConfig.get(category, "size", size).setValidValues(ALLOWED_SIZES).getString(),
				oresConfig.get(category, "oreName", blockName).getString(),
				oresConfig.get(category, "oreMeta", meta).getInt(),
				oresConfig.get(category, "rarity", rarity).getInt(),
				oresConfig.get(category, "baseRocks", rocks).setValidValues(ALLOWED_BASE_ROCKS).getStringList(),
				oresConfig.get(category, "Minimum Height", min).getInt(),
				oresConfig.get(category, "Maximum Height", max).getInt(),
				oresConfig.get(category, "Vertical Density", v).getInt(),
				oresConfig.get(category, "Horizontal Density", h).getInt()
		);
	}
	
	public void registerWAILA()
	{
		FMLInterModComms.sendMessage("Waila", "register", "com.JAWolfe.terrafirmapunktweaks.WAILAInfo.callbackRegister");
	}
}
