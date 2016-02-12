package com.JAWolfe.tfptweaks.handlers;

import java.io.File;

import com.JAWolfe.tfptweaks.reference.ConfigSettings;
import com.JAWolfe.tfptweaks.reference.References;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler 
{
	
	public static Configuration config;
	
	public static void init(File configFile)
	{
		if(config == null)
		{
			config = new Configuration(configFile);
			loadConfig();
		}
	}
	
	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equalsIgnoreCase(References.ModID))
			loadConfig();
	}
	
	private static void loadConfig()
	{			
		ConfigSettings.VanillaChestConversion = config.getBoolean("VanillaChestConversion", Configuration.CATEGORY_GENERAL, 
				true, "Enable vanilla chest conversion: ");
		
		ConfigSettings.FleshBlockRecipe = config.getBoolean("FleshBlockRecipe", Configuration.CATEGORY_GENERAL, 
				false, "Enable Steamcraft 2 Flesh Block Recipe: ");
		
		ConfigSettings.FSPBoilerWaterFix = config.getBoolean("FSPBoilerWaterFix", Configuration.CATEGORY_GENERAL, 
				true, "Enable Flaxbeard's Steam Power Boiler TFC water fix: ");
		
		ConfigSettings.FSPFlashBoilerWaterFix = config.getBoolean("FSPFlashBoilerWaterFix", Configuration.CATEGORY_GENERAL, 
				true, "Enable Flaxbeard's Steam Power Flash Boiler TFC water fix: ");
		
		ConfigSettings.FSPWailaIntegration = config.getBoolean("FSPWailaIntegration", Configuration.CATEGORY_GENERAL, 
				true, "Enable Flaxbeard's Steam Power Waila integration: ");
		
		ConfigSettings.VanillaMagicScaling = config.getBoolean("EnableVanillaMagicScaling", "MagicScaling", 
				true, "Enable vanilla magic damage to TFC scaling: ");
		
		ConfigSettings.VanillaMagicMultipier = config.getInt("VanillaMagicMultipier", "MagicScaling",
				25, 20, 1000, "Multiplier used to scale vanilla magic damage to TFC levels: ");
		
		ConfigSettings.VanillaDamageScaling = config.getBoolean("EnableVanillaDamageScaling", "HitScaling", 
				true, "Enable vanilla hit damage to TFC scaling: ");
		
		ConfigSettings.VanillaPvPDamageScaling = config.getBoolean("EnableVanillaPvPDamageScaling", "HitScaling.PvP", 
				true, "Enable vanilla PvP hit damage to TFC scaling: ");
		
		ConfigSettings.VanillaPvPDamageMultipier = config.getInt("VanillaPvPDamageMultipier", "HitScaling.PvP",
				25, 20, 1000, "Multiplier used to scale vanilla PvP hit damage to TFC levels: ");
		
		ConfigSettings.VanillaMobDamageScaling = config.getBoolean("EnableVanillaMobDamageScaling", "HitScaling.mob", 
				true, "Enable vanilla mob hit damage to TFC scaling: ");
		
		ConfigSettings.VanillaMobDamageMultipier = config.getInt("VanillaMobDamageMultipier", "HitScaling.mob",
				50, 20, 1000, "Multiplier used to scale vanilla mob hit damage to TFC levels: ");
		
		ConfigSettings.InstantHealingScaling = config.getBoolean("EnableInstantHealingScaling", "HealScaling", 
				true, "Enable vanilla instant healing potion to TFC scaling: ");
		
		ConfigSettings.VanillaHealingMultipier = config.getInt("VanillaHealingMultipier", "HealScaling",
				50, 10, 1000, "Multiplier used to scale vanilla instant healing potion to TFC levels: ");
		
		ConfigSettings.MineTweakerSupport = config.getBoolean("MineTweakerSupport", Configuration.CATEGORY_GENERAL, 
				true, "Enable Minetweaker support for TFC: ");
		
		if(config.hasChanged())
			config.save();
	}
}
