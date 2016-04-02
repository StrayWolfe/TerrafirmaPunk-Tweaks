package com.JAWolfe.terrafirmapunktweaks;

import java.util.List;

import com.JAWolfe.terrafirmapunktweaks.reference.ConfigSettings;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import steamcraft.common.init.InitBlocks;

public class RecipeTweaks 
{
	@SuppressWarnings("unchecked")
	public static void SC2RecipeTweaks()
	{
		if(Loader.isModLoaded("steamcraft2"))
		{
			if(!ConfigSettings.FleshBlockRecipe)
			{
				List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
				for (int i = 0; i < recipes.size(); i++)
				{
					if (recipes.get(i) != null)
					{
						ItemStack recipeResult = recipes.get(i).getRecipeOutput();
						
						if (recipeResult != null && recipeResult.getItem() == Item.getItemFromBlock(InitBlocks.blockFlesh))
						{
							recipes.remove(i--);
						}
					}
				}
			}
			
			//OreDictionary.registerOre("itemAxe", new ItemStack(InitItems.axeSteam, 1, OreDictionary.WILDCARD_VALUE));
		}
	}
	
	public static void RecipeFixes()
	{
		if(Loader.isModLoaded("Steamcraft"))
		{
			//OreDictionary.registerOre("itemAxe", new ItemStack(SteamcraftItems.steamAxe, 1, OreDictionary.WILDCARD_VALUE));
		}
	}
}
