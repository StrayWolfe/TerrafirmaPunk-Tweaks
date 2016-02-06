package com.JAWolfe.tfptweaks.minetweaker;

import java.util.List;

import com.bioxx.tfc.api.Crafting.QuernManager;
import com.bioxx.tfc.api.Crafting.QuernRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfptweaks.Quern")
public class Quern 
{
	@ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input) 
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(input);
		ItemStack outputStack = MineTweakerMC.getItemStack(output);
		
		if(inputStack == null || inputStack.getItem() == null)
			MineTweakerAPI.logError("Missing InputStack");
		else if(outputStack == null || outputStack.getItem() == null)
			MineTweakerAPI.logError("Missing OutputStack");
		else
			MineTweakerAPI.apply(new addQuernAction(outputStack, inputStack));
	}
	
	@ZenMethod
    public static void removeRecipe(IItemStack output, IItemStack input) 
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(input);
		ItemStack outputStack = MineTweakerMC.getItemStack(output);
		
		if(inputStack == null || inputStack.getItem() == null)
			MineTweakerAPI.logError("Missing InputStack");
		else if(outputStack == null || outputStack.getItem() == null)
			MineTweakerAPI.logError("Missing OutputStack");
		else
			MineTweakerAPI.apply(new removeQuernAction(outputStack, inputStack));
	}
	
	private static class addQuernAction implements IUndoableAction 
	{
		private ItemStack inputStack;
		private ItemStack outputStack;
		
		public addQuernAction(ItemStack output, ItemStack input)
		{
			
			this.outputStack = output;
			this.inputStack = input;
		}

		@Override
		public void apply() 
		{
			QuernManager.getInstance().addRecipe(new QuernRecipe(inputStack, outputStack));
		}

		@Override
		public String describe() 
		{
			return "Adding item '" + inputStack.getDisplayName() + "' to the Quern to yeild '" + outputStack.getDisplayName() + "'";
		}

		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			List<QuernRecipe> quernList = QuernManager.getInstance().getRecipes();
			for (int i = 0; i < quernList.size(); i++)
			{
				if (quernList.get(i) != null)
				{
					if (quernList.get(i).isInItem(inputStack) && ItemStack.areItemStacksEqual(quernList.get(i).getResult(), outputStack))
						quernList.remove(i--);
				}
			}
			
			if(QuernManager.getInstance().findMatchingRecipe(inputStack) == null && QuernManager.getInstance().isValidItem(inputStack))
			{
				List<ItemStack> validItemsList = QuernManager.getInstance().getValidItems();
				for (int i = 0; i < validItemsList.size(); i++)
				{
					if (validItemsList.get(i) != null)
					{
						if (ItemStack.areItemStacksEqual(validItemsList.get(i), inputStack))
							validItemsList.remove(i--);
					}
				}
			}
		}
		
		@Override
		public String describeUndo() 
		{
			return "Removing item '" + inputStack.getDisplayName() + "' from the Quern yeilding '" + outputStack.getDisplayName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class removeQuernAction implements IUndoableAction 
	{
		private ItemStack inputStack;
		private ItemStack outputStack;
		
		public removeQuernAction(ItemStack output, ItemStack input)
		{
			this.outputStack = output;
			this.inputStack = input;
		}
		
		@Override
		public void apply() 
		{
			List<QuernRecipe> quernList = QuernManager.getInstance().getRecipes();
			for (int i = 0; i < quernList.size(); i++)
			{
				if (quernList.get(i) != null)
				{
					if (quernList.get(i).isInItem(inputStack) && ItemStack.areItemStacksEqual(quernList.get(i).getResult(), outputStack))
						quernList.remove(i--);
				}
			}
			
			if(QuernManager.getInstance().findMatchingRecipe(inputStack) == null && QuernManager.getInstance().isValidItem(inputStack))
			{
				List<ItemStack> validItemsList = QuernManager.getInstance().getValidItems();
				for (int i = 0; i < validItemsList.size(); i++)
				{
					if (validItemsList.get(i) != null)
					{
						if (ItemStack.areItemStacksEqual(validItemsList.get(i), inputStack))
							validItemsList.remove(i--);
					}
				}
			}
		}
		
		@Override
		public String describe() 
		{
			return "Removing item '" + inputStack.getDisplayName() + "' from the Quern yeilding '" + outputStack.getDisplayName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			QuernManager.getInstance().addRecipe(new QuernRecipe(inputStack, outputStack));
		}
		
		@Override
		public String describeUndo() 
		{
			return "Adding item '" + inputStack.getDisplayName() + "' to the Quern to yeild '" + outputStack.getDisplayName() + "'";
		}
		
		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
