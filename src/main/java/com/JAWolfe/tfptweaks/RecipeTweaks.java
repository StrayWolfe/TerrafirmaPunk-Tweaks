package com.JAWolfe.tfptweaks;

import java.util.List;

import cpw.mods.fml.common.Loader;
import flaxbeard.steamcraft.SteamcraftItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import steamcraft.common.init.InitBlocks;
import steamcraft.common.init.InitItems;

public class RecipeTweaks 
{
	@SuppressWarnings("unchecked")
	public static void SC2RecipeTweaks()
	{
		if(Loader.isModLoaded("steamcraft2"))
		{
			List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
			for (int i = 0; i < recipes.size(); i++)
			{
				if (recipes.get(i) != null)
				{
					ItemStack recipeResult = recipes.get(i).getRecipeOutput();
					
					if (recipeResult != null && recipeResult.getItem() == Item.getItemFromBlock(InitBlocks.blockFlesh))
						recipes.remove(i--);
				}
			}
			
			OreDictionary.registerOre("itemAxe", new ItemStack(InitItems.axeSteam, 1, OreDictionary.WILDCARD_VALUE));
		}
	}
	
	public static void RecipeFixes()
	{
		if(Loader.isModLoaded("Steamcraft"))
		{
			OreDictionary.registerOre("itemAxe", new ItemStack(SteamcraftItems.steamAxe, 1, OreDictionary.WILDCARD_VALUE));
		}      
	}
}
