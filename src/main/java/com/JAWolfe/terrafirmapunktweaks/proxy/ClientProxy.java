package com.JAWolfe.terrafirmapunktweaks.proxy;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBrickOvenRenderer;
import com.JAWolfe.terrafirmapunktweaks.items.TFPItems;
import com.JAWolfe.terrafirmapunktweaks.items.TFPNecronomiconRenderer;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEAltar;
import com.sirolf2009.necromancy.client.renderer.tileentity.TileEntityAltarRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

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
		MinecraftForgeClient.registerItemRenderer(TFPItems.TFPNecronomicon, new TFPNecronomiconRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TEAltar.class, new TileEntityAltarRenderer());
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFPBlocks.tfpAltar), new TileEntityAltarRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFPBlocks.tfpAltarBlock), new TileEntityAltarRenderer());
	}
}
