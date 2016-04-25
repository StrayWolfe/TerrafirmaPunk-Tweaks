package com.JAWolfe.terrafirmapunktweaks.minetweaker.Forestry;

import forestry.api.recipes.RecipeManagers;
import forestry.factory.recipes.SqueezerRecipeManager;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfptweaks.Squeezer")
public class Squeezer 
{
	@ZenMethod
    public static void addRecipe(ILiquidStack outputLiquid, int ticks, IItemStack...inputs) 
	{
		ItemStack[] inputStacks = MineTweakerMC.getItemStacks(inputs);
		FluidStack outputStack = MineTweakerMC.getLiquidStack(outputLiquid);
		
		MineTweakerAPI.apply(new addSqueezerAction(outputStack, ticks, inputStacks));
	}
	
	@ZenMethod
    public static void addRecipe(ILiquidStack outputLiquid, IItemStack byProduct, int chance, int ticks, IItemStack...inputs) 
	{
		ItemStack[] inputStacks = MineTweakerMC.getItemStacks(inputs);
		FluidStack outputStack = MineTweakerMC.getLiquidStack(outputLiquid);
		ItemStack byproductStack = MineTweakerMC.getItemStack(byProduct);
		
		MineTweakerAPI.apply(new addSqueezerAction(outputStack, byproductStack, chance, ticks, inputStacks));
	}
	
	private static class addSqueezerAction implements IUndoableAction 
	{
		private ItemStack[] inputStacks;
		private ItemStack byProduct;
		private FluidStack outputFluid;
		private int tickcount;
		private int chance = 0;
		
		public addSqueezerAction(FluidStack output, int ticks, ItemStack[] inputs)
		{
			this.inputStacks = inputs;
			this.outputFluid = output;
			this.tickcount = ticks;			
		}
		
		public addSqueezerAction(FluidStack output, ItemStack byproduct, int chance, int ticks, ItemStack[] inputs)
		{
			this(output, ticks, inputs);
			this.byProduct = byproduct;
			this.chance = chance;
		}
		
		@Override
		public void apply() 
		{
			if(byProduct == null)
				RecipeManagers.squeezerManager.addRecipe(tickcount, inputStacks, outputFluid);
			else
				RecipeManagers.squeezerManager.addRecipe(tickcount, inputStacks, outputFluid, byProduct, chance);
		}
		
		@Override
		public String describe() 
		{
			String inputString = "";
			for(int i = 0; i < inputStacks.length; i++)
			{
				inputString.concat(inputStacks[i].getDisplayName() + " ");
			}
			
			if(byProduct == null)
				return "Adding items '" + inputString + "' to squeezer yeilding '" + outputFluid.getLocalizedName() + "'";
			else
				return "Adding items '" + inputString + "' to squeezer yeilding '" + outputFluid.getLocalizedName() + 
						" with the byproduct " + byProduct.getDisplayName() + "'";
		}

		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			RecipeManagers.squeezerManager.removeRecipe(SqueezerRecipeManager.findMatchingRecipe(inputStacks));
		}

		@Override
		public String describeUndo() 
		{
			String inputString = "";
			for(int i = 0; i < inputStacks.length; i++)
			{
				inputString = inputString.concat(inputStacks[i].getDisplayName() + " ");
			}
			
			if(byProduct == null)
				return "Removing items '" + inputString + "' from squeezer yeilding '" + outputFluid.getLocalizedName() + "'";
			else
				return "Removing items '" + inputString + "' from squeezer yeilding '" + outputFluid.getLocalizedName() + 
						" with the byproduct " + byProduct.getDisplayName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
}
