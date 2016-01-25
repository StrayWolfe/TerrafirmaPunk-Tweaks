package com.onewolfe.tfptweaks;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
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
		API.hideItem(new ItemStack(InitBlocks.blockFlesh, 1));
	}

}
