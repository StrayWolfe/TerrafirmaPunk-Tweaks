package com.JAWolfe.terrafirmapunktweaks.proxy;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBrickOvenRenderer;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{	
	public void registerTileEntities()
	{
		super.registerTileEntities();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderInformation()
	{
		TFPBlocks.brickOvenRenderID = RenderingRegistry.getNextAvailableRenderId();
				
		RenderingRegistry.registerBlockHandler(TFPBlocks.brickOvenRenderID, new TFPBrickOvenRenderer());
	}
}
