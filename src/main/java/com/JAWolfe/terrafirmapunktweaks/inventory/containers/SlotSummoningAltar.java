package com.JAWolfe.terrafirmapunktweaks.inventory.containers;

import com.sirolf2009.necromancy.item.ItemBodyPart;
import com.sirolf2009.necromancy.item.ItemGeneric;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSummoningAltar extends Slot
{
	public SlotSummoningAltar(IInventory iinventory, int slotID, int x, int y)
	{
		super(iinventory, slotID, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{        
        if(getSlotIndex() == 0 && itemstack.getItem() instanceof ItemGeneric && itemstack.getItemDamage() == 2)
        	return true;
        else if(getSlotIndex() == 1 && itemstack.getItem() instanceof ItemGeneric && itemstack.getItemDamage() == 1)
        	return true;
        else if(getSlotIndex() == 2 && itemstack.getItem() instanceof ItemBodyPart && ItemBodyPart.necroEntities.get(itemstack.getItemDamage()).contains("Head"))
        	return true;
        else if(getSlotIndex() == 3 && itemstack.getItem() instanceof ItemBodyPart && ItemBodyPart.necroEntities.get(itemstack.getItemDamage()).contains("Torso"))
        	return true;
        else if(getSlotIndex() == 4 && itemstack.getItem() instanceof ItemBodyPart && ItemBodyPart.necroEntities.get(itemstack.getItemDamage()).contains("Legs"))
        	return true;
        else if(getSlotIndex() == 5 && itemstack.getItem() instanceof ItemBodyPart && ItemBodyPart.necroEntities.get(itemstack.getItemDamage()).contains("Arm"))
        	return true;
        else if(getSlotIndex() == 6 && itemstack.getItem() instanceof ItemBodyPart && ItemBodyPart.necroEntities.get(itemstack.getItemDamage()).contains("Arm"))
        	return true;
        
		return false;
	}
	
	@Override
	public int getSlotStackLimit()
	{
			return 1;
	}
}
