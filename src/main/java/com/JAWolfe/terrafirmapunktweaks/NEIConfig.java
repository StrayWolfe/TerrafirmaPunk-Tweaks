package com.JAWolfe.terrafirmapunktweaks;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.JAWolfe.terrafirmapunktweaks.reference.ConfigSettings;
import com.JAWolfe.terrafirmapunktweaks.reference.References;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import steamcraft.common.init.InitBlocks;

public class NEIConfig implements IConfigureNEI
{

	@Override
	public String getName() {
		return References.ModName;
	}

	@Override
	public String getVersion() {
		return References.ModVersion;
	}

	@Override
	public void loadConfig() 
	{
		if(Loader.isModLoaded("steamcraft2") && !ConfigSettings.FleshBlockRecipe)
			API.hideItem(new ItemStack(InitBlocks.blockFlesh, 1));
		
		if(Loader.isModLoaded("Steamcraft"))
		{
			API.hideItem(new ItemStack(TFPBlocks.tweakedboiler, 1));
			API.hideItem(new ItemStack(TFPBlocks.tweakedboilerOn, 1));
			API.hideItem(new ItemStack(TFPBlocks.tweakedFlashBoiler, 1));
		}
	}

}
