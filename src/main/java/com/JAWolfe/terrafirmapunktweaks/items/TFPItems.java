package com.JAWolfe.terrafirmapunktweaks.items;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.bioxx.tfc.api.TFCItems;

import buildcraft.BuildCraftEnergy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class TFPItems 
{
	public static Item CustomBucketOil;
	public static Item CustomBucketBlood;
	
	public static void initialise()
	{
		if(Loader.isModLoaded("BuildCraft|Core"))
		{
			CustomBucketOil = new CustomBucketOil(BuildCraftEnergy.blockOil, TFCItems.woodenBucketEmpty).setUnlocalizedName("Wooden Bucket Oil");
			
			GameRegistry.registerItem(CustomBucketOil, CustomBucketOil.getUnlocalizedName());
		}
		
		if(Loader.isModLoaded("necromancy"))
		{
			CustomBucketBlood = new CustomBucketBlood(TFPBlocks.tfpBlood, TFCItems.woodenBucketEmpty).setUnlocalizedName("Wooden Bucket Blood");
			GameRegistry.registerItem(CustomBucketBlood, CustomBucketBlood.getUnlocalizedName());
		}
	}
}
