package com.JAWolfe.terrafirmapunktweaks;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.JAWolfe.terrafirmapunktweaks.handlers.*;
import com.JAWolfe.terrafirmapunktweaks.items.TFPItems;
import com.JAWolfe.terrafirmapunktweaks.minetweaker.TFCTweaker;
import com.JAWolfe.terrafirmapunktweaks.proxy.CommonProxy;
import com.JAWolfe.terrafirmapunktweaks.reference.References;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = References.ModID, name = References.ModName, version = References.ModVersion, dependencies = References.ModDependencies)
public class TerraFirmaPunkTweaks
{    			
	@Mod.Instance(References.ModID)
	public static TerraFirmaPunkTweaks instance;
			
	@SidedProxy(clientSide = References.CLIENT_PROXY_CLASS, serverSide = References.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
			
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ConfigHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigHandler());
		
		TFPItems.initialise();
		
    	TFPBlocks.initialise();
    	
    	proxy.registerTileEntities();    	
    }
    
    @EventHandler
	public void initialize(FMLInitializationEvent event)
	{    	
    	RecipeTweaks.RecipeFixes();
    	
    	proxy.setupFluids();
    	   	
    	MinecraftForge.EVENT_BUS.register(new PlayerInteractionHandler());
    	MinecraftForge.EVENT_BUS.register(new PlayerDamageHandler());
    	
    	proxy.registerWAILA();
	}
    
    @EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{        	    	
    	TFCTweaker.postInit();
    	
    	RecipeTweaks.SC2RecipeTweaks();
	}
}
