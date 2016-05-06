package com.JAWolfe.terrafirmapunktweaks.minetweaker.TFC;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Crafting.LoomManager;
import com.bioxx.tfc.api.Crafting.LoomRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfptweaks.Loom")
public class Loom 
{	
	@ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input, String resource) 
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(input);
		ItemStack outputStack = MineTweakerMC.getItemStack(output);
		
		if(inputStack == null || inputStack.getItem() == null)
			MineTweakerAPI.logError("Missing InputStack");
		else if(outputStack == null || outputStack.getItem() == null)
			MineTweakerAPI.logError("Missing OutputStack");
		else if(resource != null && resource.length() == 0)
			MineTweakerAPI.logError("Missing Resource Location");
		else
			MineTweakerAPI.apply(new addLoomAction(outputStack, inputStack, resource));
	}
	
	@ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input, int overlay) 
	{
		switch(overlay)
		{
			case 1: addRecipe(output, input, Reference.MOD_ID + ":" + "blocks/String"); break;
			case 2: addRecipe(output, input, Reference.MOD_ID + ":" + "blocks/Silk"); break;
			case 3: addRecipe(output, input, Reference.MOD_ID + ":" + "blocks/Rope"); break;
			default: addRecipe(output, input, Reference.MOD_ID + ":" + "blocks/String"); break;
		}
	}
	
	@ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input) 
	{
		addRecipe(output, input, 1);
	}
	
	@ZenMethod
    public static void removeRecipe(IItemStack input) 
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(input);
		
		if(inputStack == null || inputStack.getItem() == null)
			MineTweakerAPI.logError("Missing InputStack");
		else
			MineTweakerAPI.apply(new removeLoomAction(inputStack));
    }
	
	private static class addLoomAction implements IUndoableAction 
	{
		private ItemStack inputStack;
		private ItemStack outputStack;
		private String modid;
		private String pathLocation;
		
		public addLoomAction(ItemStack output, ItemStack input, String location)
		{
			this.inputStack = input;
			this.outputStack = output;
			
			String[] itemresource = location.split(":");
			this.modid = itemresource[0];
			this.pathLocation = "textures/" + itemresource[1] + ".png";
		}
		
		@Override
		public void apply() 
		{
			LoomManager.getInstance().addRecipe(new LoomRecipe(inputStack, outputStack), new ResourceLocation(modid, pathLocation));		
		}
		
		@Override
		public String describe() 
		{
			return "Adding item '" + inputStack.getDisplayName() + "' to loom yeilding '" + outputStack.getDisplayName() 
					+ "' with the resource location of '" + modid +":" + pathLocation + "'";
		}

		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			List<LoomRecipe> LoomList = LoomManager.getInstance().getRecipes();
			for (int i = 0; i < LoomList.size(); i++)
			{
				if (LoomList.get(i) != null)
				{
					if (LoomList.get(i).matches(inputStack) && LoomList.get(i).resultMatches(outputStack))
						LoomList.remove(i--);
				}
			}
		}

		@Override
		public String describeUndo() {
			return "Removing item '" + inputStack.getDisplayName() + "' from loom'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
	
	private static class removeLoomAction implements IUndoableAction 
	{
		private ItemStack inputStack;
		
		public removeLoomAction(ItemStack input)
		{
			this.inputStack = input;
		}

		@Override
		public void apply() 
		{
			List<LoomRecipe> LoomList = LoomManager.getInstance().getRecipes();
			for (int i = 0; i < LoomList.size(); i++)
			{
				if (LoomList.get(i) != null)
				{
					if (LoomList.get(i).matches(inputStack))
						LoomList.remove(i--);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing item '" + inputStack.getDisplayName() + "' from loom'";
		}
		
		@Override
		public boolean canUndo() {
			return false;
		}
		
		@Override
		public void undo() {
		}

		@Override
		public String describeUndo() {
			return null;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
