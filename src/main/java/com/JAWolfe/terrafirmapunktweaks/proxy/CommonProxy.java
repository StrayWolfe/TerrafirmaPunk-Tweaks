package com.JAWolfe.terrafirmapunktweaks.proxy;

import com.JAWolfe.terrafirmapunktweaks.TFPFluids;
import com.JAWolfe.terrafirmapunktweaks.items.TFPItems;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEAltar;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBoiler;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBrickOven;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEFlashBoiler;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.sirolf2009.necromancy.item.ItemGeneric;

import buildcraft.BuildCraftEnergy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class CommonProxy 
{
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
	
	public void registerWAILA()
	{
		FMLInterModComms.sendMessage("Waila", "register", "com.JAWolfe.terrafirmapunktweaks.WAILAInfo.callbackRegister");
	}
}
