package com.JAWolfe.terrafirmapunktweaks.minetweaker;

import com.JAWolfe.terrafirmapunktweaks.handlers.FuelManager;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfptweaks.BrickOven")
public class BrickOven 
{
	@ZenMethod
	public static void addFuel(IItemStack Ifuel)
	{
		ItemStack fuel = MineTweakerMC.getItemStack(Ifuel);
		
		if(fuel == null || fuel.getItem() == null)
			MineTweakerAPI.logError("Missing Fuel");
		else
			MineTweakerAPI.apply(new addFuelAction(fuel));
	}
	
	@ZenMethod
	public static void removeFuel(IItemStack Ifuel)
	{
		ItemStack fuel = MineTweakerMC.getItemStack(Ifuel);
		
		if(fuel == null || fuel.getItem() == null)
			MineTweakerAPI.logError("Missing Fuel");
		else
			MineTweakerAPI.apply(new removeFuelAction(fuel));
	}
	
	private static class addFuelAction implements IUndoableAction 
	{
		private ItemStack Fuel;
		
		public addFuelAction(ItemStack fuel)
		{
			this.Fuel = fuel;
		}
		
		@Override
		public void apply() 
		{
			FuelManager.getInstance().addFuel(Fuel);
		}
		
		@Override
		public String describe() 
		{
			return "Adding item '" + Fuel.getDisplayName() + "' to the Brick Oven Fuel Registry.";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			FuelManager.getInstance().removeFuel(Fuel);
		}
		
		@Override
		public String describeUndo() 
		{
			return "Removing item '" + Fuel.getDisplayName() + "' from the Brick Oven Fuel Registry.";
		}
		
		@Override
		public Object getOverrideKey() 
		{
			return null;
		}
	}
	
	private static class removeFuelAction implements IUndoableAction 
	{
		private ItemStack Fuel;
		
		public removeFuelAction(ItemStack fuel)
		{
			this.Fuel = fuel;
		}
		
		@Override
		public void apply() 
		{
			FuelManager.getInstance().removeFuel(Fuel);
		}
		
		@Override
		public String describe() 
		{
			return "Removing item '" + Fuel.getDisplayName() + "' from the Brick Oven Fuel Registry.";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			FuelManager.getInstance().addFuel(Fuel);
		}
		
		@Override
		public String describeUndo() 
		{
			return "Adding item '" + Fuel.getDisplayName() + "' to the Brick Oven Fuel Registry.";
		}
		
		@Override
		public Object getOverrideKey() 
		{
			return null;
		}
	}
}
