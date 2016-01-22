package com.onewolfe.tfptweaks;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = References.ModID, name = References.ModName, version = References.ModVersion, dependencies = References.ModDependencies)
public class TerraFirmaPunkTweaks
{    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @EventHandler
	public void initialize(FMLInitializationEvent event)
	{
    	MinecraftForge.EVENT_BUS.register(new PlayerHandler());
	}
}
