package com.JAWolfe.terrafirmapunktweaks.inventory.containers;

import com.JAWolfe.terrafirmapunktweaks.handlers.FuelManager;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBrickOven;
import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Containers.Slots.SlotFirepit;
import com.bioxx.tfc.Containers.Slots.SlotFirepitIn;
import com.bioxx.tfc.Containers.Slots.SlotFirepitOut;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.api.HeatRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBrickOven extends ContainerTFC
{
	private TEBrickOven teBrickOven;
	private float firetemp;
	
	public ContainerBrickOven(InventoryPlayer inventoryplayer, TEBrickOven BrickOven)
	{
		this.teBrickOven = BrickOven;
		firetemp = -1111;
		
		//Input Slots
		addSlotToContainer(new SlotFirepitIn(inventoryplayer.player, teBrickOven, 0, 84, 19));
		addSlotToContainer(new SlotFirepitIn(inventoryplayer.player, teBrickOven, 1, 102, 19));
		addSlotToContainer(new SlotFirepitIn(inventoryplayer.player, teBrickOven, 2, 120, 19));
		
		//Output Slots
		addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, teBrickOven, 3, 84, 47));
		addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, teBrickOven, 4, 102, 47));
		addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, teBrickOven, 5, 120, 47));
		
		//Fuel Slots
		addSlotToContainer(new SlotBrickOvenFuel(inventoryplayer.player, teBrickOven, 6, 8, 7));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, teBrickOven, 7, 8, 25));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, teBrickOven, 8, 8, 43));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, teBrickOven, 9, 8, 61));
		
		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}
	
	@Override
    public ItemStack transferStackInSlotTFC(EntityPlayer entityPlayer, int slotIndex)
    {
		super.transferStackInSlotTFC(entityPlayer, slotIndex);
		ItemStack origStack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		Slot[] slotinput = {(Slot)inventorySlots.get(0), (Slot)inventorySlots.get(1), (Slot)inventorySlots.get(2)};
		Slot[] slotfuel = {(Slot)inventorySlots.get(6), (Slot)inventorySlots.get(7), (Slot)inventorySlots.get(8), (Slot)inventorySlots.get(9)};
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From firepit to inventory
			if (slotIndex < 10)
			{
				if (!this.mergeItemStack(slotStack, 10, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				// Fuel to the fuel input slot
				if (FuelManager.getInstance().isFuel(slotStack))
				{
					if(slotfuel[0].getHasStack())
						return null;
					ItemStack stack = slotStack.copy();
					stack.stackSize = 1;
					slotfuel[0].putStack(stack);
					slotStack.stackSize--;
				}
				// No ores, but anything else with a heat index to the input slot
				else if (!(slotStack.getItem() instanceof ItemOre) && HeatRegistry.getInstance().findMatchingIndex(slotStack) != null)
				{
					int openSlot = 0;
					for(openSlot = 0; openSlot <= 3; openSlot++)
					{
						if(openSlot == 3)
							return null;
						
						if(!slotinput[openSlot].getHasStack())
							break;
					}
					
					ItemStack stack = slotStack.copy();
					stack.stackSize = 1;
					slotinput[openSlot].putStack(stack);
					slotStack.stackSize--;
				}
			}

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
		{
			ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
			ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

			if (!ItemStack.areItemStacksEqual(var3, var2))
			{
				var3 = var2 == null ? null : var2.copy();
				this.inventoryItemStacks.set(var1, var3);

				for (int var4 = 0; var4 < this.crafters.size(); ++var4)
					((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
			}
		}
		
		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.firetemp != this.teBrickOven.fireTemp)
				var2.sendProgressBarUpdate(this, 0, (int)this.teBrickOven.fireTemp);
		}
		firetemp = this.teBrickOven.fireTemp;
	}
	
	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			this.teBrickOven.fireTemp = par2;
	}
}
