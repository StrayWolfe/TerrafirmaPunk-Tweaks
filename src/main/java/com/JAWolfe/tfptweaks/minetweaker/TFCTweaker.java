package com.JAWolfe.tfptweaks.minetweaker;

import com.JAWolfe.tfptweaks.reference.ConfigSettings;

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
			//MineTweakerAPI.registerClass(Barrel.class);
			//MineTweakerAPI.registerClass(Anvil.class);
		}
	}
}
