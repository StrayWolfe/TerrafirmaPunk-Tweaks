package com.JAWolfe.terrafirmapunktweaks.reference;

public class References 
{
	public static final String ModID = "TerraFirmaPunkTweaks";
	public static final String ModName = "TerraFirmaPunk Tweaks";
	
	public static final String MODID_TFC = "terrafirmacraft";
	public static final String MODNAME_TFC = "TerraFirmaCraft";
	
	public static final String MODID_SC2 = "steamcraft2";
	
	public static final String MODID_FSP = "Steamcraft";
	
	public static final String MODID_BC = "BuildCraft|Core";
	
	public static final String MODID_FORESTRY = "Forestry";

	public static final String ModVersion = "@MOD_VERSION@";
	
	public static final String ModDependencies = "required-after:" + MODID_TFC + 
												";required-after:" + MODID_SC2 +
												";required-after:" + MODID_BC +
												";required-after:" + MODID_FORESTRY +
												";required-after:" + MODID_FSP;
	
	public static final String SERVER_PROXY_CLASS = "com.JAWolfe.terrafirmapunktweaks.proxy.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "com.JAWolfe.terrafirmapunktweaks.proxy.ClientProxy";
}
