package com.JAWolfe.tfptweaks.minetweaker.Buildcraft;

import java.util.List;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.core.recipes.FlexibleRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.buildcraft.AssemblyTable")
@ModOnly(value="BuildCraft|Core", version="7.1")
public class AssemblyTable 
{
	private static int iCounter = 0;
	
	@ZenMethod
	public static void addRecipe(IItemStack output, int energy, IItemStack[] ingredients) 
	{
		MineTweakerAPI.apply(new AddRecipeAction(iCounter++, output, energy, ingredients));
	}
	
	@ZenMethod
	public static void remove(IIngredient output) {
		removeRecipe(output, null, false);
	}
	
	@ZenMethod
	public static void removeRecipe(IIngredient output, @Optional IIngredient[] ingredients, @Optional boolean wildcard) {
		List<IFlexibleRecipe<ItemStack>> toRemove = BuildcraftRecipes.removeRecipes(
				output,
				ingredients,
				BuildcraftRecipeRegistry.assemblyTable.getRecipes());
		
		for (IFlexibleRecipe<ItemStack> recipe : toRemove) 
		{
			@SuppressWarnings("rawtypes")
			ItemStack recipeOutput = recipe instanceof FlexibleRecipe ? (ItemStack)(((FlexibleRecipe) recipe).output) : null;
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe, recipeOutput));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		ItemStack output;
		int energy;
		ItemStack[] ingredients;
		String id;
		
		public AddRecipeAction(int id, IItemStack output, int energy, IItemStack[] ingredients) 
		{
			this.output = MineTweakerMC.getItemStack(output);
			this.energy = energy;
			this.ingredients = MineTweakerMC.getItemStacks(ingredients);
			this.id = "terrafirmapunktweaks:recipe" + id;
		}

		@Override
		public void apply() 
		{
			BuildcraftRecipeRegistry.assemblyTable.addRecipe(id, energy, output, (Object[])ingredients);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.assemblyTable.removeRecipe(id);
		}

		@Override
		public String describe() {
			return "Adding assembly table recipe for " + output;
		}

		@Override
		public String describeUndo() {
			return "Removing assembly table recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final IFlexibleRecipe<ItemStack> recipe;
		private final ItemStack output;
		
		public RemoveRecipeAction(IFlexibleRecipe<ItemStack> recipe, ItemStack output) {
			this.recipe = recipe;
			this.output = output;
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.assemblyTable.removeRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.assemblyTable.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Removing assembly table recipe for " + MineTweakerMC.getIItemStack(output);
		}

		@Override
		public String describeUndo() {
			return "Restoring assembly table recipe for " + MineTweakerMC.getIItemStack(output);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
