package com.JAWolfe.tfptweaks.minetweaker;

import java.util.List;

import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelMultiItemRecipe;
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
	@ZenMethod
	public static void convertFluid(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int sealtime, boolean removesLiquid, boolean sealed, int minTechLevel, boolean allowAnyStack)
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(inputIS);
		FluidStack inputFluid = MineTweakerMC.getLiquidStack(inputFS);
		FluidStack outputFluid = MineTweakerMC.getLiquidStack(outputFS);
		
		MineTweakerAPI.apply(new convertFluidAction(inputStack, inputFluid, outputFluid, sealtime, removesLiquid, sealed, minTechLevel, allowAnyStack));
	}
	
	@ZenMethod
	public static void convertFluid(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int sealtime, boolean removesLiquid, boolean sealed, int minTechLevel)
	{
		convertFluid(outputFS, inputIS, inputFS, sealtime, removesLiquid, sealed, minTechLevel, true);
	}
	
	@ZenMethod
	public static void convertFluid(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int sealtime, boolean removesLiquid, boolean sealed)
	{
		convertFluid(outputFS, inputIS, inputFS, sealtime, removesLiquid, sealed, 1, true);
	}
	
	@ZenMethod
	public static void convertFluid(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int sealtime, boolean removesLiquid)
	{
		convertFluid(outputFS, inputIS, inputFS, sealtime, removesLiquid, true, 1, true);
	}
	
	@ZenMethod
	public static void convertFluid(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS, int sealtime)
	{
		convertFluid(outputFS, inputIS, inputFS, sealtime, true, true, 1, true);
	}
	
	@ZenMethod
	public static void convertFluid(ILiquidStack outputFS, IItemStack inputIS, ILiquidStack inputFS)
	{
		convertFluid(outputFS, inputIS, inputFS, 8, true, true, 1, true);
	}
	
	@ZenMethod
	public static void convertItem(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS, boolean sealed, int minTechLevel, boolean allowAnyStack, boolean keepstacksize)
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(inputIS);
		ItemStack outputStack = MineTweakerMC.getItemStack(outputIS);
		FluidStack inputFluid = MineTweakerMC.getLiquidStack(inputFS);
		
		MineTweakerAPI.apply(new addBarrelMultiItemAction(inputStack, inputFluid, outputStack, sealed, minTechLevel, allowAnyStack, keepstacksize));
	}
	
	@ZenMethod
	public static void convertItem(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS, boolean sealed, int minTechLevel, boolean allowAnyStack)
	{		
		convertItem(outputIS, inputIS, inputFS, sealed, minTechLevel, allowAnyStack, true);
	}
	
	@ZenMethod
	public static void convertItem(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS, boolean sealed, int minTechLevel)
	{		
		convertItem(outputIS, inputIS, inputFS, sealed, minTechLevel, true, true);
	}
	
	@ZenMethod
	public static void convertItem(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS, boolean sealed)
	{		
		convertItem(outputIS, inputIS, inputFS, sealed, 1, true, true);
	}
	
	@ZenMethod
	public static void convertItem(IItemStack outputIS, IItemStack inputIS, ILiquidStack inputFS)
	{		
		convertItem(outputIS, inputIS, inputFS, true, 1, true, true);
	}
	
	private static class convertFluidAction implements IUndoableAction 
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
		
		public convertFluidAction(ItemStack inputIS, FluidStack inputFS, FluidStack outputFS, int sealtime, boolean removesLiquid, boolean sealed, int minTechLevel, boolean allowAnyStack)
		{
			this.inputStack = inputIS;
			this.inputFluid = inputFS;
			this.outputFluid = outputFS;
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
	
	private static class addBarrelMultiItemAction implements IUndoableAction 
	{
		ItemStack inputStack;
		ItemStack outputStack;
		FluidStack inputFluid;
		boolean sealed;
		int minTechLevel;
		boolean allowAnyStack;
		
		public addBarrelMultiItemAction(ItemStack inputIS, FluidStack inputFS, ItemStack outputIS, boolean sealed, int minTechLevel, boolean allowAnyStack, boolean keepstacksize)
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
			BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(inputStack, inputFluid, outputStack, inputFluid).setAllowAnyStack(allowAnyStack).setMinTechLevel(minTechLevel).setSealedRecipe(sealed));
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
}
