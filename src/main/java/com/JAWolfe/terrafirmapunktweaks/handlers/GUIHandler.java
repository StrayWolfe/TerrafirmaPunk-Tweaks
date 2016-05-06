package com.JAWolfe.terrafirmapunktweaks.handlers;

import com.JAWolfe.terrafirmapunktweaks.inventory.GUIBrickOven;
import com.JAWolfe.terrafirmapunktweaks.inventory.containers.ContainerBrickOven;
import com.JAWolfe.terrafirmapunktweaks.reference.GUIs;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBrickOven;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(ID == GUIs.BRICKOVEN.ordinal() && entity != null && entity instanceof TEBrickOven)
			return new ContainerBrickOven(player.inventory, (TEBrickOven)entity);
			
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(ID == GUIs.BRICKOVEN.ordinal() && entity != null && entity instanceof TEBrickOven)
			return new GUIBrickOven(player.inventory, (TEBrickOven)entity, world, x, y, z);
		
		return null;
	}

}
