package com.JAWolfe.tfptweaks.proxy;

import com.JAWolfe.tfptweaks.tiles.TEBoiler;
import com.JAWolfe.tfptweaks.tiles.TEFlashBoiler;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy 
{
	public void registerTileEntities()
	{
		if(Loader.isModLoaded("Steamcraft"))
		{
			GameRegistry.registerTileEntity(TEBoiler.class, "TEBoiler");
			GameRegistry.registerTileEntity(TEFlashBoiler.class, "TEFlashBoiler");
		}
	}
	
	public void registerWAILA()
	{
		FMLInterModComms.sendMessage("Waila", "register", "com.JAWolfe.tfptweaks.WAILAInfo.callbackRegister");
	}
}
