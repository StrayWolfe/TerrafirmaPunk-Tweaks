package com.JAWolfe.terrafirmapunktweaks.inventory.containers;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEAltar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerTFPAltar extends Container
{
	private TEAltar altar;
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
    
	public ContainerTFPAltar(InventoryPlayer inventory, TEAltar tileEntityAltar)
	{
		worldObj = tileEntityAltar.getWorldObj();
        posX = tileEntityAltar.xCoord;
        posY = tileEntityAltar.yCoord;
        posZ = tileEntityAltar.zCoord;
        altar = tileEntityAltar;
        addSlotToContainer(new SlotSummoningAltar(altar, 0, 26, 40)); // blood
        addSlotToContainer(new SlotSummoningAltar(altar, 1, 134, 39)); // soul
        addSlotToContainer(new SlotSummoningAltar(altar, 2, 80, 19)); // head
        addSlotToContainer(new SlotSummoningAltar(altar, 3, 80, 36)); // body
        addSlotToContainer(new SlotSummoningAltar(altar, 4, 80, 53)); // legs
        addSlotToContainer(new SlotSummoningAltar(altar, 5, 63, 36)); // right-arm
        addSlotToContainer(new SlotSummoningAltar(altar, 6, 97, 36)); // left-arm
        int var3;
        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(inventory, var3, 8 + var3 * 18, 142));
        }
	}
	
	@Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return worldObj.getBlock(posX, posY, posZ) == TFPBlocks.tfpAltar
                ? par1EntityPlayer.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64D : false;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {
        ItemStack origStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            origStack = slotStack.copy();
            
            //From altar to inventory
            if (slotIndex < 7)
            {
                if (!mergeItemStack(slotStack, 7, inventorySlots.size(), true))
                    return null;
            }
            //From inventory to altar
            else
            {
            	for(int i = 0; i < 6; i++)
            	{
            		if(((Slot) inventorySlots.get(i)).isItemValid(slotStack) && i < 5)
                	{
            			if (!mergeItemStack(slotStack, i, i + 1, true))
    	                    return null;
            			else
            				break;
                	}
            		else if(((Slot) inventorySlots.get(i)).isItemValid(slotStack))
            		{
            			if (!mergeItemStack(slotStack, i, i + 1, true))
            			{
            				if (!mergeItemStack(slotStack, i + 1, i + 2, true))
	    	                    return null;
            			}
            		}
            	}
            }
            
            if (slotStack.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
            
            if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(entityPlayer, slotStack);
        }
        return origStack;
    }
}
