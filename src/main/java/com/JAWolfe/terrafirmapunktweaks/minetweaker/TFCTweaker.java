package com.JAWolfe.terrafirmapunktweaks.minetweaker;

import com.JAWolfe.terrafirmapunktweaks.minetweaker.Buildcraft.AssemblyTable;
import com.JAWolfe.terrafirmapunktweaks.minetweaker.Forestry.Squeezer;
import com.JAWolfe.terrafirmapunktweaks.reference.ConfigSettings;

import cpw.mods.fml.common.Loader;
import minetweaker.MineTweakerAPI;

public class TFCTweaker 
{
	public static void postInit()
	{
		if (Loader.isModLoaded("MineTweaker3") && ConfigSettings.MineTweakerSupport)
		{
			MineTweakerAPI.registerClass(BrickOven.class);
			
			if(Loader.isModLoaded("BuildCraft|Core"))
			{
				MineTweakerAPI.registerClass(AssemblyTable.class);
			}
			
			if(Loader.isModLoaded("Forestry"))
			{
				MineTweakerAPI.registerClass(Squeezer.class);
			}
		}
	}
}
