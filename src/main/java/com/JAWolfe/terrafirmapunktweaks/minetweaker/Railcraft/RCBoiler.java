package com.JAWolfe.terrafirmapunktweaks.minetweaker.Railcraft;

import java.util.Map;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import mods.railcraft.api.fuel.FuelManager;

@ZenClass("mods.tfptweaks.RCBoiler")
public class RCBoiler 
{
	//Ex. Biodiesel/16000, creosote/4800, bioethanol/16000, fuel/48000
	@ZenMethod
	public static void addFuel(ILiquidStack fluidstack, int time)
	{
		Fluid fluid = MineTweakerMC.getLiquidStack(fluidstack).getFluid();
		
		MineTweakerAPI.apply(new addRCFuels(fluid, time));
	}
	
	@ZenMethod
	public static void removeFuel(ILiquidStack fluidstack)
	{
		Fluid fluid = MineTweakerMC.getLiquidStack(fluidstack).getFluid();
		
		MineTweakerAPI.apply(new removeRCFuels(fluid));
	}
	
	private static class addRCFuels implements IUndoableAction 
	{
		Fluid fluid;
		int heat;
		
		public addRCFuels(Fluid fuel, int heat)
		{
			this.fluid = fuel;
			this.heat = heat;
		}
		
		@Override
		public void apply() 
		{
			FuelManager.addBoilerFuel(fluid, heat);
		}
		
		@Override
		public String describe() 
		{
			return "Adding fuel '" + fluid.getLocalizedName(new FluidStack(fluid, 1000)) + "' to RC fuels.'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			Map<Fluid, Integer> fuelHandler = FuelManager.boilerFuel;
			
			fuelHandler.remove(fluid, heat);
		}
		
		@Override
		public String describeUndo() 
		{
			return "Removing fuel '" + fluid.getLocalizedName(new FluidStack(fluid, 1000)) + "' from RC fuels.'";
		}
		
		@Override
		public Object getOverrideKey() 
		{
			return null;
		}
	}
	
	private static class removeRCFuels implements IUndoableAction 
	{
		Fluid fluid;
		int heat;
		
		public removeRCFuels(Fluid fuel)
		{
			this.fluid = fuel;
			heat = FuelManager.getBoilerFuelValue(fluid);
		}
		
		@Override
		public void apply() 
		{			
			Map<Fluid, Integer> fuelHandler = FuelManager.boilerFuel;
				
			if(heat != 0)
				fuelHandler.remove(fluid, heat);
		}
		
		@Override
		public String describe() 
		{
			return "Removing fuel '" + fluid.getLocalizedName(new FluidStack(fluid, 1000)) + "' from RC fuels.";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			if(heat != 0)
				FuelManager.addBoilerFuel(fluid, heat);
		}
		
		@Override
		public String describeUndo() 
		{
			return "Adding fuel '" + fluid.getLocalizedName(new FluidStack(fluid, 1000)) + "' to RC fuels.";
		}
		
		@Override
		public Object getOverrideKey() 
		{
			return null;
		}
	}
}
