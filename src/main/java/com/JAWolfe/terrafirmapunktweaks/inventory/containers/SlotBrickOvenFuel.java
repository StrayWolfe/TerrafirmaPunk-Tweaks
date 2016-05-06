package com.JAWolfe.terrafirmapunktweaks.inventory.containers;

import com.JAWolfe.terrafirmapunktweaks.handlers.FuelManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBrickOvenFuel extends Slot
{
	public SlotBrickOvenFuel(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return FuelManager.getInstance().isFuel(itemstack);
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public void putStack(ItemStack par1ItemStack)
	{
		if (par1ItemStack != null) par1ItemStack.stackSize = 1;
		super.putStack(par1ItemStack);
	}
}
