package com.JAWolfe.terrafirmapunktweaks.minetweaker;

import com.JAWolfe.terrafirmapunktweaks.minetweaker.Buildcraft.AssemblyTable;
import com.JAWolfe.terrafirmapunktweaks.minetweaker.TFC.Anvil;
import com.JAWolfe.terrafirmapunktweaks.minetweaker.TFC.Barrel;
import com.JAWolfe.terrafirmapunktweaks.minetweaker.TFC.ItemHeat;
import com.JAWolfe.terrafirmapunktweaks.minetweaker.TFC.Loom;
import com.JAWolfe.terrafirmapunktweaks.minetweaker.TFC.Quern;
import com.JAWolfe.terrafirmapunktweaks.reference.ConfigSettings;

import cpw.mods.fml.common.Loader;
import minetweaker.MineTweakerAPI;

public class TFCTweaker 
{
	public static void postInit()
	{
		if (Loader.isModLoaded("MineTweaker3") && ConfigSettings.MineTweakerSupport)
		{
			MineTweakerAPI.registerClass(ItemHeat.class);
			MineTweakerAPI.registerClass(Loom.class);
			MineTweakerAPI.registerClass(Quern.class);
			MineTweakerAPI.registerClass(Barrel.class);
			MineTweakerAPI.registerClass(Anvil.class);
		}
		
		if(Loader.isModLoaded("BuildCraft|Core"))
		{
			MineTweakerAPI.registerClass(AssemblyTable.class);
		}
	}
}
