package com.JAWolfe.terrafirmapunktweaks.items;

import com.bioxx.tfc.api.TFCItems;

import buildcraft.BuildCraftEnergy;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class TFPItems 
{
	public static Item CustomBucketOil;
	
	public static void initialise()
	{
		CustomBucketOil = new CustomBucketOil(BuildCraftEnergy.blockOil, TFCItems.woodenBucketEmpty).setUnlocalizedName("Wooden Bucket Oil");
		
		GameRegistry.registerItem(CustomBucketOil, CustomBucketOil.getUnlocalizedName());
	}
}
