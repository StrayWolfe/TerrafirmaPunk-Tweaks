package com.JAWolfe.tfptweaks.minetweaker;

import java.util.List;

import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfptweaks.ItemHeat")
public class ItemHeat 
{		
	@ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input, int heat, int specHeat) 
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(input);
		ItemStack outputStack = MineTweakerMC.getItemStack(output);
		
		if(inputStack == null || inputStack.getItem() == null)
			MineTweakerAPI.logError("Missing InputStack");
		else if(outputStack == null || outputStack.getItem() == null)
			MineTweakerAPI.logError("Missing OutputStack");
		else if(heat < 0)
			MineTweakerAPI.logError("Item melting point cannot be less than 0");
		else if(specHeat < 0)
			MineTweakerAPI.logError("Item specific heat cannot be less than 0");
		else
			MineTweakerAPI.apply(new addHeatingAction(outputStack, inputStack, heat, specHeat));
    }
	
	@ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input, int heat) 
	{
		addRecipe(output, input, heat, 1); 
    }
	
	@ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input) 
	{
		addRecipe(output, input, 600); 
    }
	
	@ZenMethod
    public static void addRecipe(IItemStack output, IOreDictEntry oredictentry, int heat, int specHeat) 
	{
        if (oredictentry != null && oredictentry.getAmount() > 0)
        {
        	List<IItemStack> oreEntries = oredictentry.getItems();
        	for(int i = 0; i < oreEntries.size(); i++)
        	{
        		addRecipe(output, oreEntries.get(i), heat, specHeat);
        	}
        }
    }
	
	@ZenMethod
    public static void addRecipe(IItemStack output, IOreDictEntry oredictentry, int heat) 
	{
		addRecipe(output, oredictentry, heat, 1);
	}
	
	@ZenMethod
    public static void addRecipe(IItemStack output, IOreDictEntry oredictentry) 
	{
		addRecipe(output, oredictentry, 600);
	}
	
	@ZenMethod
    public static void removeRecipe(IItemStack input) 
	{
		ItemStack inputStack = MineTweakerMC.getItemStack(input);
		
		if(inputStack == null || inputStack.getItem() == null)
			MineTweakerAPI.logError("Missing InputStack");
		else
			MineTweakerAPI.apply(new removeHeatingAction(inputStack));
    }
	
	private static class addHeatingAction implements IUndoableAction 
	{
		private ItemStack inputStack;
		private ItemStack outputStack;
		private int meltingPoint;
		private int specificHeat;
		
		public addHeatingAction(ItemStack output, ItemStack input, int heat, int specHeat)
		{
			this.outputStack = output;
			this.inputStack = input;
			this.meltingPoint = heat;
			this.specificHeat = specHeat;
		}

		@Override
		public void apply() 
		{
			HeatRegistry.getInstance().addIndex(new HeatIndex(inputStack, specificHeat, meltingPoint, outputStack).setMinMax(outputStack.getItemDamage())); 
		}

		@Override
		public String describe() 
		{
			return "Adding item '" + inputStack.getDisplayName() + "' to heated items with melting point '" + meltingPoint 
					+ "' and specific heat '" + specificHeat + "' to yeild '" + outputStack.getDisplayName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}

		@Override
		public void undo() 
		{
			List<HeatIndex> heatList = HeatRegistry.getInstance().getHeatList();
			for (int i = 0; i < heatList.size(); i++)
			{
				if (heatList.get(i) != null)
				{
					if (heatList.get(i).matches(inputStack) && heatList.get(i).getOutputItem() == outputStack.getItem()
							&& heatList.get(i).meltTemp == meltingPoint && heatList.get(i).specificHeat == specificHeat)
						heatList.remove(i--);
				}
			}
		}
		
		@Override
		public String describeUndo() 
		{
			return "Removing item '" + inputStack.getDisplayName() + "' from heated items with melting point '" + meltingPoint 
					+ "' and specific heat '" + specificHeat + "' yeilding '" + outputStack.getDisplayName() + "'";
		}
		
		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class removeHeatingAction implements IUndoableAction 
	{
		private ItemStack inputStack;
		
		public removeHeatingAction(ItemStack input)
		{
			this.inputStack = input;
		}

		@Override
		public void apply() 
		{
			List<HeatIndex> heatList = HeatRegistry.getInstance().getHeatList();
			for (int i = 0; i < heatList.size(); i++)
			{
				if (heatList.get(i) != null)
				{
					if (heatList.get(i).matches(inputStack))
						heatList.remove(i--);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing item '" + inputStack.getDisplayName() + "' from Heated Items.'";
		}
		
		@Override
		public boolean canUndo() {
			return false;
		}
		
		@Override
		public void undo() {
			//Cannot undo			
		}

		@Override
		public String describeUndo() {
			//Cannot undo	
			return null;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
}
