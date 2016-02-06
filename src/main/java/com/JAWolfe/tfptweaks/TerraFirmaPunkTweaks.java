package com.JAWolfe.tfptweaks;

import com.JAWolfe.tfptweaks.handlers.*;
import com.JAWolfe.tfptweaks.minetweaker.TFCTweaker;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = References.ModID, name = References.ModName, version = References.ModVersion, dependencies = References.ModDependencies)
public class TerraFirmaPunkTweaks
{    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    }
    
    @EventHandler
	public void initialize(FMLInitializationEvent event)
	{    	
    	RecipeTweaks.RecipeFixes();
    	
    	MinecraftForge.EVENT_BUS.register(new PlayerHandler());
	}
    
    @EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
    	TFCTweaker.postInit();
    	
    	RecipeTweaks.SC2RecipeTweaks();
	}
}
