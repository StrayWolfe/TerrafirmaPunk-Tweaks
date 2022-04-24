package com.JAWolfe.terrafirmapunktweaks;

import java.util.List;

import com.JAWolfe.terrafirmapunktweaks.reference.ConfigSettings;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBoiler;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEFlashBoiler;

import cpw.mods.fml.common.Loader;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class WAILAInfo implements IWailaDataProvider
{
	public static void callbackRegister(IWailaRegistrar reg)
	{
		if(Loader.isModLoaded("Steamcraft"))
		{
			reg.registerBodyProvider(new WAILAInfo(), TEBoiler.class);
			reg.registerNBTProvider(new WAILAInfo(), TEBoiler.class);
			
			reg.registerBodyProvider(new WAILAInfo(), TEFlashBoiler.class);
			reg.registerNBTProvider(new WAILAInfo(), TEFlashBoiler.class);
		}
	}
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return accessor.getStack();
	}
	
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}
	
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		NBTTagCompound tag = accessor.getNBTData();
		TileEntity tileEntity = accessor.getTileEntity();
		
		if(Loader.isModLoaded("Steamcraft") && ConfigSettings.FSPWailaIntegration)
		{
			if (tileEntity != null && tileEntity instanceof TEBoiler)
			{
				currenttip.add(StatCollector.translateToLocal("gui.tfptweaks.watertank") + ": " + tag.getInteger("water") + " / " + ((TEBoiler)tileEntity).myTank.getCapacity());
				currenttip.add(StatCollector.translateToLocal("gui.tfptweaks.steamtank") + ": " + tag.getInteger("steam") + " / " + ((TEBoiler)tileEntity).getCapacity());
			}
			else if (tileEntity != null && tileEntity instanceof TEFlashBoiler && ((TEFlashBoiler)tileEntity).hasPrimary())
			{
				currenttip.add(StatCollector.translateToLocal("gui.tfptweaks.watertank") + ": " + ((TEFlashBoiler)tileEntity).getPrimaryTileEntity().getTank().getFluidAmount() + " / " + ((TEFlashBoiler)tileEntity).getPrimaryTileEntity().getTank().getCapacity());
				currenttip.add(StatCollector.translateToLocal("gui.tfptweaks.steamtank") + ": "+ tag.getInteger("steam") + " / " + ((TEFlashBoiler)tileEntity).getPrimaryTileEntity().getCapacity());
			}
		}
		
		return currenttip;
	}
	
	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}
	
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}
}
