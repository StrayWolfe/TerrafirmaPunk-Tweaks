package com.JAWolfe.terrafirmapunktweaks.minetweaker.TFC;

import java.util.List;

import com.bioxx.tfc.api.Crafting.BarrelLiquidToLiquidRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfptweaks.Barrel")
public class Barrel 
{		
	//BarrelRecipe
	@ZenMethod
	public static void addItemFluidConversion(IItemStack outputIS, ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel, boolean sealed, int sealtime, boolean removesLiquid, boolean allowAnyStack)
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(inputIS);
		FluidStack inputFluid = MineTweakerMC.getLiquidStack(inputFS);
		ItemStack outputStack = MineTweakerMC.getItemStack(outputIS);
		FluidStack outputFluid = MineTweakerMC.getLiquidStack(outputFS);
		
		if(inputFluid == null || inputFluid.getFluid() == null)
			MineTweakerAPI.logError("Missing InputFluid");
		else if(inputFluid.amount <= 0)
			MineTweakerAPI.logError("InputFluid must contain more than 0 mb of fluid");
		else if(outputFluid == null || outputFluid.getFluid() == null)
			MineTweakerAPI.logError("Missing OutputFluid");
		else if(outputFluid.amount <= 0)
			MineTweakerAPI.logError("OutputFluid must contain more than 0 mb of fluid");
		else if(sealed == false && sealtime > 0)
			MineTweakerAPI.logError("Sealed time must be 0 if barrel is unsealed");
		else
			MineTweakerAPI.apply(new addFluidConversionAction(inputStack, inputFluid, outputStack, outputFluid, sealtime, removesLiquid, sealed, minTechLevel, allowAnyStack));
	}
	
	@ZenMethod
	public static void addItemFluidConversion(IItemStack outputIS, ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel, boolean sealed, int sealtime, boolean removesLiquid)
	{
		addItemFluidConversion(outputIS, outputFS, inputIS, inputFS, minTechLevel, sealed, sealtime, removesLiquid, true);
	}
	
	@ZenMethod
	public static void addItemFluidConversion(IItemStack outputIS, ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel, boolean sealed, int sealtime)
	{
		addItemFluidConversion(outputIS, outputFS, inputIS, inputFS, minTechLevel, sealed, sealtime, true, true);
	}
	
	@ZenMethod
	public static void addItemFluidConversion(IItemStack outputIS, ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel)
	{
		addItemFluidConversion(outputIS, outputFS, inputIS, inputFS, minTechLevel, true, 8, true, true);
	}
	
	@ZenMethod
	public static void addItemFluidConversion(IItemStack outputIS, ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS)
	{
		addItemFluidConversion(outputIS, outputFS, inputIS, inputFS, 1, true, 8, true, true);
	}
	
	@ZenMethod
	public static void addItemFluidConversion(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel, boolean sealed, int sealtime, boolean removesLiquid, boolean allowAnyStack)
	{
		addItemFluidConversion(null, outputFS, inputIS, inputFS, minTechLevel, sealed, sealtime, removesLiquid, allowAnyStack);
	}
	
	@ZenMethod
	public static void addItemFluidConversion(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel, boolean sealed, int sealtime, boolean removesLiquid)
	{
		addItemFluidConversion(null, outputFS, inputIS, inputFS, minTechLevel, sealed, sealtime, removesLiquid, true);
	}
	
	@ZenMethod
	public static void addItemFluidConversion(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel, boolean sealed, int sealtime)
	{
		addItemFluidConversion(null, outputFS, inputIS, inputFS, minTechLevel, sealed, sealtime, true, true);
	}
	
	@ZenMethod
	public static void addItemFluidConversion(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel)
	{
		addItemFluidConversion(null, outputFS, inputIS, inputFS, minTechLevel, true, 8, true, true);
	}
	
	@ZenMethod
	public static void addItemFluidConversion(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS)
	{
		addItemFluidConversion(null, outputFS, inputIS, inputFS, 1, true, 8, true, true);
	}
	
	@ZenMethod
	public static void removeItemFluidConversion(IItemStack inputIS, ILiquidStack inputFS)
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(inputIS);
		FluidStack inputFluid = MineTweakerMC.getLiquidStack(inputFS);
		
		if(inputStack == null || inputStack.getItem() == null)
			MineTweakerAPI.logError("Missing InputStack");
		else if(inputFluid == null || inputFluid.getFluid() == null)
			MineTweakerAPI.logError("Missing InputFluid");
		else if(inputFluid.amount <= 0)
			MineTweakerAPI.logError("InputFluid must contain more than 0 mb of fluid");
		else
			MineTweakerAPI.apply(new removeItemFluidConversionAction(inputStack, inputFluid));
	}	
	
	//BarrelMultiItemRecipe - Better for converting items as the outputstack size can match the input
	@ZenMethod
	public static void addItemConversion(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel, boolean sealed, boolean allowAnyStack)
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(inputIS);
		ItemStack outputStack = MineTweakerMC.getItemStack(outputIS);
		FluidStack inputFluid = MineTweakerMC.getLiquidStack(inputFS);
		
		MineTweakerAPI.apply(new addItemConversionAction(inputStack, inputFluid, outputStack, sealed, minTechLevel, allowAnyStack));
	}
	
	@ZenMethod
	public static void addItemConversion(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel, boolean sealed)
	{
		addItemConversion(outputIS, inputIS, inputFS, minTechLevel, sealed, true);
	}
	
	@ZenMethod
	public static void addItemConversion(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS, int minTechLevel)
	{
		addItemConversion(outputIS, inputIS, inputFS, minTechLevel, true, true);
	}
	
	@ZenMethod
	public static void addItemConversion(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS)
	{
		addItemConversion(outputIS, inputIS, inputFS, 1, true, true);
	}
	
	@ZenMethod
	public static void removeItemConversion(IItemStack inputIS, ILiquidStack inputFS)
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(inputIS);
		FluidStack inputFluid = MineTweakerMC.getLiquidStack(inputFS);
		
		MineTweakerAPI.apply(new removeItemFluidConversionAction(inputStack, inputFluid));
	}
	
	@ZenMethod
	public static void ageFluid(ILiquidStack outputFS, ILiquidStack inputFS, int minTechLevel, boolean sealed, int sealtime)
	{		
		FluidStack inputFluid = MineTweakerMC.getLiquidStack(inputFS);
		FluidStack outputFluid = MineTweakerMC.getLiquidStack(outputFS);
		
		if(inputFluid.amount <= 0)
			MineTweakerAPI.logError("InputFluid must contain more than 0 mb of fluid");
		else if(outputFluid == null || outputFluid.getFluid() == null)
			MineTweakerAPI.logError("Missing OutputFluid");
		else if(outputFluid.amount <= 0)
			MineTweakerAPI.logError("OutputFluid must contain more than 0 mb of fluid");
		else if(sealed == false && sealtime > 0)
			MineTweakerAPI.logError("Sealed time must be 0 if barrel is unsealed");
		else
			MineTweakerAPI.apply(new addAgedFluidAction(inputFluid, outputFluid, sealtime, sealed, minTechLevel));
	}
	
	@ZenMethod
	public static void ageFluid(ILiquidStack outputFS, ILiquidStack inputFS, int minTechLevel)
	{
		ageFluid(outputFS, inputFS, minTechLevel, true, 8);
	}
	
	@ZenMethod
	public static void ageFluid(ILiquidStack outputFS, ILiquidStack inputFS)
	{
		ageFluid(outputFS, inputFS, 1, true, 8);
	}
	
	//BarrelLiquidToLiquidRecipe
	@ZenMethod
	public static void addFluidCombination(ILiquidStack outputFluid, ILiquidStack fluidInBarrel, ILiquidStack inputFluid)
	{
		FluidStack barrelContents = MineTweakerMC.getLiquidStack(fluidInBarrel);
		FluidStack inputfluid = MineTweakerMC.getLiquidStack(inputFluid);
		FluidStack outputfluid = MineTweakerMC.getLiquidStack(outputFluid);
		
		MineTweakerAPI.apply(new addFluidCombinationAction(barrelContents, inputfluid, outputfluid));
	}
	
	@ZenMethod
	public static void removeFluidCombination(ILiquidStack outputFluid, ILiquidStack fluidInBarrel, ILiquidStack inputFluid)
	{
		FluidStack barrelContents = MineTweakerMC.getLiquidStack(fluidInBarrel);
		FluidStack inputfluid = MineTweakerMC.getLiquidStack(inputFluid);
		FluidStack outputfluid = MineTweakerMC.getLiquidStack(outputFluid);
		
		MineTweakerAPI.apply(new removeFluidCombinationAction(barrelContents, inputfluid, outputfluid));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	//Add Fluid Conversion
	
	private static class addFluidConversionAction implements IUndoableAction 
	{
		ItemStack inputStack;
		ItemStack outputStack;
		FluidStack inputFluid;
		FluidStack outputFluid;
		int sealtime;
		boolean removesLiquid;
		boolean sealed;
		int minTechLevel;
		boolean allowAnyStack;
		
		public addFluidConversionAction(ItemStack inputIS, FluidStack inputFS, ItemStack outputIS, FluidStack outputFS, int sealtime, boolean removesLiquid, boolean sealed, int minTechLevel, boolean allowAnyStack)
		{
			this.inputStack = inputIS;
			this.inputFluid = inputFS;
			this.outputFluid = outputFS;
			this.outputStack = outputIS;
			this.sealtime = sealtime;
			this.removesLiquid = removesLiquid;
			this.sealed = sealed;
			this.minTechLevel =	minTechLevel;
			this.allowAnyStack = allowAnyStack;
		}

		@Override
		public void apply() 
		{
			BarrelManager.getInstance().addRecipe(new BarrelRecipe(inputStack, inputFluid, outputStack, outputFluid, sealtime).setRemovesLiquid(removesLiquid).setAllowAnyStack(allowAnyStack).setMinTechLevel(minTechLevel).setSealedRecipe(sealed));
		}

		@Override
		public String describe() 
		{
			if(outputStack != null)
				return "Adding item '" + inputStack.getDisplayName() + "' with '" + inputFluid.getLocalizedName()
				+ "' to barrel yeilding '" + outputStack.getDisplayName() + "'";
			else
				return "Adding item '" + inputStack.getDisplayName() + "' with '" + inputFluid.getLocalizedName()
				+ "' to barrel yeilding '" + outputFluid.getLocalizedName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			List<BarrelRecipe> BarrelList = BarrelManager.getInstance().getRecipes();
			for (int i = 0; i < BarrelList.size(); i++)
			{
				if (BarrelList.get(i) != null)
				{
					if (BarrelList.get(i).matches(inputStack, inputFluid))
						BarrelList.remove(i--);
				}
			}
		}

		@Override
		public String describeUndo() 
		{
			return "Removing item '" + inputStack.getDisplayName() + "' with '" + inputFluid.getLocalizedName() + "' from barrel '";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
	
	//Remove Fluid Conversion & Remove Item Conversion Action
	
	private static class removeItemFluidConversionAction implements IUndoableAction 
	{
		ItemStack inputStack;
		FluidStack inputFluid;
		
		public removeItemFluidConversionAction(ItemStack inputIS, FluidStack inputFS)
		{
			this.inputStack = inputIS;
			this.inputFluid = inputFS;
		}

		@Override
		public void apply() 
		{
			List<BarrelRecipe> BarrelList = BarrelManager.getInstance().getRecipes();
			for (int i = 0; i < BarrelList.size(); i++)
			{
				if (BarrelList.get(i) != null)
				{
					if (BarrelList.get(i).matches(inputStack, inputFluid))
						BarrelList.remove(i--);
				}
			}
		}

		@Override
		public String describe() 
		{
			return "Removing item '" + inputStack.getDisplayName() + "' with '" + inputFluid.getLocalizedName() + "' from barrel '";
		}
		
		@Override
		public boolean canUndo() 
		{
			return false;
		}
		
		@Override
		public void undo() 
		{
		}

		@Override
		public String describeUndo() 
		{
			return null;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
	
	//Add Item Conversion Action
	
	private static class addItemConversionAction implements IUndoableAction 
	{
		ItemStack inputStack;
		ItemStack outputStack;
		FluidStack inputFluid;
		boolean sealed;
		int minTechLevel;
		boolean allowAnyStack;
		
		public addItemConversionAction(ItemStack inputIS, FluidStack inputFS, ItemStack outputIS, boolean sealed, int minTechLevel, boolean allowAnyStack)
		{
			this.inputStack = inputIS;
			this.inputFluid = inputFS;
			this.outputStack = outputIS;
			this.sealed = sealed;
			this.minTechLevel =	minTechLevel;
			this.allowAnyStack = allowAnyStack;
			
		}

		@Override
		public void apply() 
		{
			BarrelManager.getInstance().addRecipe(new BarrelRecipe(inputStack, inputFluid, outputStack, inputFluid).setAllowAnyStack(allowAnyStack).setMinTechLevel(minTechLevel).setSealedRecipe(sealed));
		}

		@Override
		public String describe() 
		{
			return "Adding item '" + inputStack.getDisplayName() + "' with '" + inputFluid.getLocalizedName()
			+ "' to barrel yeilding '" + outputStack.getDisplayName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			List<BarrelRecipe> BarrelList = BarrelManager.getInstance().getRecipes();
			for (int i = 0; i < BarrelList.size(); i++)
			{
				if (BarrelList.get(i) != null)
				{
					if (BarrelList.get(i).matches(inputStack, inputFluid))
						BarrelList.remove(i--);
				}
			}
		}

		@Override
		public String describeUndo() 
		{
			return "Removing item '" + inputStack.getDisplayName() + "' with '" + inputFluid.getLocalizedName() + "' from barrel '";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
	
	//Add Fluid Combination Action
	
	private static class addFluidCombinationAction implements IUndoableAction 
	{
		FluidStack barrelContents;
		FluidStack inputFluid;
		FluidStack outputFluid;
		
		public addFluidCombinationAction(FluidStack barrelContents, FluidStack inputFS, FluidStack outputFS)
		{
			this.barrelContents = barrelContents;
			this.inputFluid = inputFS;
			this.outputFluid = outputFS;
		}

		@Override
		public void apply() 
		{
			BarrelManager.getInstance().addRecipe(new BarrelLiquidToLiquidRecipe(barrelContents, inputFluid, outputFluid).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		}

		@Override
		public String describe() 
		{
			return "Combining '" + barrelContents.getLocalizedName() + "' with '" + inputFluid.getLocalizedName()
			+ "' in barrel yeilding '" + outputFluid.getLocalizedName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			List<BarrelRecipe> BarrelList = BarrelManager.getInstance().getRecipes();
			for (int i = 0; i < BarrelList.size(); i++)
			{
				if (BarrelList.get(i) != null)
				{
					if(BarrelList.get(i) instanceof BarrelLiquidToLiquidRecipe)
					{
						if(BarrelList.get(i).getInItem() == null && BarrelList.get(i).getRecipeOutIS() == null &&
							((BarrelLiquidToLiquidRecipe)BarrelList.get(i)).getInputfluid().isFluidEqual(inputFluid) &&
							BarrelList.get(i).getInFluid().isFluidEqual(barrelContents) && 
							BarrelList.get(i).getRecipeOutFluid().isFluidEqual(outputFluid))
						{
							BarrelList.remove(i--);
						}
					}
				}
			}
		}

		@Override
		public String describeUndo() 
		{
			return "Removing combination of '" + barrelContents.getLocalizedName() + "' with '" + inputFluid.getLocalizedName()
			+ "' in barrel yeilding '" + outputFluid.getLocalizedName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
	
	//Remove Fluid Combination Action
	
	private static class removeFluidCombinationAction implements IUndoableAction 
	{
		FluidStack barrelContents;
		FluidStack inputFluid;
		FluidStack outputFluid;
		
		public removeFluidCombinationAction(FluidStack barrelContents, FluidStack inputFS, FluidStack outputFS)
		{
			this.barrelContents = barrelContents;
			this.inputFluid = inputFS;
			this.outputFluid = outputFS;
		}

		@Override
		public void apply() 
		{
			List<BarrelRecipe> BarrelList = BarrelManager.getInstance().getRecipes();
			for (int i = 0; i < BarrelList.size(); i++)
			{
				if (BarrelList.get(i) != null)
				{
					if(BarrelList.get(i) instanceof BarrelLiquidToLiquidRecipe)
					{
						if(BarrelList.get(i).getInItem() == null && BarrelList.get(i).getRecipeOutIS() == null &&
							((BarrelLiquidToLiquidRecipe)BarrelList.get(i)).getInputfluid().isFluidEqual(inputFluid) &&
							BarrelList.get(i).getInFluid().isFluidEqual(barrelContents) && 
							BarrelList.get(i).getRecipeOutFluid().isFluidEqual(outputFluid))
						{
							BarrelList.remove(i--);
						}
					}
				}
			}
		}

		@Override
		public String describe() 
		{
			return "Removing combination of '" + barrelContents.getLocalizedName() + "' with '" + inputFluid.getLocalizedName()
			+ "' in barrel yeilding '" + outputFluid.getLocalizedName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			BarrelManager.getInstance().addRecipe(new BarrelLiquidToLiquidRecipe(barrelContents, inputFluid, outputFluid).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		}

		@Override
		public String describeUndo() 
		{			
			return "Adding Combination of '" + barrelContents.getLocalizedName() + "' with '" + inputFluid.getLocalizedName()
			+ "' in barrel yeilding '" + outputFluid.getLocalizedName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
	
	//Add Fluid Aging
	
	private static class addAgedFluidAction implements IUndoableAction 
	{
		FluidStack inputFluid;
		FluidStack outputFluid;
		int sealtime;
		boolean sealed;
		int minTechLevel;
		
		public addAgedFluidAction(FluidStack inputFS, FluidStack outputFS, int sealtime, boolean sealed, int minTechLevel)
		{
			this.inputFluid = inputFS;
			this.outputFluid = outputFS;
			this.sealtime = sealtime;
			this.sealed = sealed;
			this.minTechLevel =	minTechLevel;
		}

		@Override
		public void apply() 
		{
			BarrelManager.getInstance().addRecipe(new BarrelRecipe(null, inputFluid, null, outputFluid, sealtime).setRemovesLiquid(false).setMinTechLevel(minTechLevel).setSealedRecipe(sealed));
		}

		@Override
		public String describe() 
		{
			return "Adding aging to '" + inputFluid.getLocalizedName() + "' in barrel yeilding '" + outputFluid.getLocalizedName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			List<BarrelRecipe> BarrelList = BarrelManager.getInstance().getRecipes();
			for (int i = 0; i < BarrelList.size(); i++)
			{
				if (BarrelList.get(i) != null)
				{
					if (BarrelList.get(i).getRecipeOutIS() == null && BarrelList.get(i).getInItem() == null &&
						BarrelList.get(i).getInFluid() == inputFluid && BarrelList.get(i).getRecipeOutFluid() == outputFluid)
						BarrelList.remove(i--);
				}
			}
		}

		@Override
		public String describeUndo() {
			return "Remove aging to '" + inputFluid.getLocalizedName() + "' in barrel yeilding '" + outputFluid.getLocalizedName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
