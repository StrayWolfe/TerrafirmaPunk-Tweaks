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
				25, 1, 1000, "Multiplier used to scale vanilla magic damage to TFC levels: ");
		
		ConfigSettings.FiniteMagicDamageCutoff = config.getBoolean("EnableFiniteMagicDamageCutoff", "MagicScaling.CutOff",
				true, "Enable magic cutoff damage at specific value: ");
		
		ConfigSettings.FiniteMagicDamageCutoffValue = config.getInt("FiniteMagicDamageCutoffValue", "MagicScaling.CutOff",
				0, 0, 1000, "Value to stop applying magic damage if EnableFiniteMagicDamageCutoff is true: ");
		
		ConfigSettings.PercentMagicDamageCutoffValue = config.getInt("PercentMagicDamageCutoffValue", "MagicScaling.CutOff",
				10, 0, 100, "Percent health to stop applying magic damage if EnableFiniteMagicDamageCutoff is false: ");
				
		ConfigSettings.VanillaDamageScaling = config.getBoolean("EnableVanillaDamageScaling", "HitScaling", 
				true, "Enable vanilla hit damage to TFC scaling: ");
		
		ConfigSettings.VanillaPvPDamageScaling = config.getBoolean("EnableVanillaPvPDamageScaling", "HitScaling.PvP", 
				true, "Enable vanilla PvP hit damage to TFC scaling: ");
		
		ConfigSettings.VanillaPvPNonWeaponDamageMultipier = config.getInt("VanillaPvPNonWeaponDamageMultipier", "HitScaling.PvP",
				10, 1, 1000, "Multiplier used to scale vanilla PvP non-weapon hit damage to TFC levels: ");
		
		ConfigSettings.VanillaPvPWeaponDamageMultipier = config.getInt("VanillaPvPWeaponDamageMultipier", "HitScaling.PvP",
				25, 1, 1000, "Multiplier used to scale vanilla PvP weapon hit damage to TFC levels: ");
		
		ConfigSettings.VanillaMobDamageScaling = config.getBoolean("EnableVanillaMobDamageScaling", "HitScaling.mob", 
				true, "Enable vanilla mob hit damage to TFC scaling: ");
		
		ConfigSettings.VanillaMobDamageMultipier = config.getInt("VanillaMobDamageMultipier", "HitScaling.mob",
				50, 1, 1000, "Multiplier used to scale vanilla mob hit damage to TFC levels: ");
		
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
