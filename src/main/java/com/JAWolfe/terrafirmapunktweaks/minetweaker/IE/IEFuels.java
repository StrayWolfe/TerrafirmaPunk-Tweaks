package com.JAWolfe.terrafirmapunktweaks.minetweaker.IE;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import java.util.HashMap;
import blusunrize.immersiveengineering.api.energy.DieselHandler;

@ZenClass("mods.tfptweaks.IEFuels")
public class IEFuels 
{
	//Ex. Biodiesel/125, Fuel/375, Diesel/175
	@ZenMethod
	public static void addFuel(ILiquidStack fluidstack, int time)
	{		
		Fluid fluid = MineTweakerMC.getLiquidStack(fluidstack).getFluid();
				
		MineTweakerAPI.apply(new addIEFuels(fluid, time));
	}
	
	@ZenMethod
	public static void removeFuel(ILiquidStack fluidstack)
	{
		Fluid fluid = MineTweakerMC.getLiquidStack(fluidstack).getFluid();
		
		MineTweakerAPI.apply(new removeIEFuels(fluid));
	}
	
	private static class addIEFuels implements IUndoableAction 
	{
		Fluid fluid;
		int burnTime;
		
		public addIEFuels(Fluid fuel, int time)
		{
			this.fluid = fuel;
			this.burnTime = time;
		}
		
		@Override
		public void apply() 
		{
			DieselHandler.registerFuel(fluid, burnTime);
		}
		
		@Override
		public String describe() 
		{
			return "Adding fuel '" + fluid.getLocalizedName(new FluidStack(fluid, 1000)) + "' to IE fuels.'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			HashMap<String, Integer> fuelHandler = DieselHandler.getFuelValues();
			
			fuelHandler.remove(fluid.getName(), burnTime);
		}
		
		@Override
		public String describeUndo() 
		{
			return "Removing fuel '" + fluid.getLocalizedName(new FluidStack(fluid, 1000)) + "' from IE fuels.'";
		}
		
		@Override
		public Object getOverrideKey() 
		{
			return null;
		}
	}
	
	private static class removeIEFuels implements IUndoableAction 
	{
		Fluid fluid;
		int burnTime;
		
		public removeIEFuels(Fluid fuel)
		{
			this.fluid = fuel;
			burnTime = DieselHandler.getBurnTime(fluid);
		}
		
		@Override
		public void apply() 
		{			
			HashMap<String, Integer> fuelHandler = DieselHandler.getFuelValues();
			
			if(burnTime != 0)
				fuelHandler.remove(fluid.getName(), burnTime);
		}
		
		@Override
		public String describe() 
		{
			return "Removing fuel '" + fluid.getLocalizedName(new FluidStack(fluid, 1000)) + "' from IE fuels.";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			if(burnTime != 0)
			 DieselHandler.registerFuel(fluid, burnTime);
		}
		
		@Override
		public String describeUndo() 
		{
			return "Adding fuel '" + fluid.getLocalizedName(new FluidStack(fluid, 1000)) + "' to IE fuels.";
		}
		
		@Override
		public Object getOverrideKey() 
		{
			return null;
		}
	}
}
