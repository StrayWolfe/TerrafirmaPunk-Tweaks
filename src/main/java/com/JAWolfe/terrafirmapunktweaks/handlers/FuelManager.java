package com.JAWolfe.terrafirmapunktweaks.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class FuelManager 
{
	private static final FuelManager INSTANCE = new FuelManager();
	public static final FuelManager  getInstance()
	{
		return INSTANCE;
	}
	
	private List<ItemStack> fuels;
	
	private FuelManager()
	{
		fuels = new ArrayList<ItemStack>();
	}
	
	public void addFuel(ItemStack fuel)
	{
		for(int i = 0; i < fuels.size(); i++)
		{
			if (fuels.get(i) != null && matches(fuel, fuels.get(i)))
				return;
		}
		
		this.fuels.add(fuel);
	}
	
	public void removeFuel(ItemStack fuel)
	{
		for(int i = 0; i < fuels.size(); i++)
		{			
			if (fuels.get(i) != null && matches(fuel, fuels.get(i)))		
				fuels.remove(i--);
		}
	}
	
	public boolean isFuel(ItemStack itemstack)
	{
		for(int i = 0; i < fuels.size(); i++)
		{
			if (fuels.get(i) != null && matches(itemstack, fuels.get(i)))
				return true;
		}
		
		return false;
	}
	
	public List<ItemStack> getFuels()
	{
		return this.fuels;
	}
	
	private Boolean matches(ItemStack item1, ItemStack item2)
	{
		boolean iStack = item1 != null && item2 != null;

		boolean itemsEqual = OreDictionary.itemMatches(item1, item2, true);

		return iStack && itemsEqual;
	}
}
