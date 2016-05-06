package com.JAWolfe.terrafirmapunktweaks.tiles;

import java.util.Random;

import com.JAWolfe.terrafirmapunktweaks.handlers.FuelManager;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Interfaces.ICookableFood;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;

public class TEBrickOven extends TEFireEntity implements ISidedInventory
{
	private static final int[] slotsTop = new int[] {0, 1, 2};
    private static final int[] slotsBottom = new int[] {3, 4, 5};
    private static final int[] slotsSides = new int[] {6};
    
    public ItemStack[] ovenItemStacks = new ItemStack[10];
    private String invName;
    
	public TEBrickOven()
	{
		fuelTimeLeft = 0;
		fuelBurnTemp =  0;
		fireTemp = 0;
		maxFireTempScale = 2000;
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			careForInventorySlot(this.ovenItemStacks[0]);
			careForInventorySlot(this.ovenItemStacks[1]);
			careForInventorySlot(this.ovenItemStacks[2]);
			careForInventorySlot(this.ovenItemStacks[3]);
			careForInventorySlot(this.ovenItemStacks[4]);
			careForInventorySlot(this.ovenItemStacks[5]);
			
			cookItem(0);
			cookItem(1);
			cookItem(2);
			
			//Push the input fuel down the stack
			handleFuelStack();
			
			//Turn oven on and off. Meta 0-4 off, 5-8 on
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			
			if (fireTemp < 1 && metadata > 4)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata - 4, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else if (fireTemp >= 1 &&  metadata <= 4)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata + 4, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			
			metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			//If the fire is still burning and has fuel
			if(fuelTimeLeft > 0 && fireTemp >= 1)
			{
				if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) <= 4)
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata + 4, 3);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			else if(fuelTimeLeft <= 0 && fireTemp >= 1 && ovenItemStacks[9] != null &&
						(!worldObj.canLightningStrikeAt(xCoord, yCoord, zCoord) && !worldObj.canLightningStrikeAt(xCoord, yCoord + 1, zCoord) ||
							!worldObj.isRaining()))
			{
				if(ovenItemStacks[9] != null)
				{
					EnumFuelMaterial m = TFC_Core.getFuelMaterial(ovenItemStacks[9]);
					fuelTasteProfile = m.ordinal();
					ovenItemStacks[9] = null;
					fuelTimeLeft = m.burnTimeMax;
					fuelBurnTemp = m.burnTempMax;
				}
			}
			
			//Calculate the fire temp
			float desiredTemp = handleTemp();
			
			handleTempFlux(desiredTemp);
			
			//Here we handle the bellows
			handleAirReduction();
			
			//do a last minute check to verify stack size
			if(ovenItemStacks[3] != null)
			{
				if(ovenItemStacks[3].stackSize <= 0)
					ovenItemStacks[3].stackSize = 1;
			}

			if(ovenItemStacks[4] != null)
			{
				if(ovenItemStacks[4].stackSize <= 0)
					ovenItemStacks[4].stackSize = 1;
			}
			
			if(ovenItemStacks[5] != null)
			{
				if(ovenItemStacks[5].stackSize <= 0)
					ovenItemStacks[5].stackSize = 1;
			}

			if(fuelTimeLeft <= 0)
				TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public void careForInventorySlot(ItemStack is)
	{
		if(is != null)
		{
			HeatRegistry manager = HeatRegistry.getInstance();
			HeatIndex index = manager.findMatchingIndex(is);

			if (index != null)
			{
				float temp = TFC_ItemHeat.getTemp(is);
				if (fuelTimeLeft > 0 && is.getItem() instanceof ICookableFood)
				{
					float inc = Food.getCooked(is) + Math.min(fireTemp / 700, 2f);
					Food.setCooked(is, inc);
					temp = inc;
					if (Food.isCooked(is))
					{
						int[] cookedTasteProfile = new int[]
						{ 0, 0, 0, 0, 0 };
						Random r = new Random(((ICookableFood) is.getItem()).getFoodID() + (((int) Food.getCooked(is) - 600) / 120));
						cookedTasteProfile[0] = r.nextInt(31) - 15;
						cookedTasteProfile[1] = r.nextInt(31) - 15;
						cookedTasteProfile[2] = r.nextInt(31) - 15;
						cookedTasteProfile[3] = r.nextInt(31) - 15;
						cookedTasteProfile[4] = r.nextInt(31) - 15;
						Food.setCookedProfile(is, cookedTasteProfile);
						Food.setFuelProfile(is, EnumFuelMaterial.getFuelProfile(fuelTasteProfile));
					}
				}
				else if (fireTemp > temp && index.hasOutput())
				{
					temp += TFC_ItemHeat.getTempIncrease(is);
				}
				else
					temp -= TFC_ItemHeat.getTempDecrease(is);
				TFC_ItemHeat.setTemp(is, temp);
			}
		}
	}
	
	public void cookItem(int slot)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random r = new Random();
		if(ovenItemStacks[slot] != null)
		{
			HeatIndex index = manager.findMatchingIndex(ovenItemStacks[slot]);
			if(index != null && TFC_ItemHeat.getTemp(ovenItemStacks[slot]) > index.meltTemp)
			{
				ItemStack output = index.getOutput(ovenItemStacks[slot], r);
				ItemCookEvent eventMelt = new ItemCookEvent(ovenItemStacks[slot], output, this);
				MinecraftForge.EVENT_BUS.post(eventMelt);
				output = eventMelt.result;
				int damage = 0;
				ItemStack mold = null;
				if(output != null)
				{
					damage = output.getItemDamage();
					if(output.getItem() == ovenItemStacks[slot].getItem())
						damage = ovenItemStacks[slot].getItemDamage();

					//If the input is unshaped metal
					if(ovenItemStacks[slot].getItem() instanceof ItemMeltedMetal)
					{
						//if both output slots are empty then just lower the input item into the first output slot
						if(ovenItemStacks[3] == null && ovenItemStacks[4] == null && ovenItemStacks[5] == null)
						{
							ovenItemStacks[3] = ovenItemStacks[slot].copy();
							ovenItemStacks[slot] = null;
							return;
						}
						//Otherwise if the first output has an item that doesnt match the input item then put the item in the second output slot
						else if(ovenItemStacks[3] != null && ovenItemStacks[3].getItem() != TFCItems.ceramicMold && 
								(ovenItemStacks[3].getItem() != ovenItemStacks[slot].getItem() || ovenItemStacks[3].getItemDamage() == 0))
						{
							if(ovenItemStacks[4] == null)
							{
								ovenItemStacks[4] = ovenItemStacks[slot].copy();
								ovenItemStacks[slot] = null;
								return;
							}
						}
						//Otherwise if both the first and second outputs have items that doesnt match the input item then put the item in the third output slot
						else if(ovenItemStacks[4] != null && ovenItemStacks[4].getItem() != TFCItems.ceramicMold && 
								(ovenItemStacks[4].getItem() != ovenItemStacks[slot].getItem() || ovenItemStacks[4].getItemDamage() == 0))
						{
							if(ovenItemStacks[5] == null)
							{
								ovenItemStacks[5] = ovenItemStacks[slot].copy();
								ovenItemStacks[slot] = null;
								return;
							}
						}
						mold = new ItemStack(TFCItems.ceramicMold, 1);
						mold.stackSize = 1;
						mold.setItemDamage(1);
					}
				}
				//Morph the input
				float temp = TFC_ItemHeat.getTemp(ovenItemStacks[slot]);
				ovenItemStacks[slot] = index.getMorph();
				if(ovenItemStacks[slot] != null && manager.findMatchingIndex(ovenItemStacks[slot]) != null)
				{
					//if the input is a new item, then apply the old temperature to it
					TFC_ItemHeat.setTemp(ovenItemStacks[slot], temp);
				}

				//Check if we should combine the output with a pre-existing output
				if(output != null && output.getItem() instanceof ItemMeltedMetal)
				{
					int leftover = 0;
					boolean addLeftover = false;
					int dest = 3;
					if (ovenItemStacks[3] != null && output.getItem() == ovenItemStacks[3].getItem() && ovenItemStacks[3].getItemDamage() > 0)
					{						
						combineMetals(output, mold, 3, slot, temp, damage, leftover, addLeftover);
					}
					else if (ovenItemStacks[4] != null && output.getItem() == ovenItemStacks[4].getItem() && ovenItemStacks[4].getItemDamage() > 0)
					{
						combineMetals(output, mold, 4, slot, temp, damage, leftover, addLeftover);
						dest = 4;
					}
					else if (ovenItemStacks[5] != null && output.getItem() == ovenItemStacks[5].getItem() && ovenItemStacks[5].getItemDamage() > 0)
					{
						combineMetals(output, mold, 5, slot, temp, damage, leftover, addLeftover);
						dest = 5;
					}
					else if (ovenItemStacks[3] != null && ovenItemStacks[3].getItem() == TFCItems.ceramicMold)
					{
						ovenItemStacks[3] = output.copy();
						ovenItemStacks[3].setItemDamage(damage);

						TFC_ItemHeat.setTemp(ovenItemStacks[3], temp);
					}
					else if (ovenItemStacks[4] != null && ovenItemStacks[4].getItem() == TFCItems.ceramicMold)
					{
						ovenItemStacks[4] = output.copy();
						ovenItemStacks[4].setItemDamage(damage);

						TFC_ItemHeat.setTemp(ovenItemStacks[4], temp);
					}
					else if (ovenItemStacks[5] != null && ovenItemStacks[5].getItem() == TFCItems.ceramicMold)
					{
						ovenItemStacks[5] = output.copy();
						ovenItemStacks[5].setItemDamage(damage);

						TFC_ItemHeat.setTemp(ovenItemStacks[5], temp);
					}

					if (addLeftover)
					{
						if(ovenItemStacks[dest] != null && output.getItem() == ovenItemStacks[dest].getItem() && ovenItemStacks[dest].getItemDamage() > 0)
						{
							int amt1 = 100 - leftover;//the percentage of the output
							int amt2 = 100 - ovenItemStacks[dest].getItemDamage();//the percentage currently in the out slot
							int amt3 = amt1 + amt2;//combined amount
							int amt4 = 100 - amt3;//convert the percent back to mc damage
							if(amt4 < 0)
								amt4 = 0;//stop the infinite glitch
							ovenItemStacks[dest] = output.copy();
							ovenItemStacks[dest].setItemDamage(amt4);

							TFC_ItemHeat.setTemp(ovenItemStacks[dest], temp);
						}
						else if(ovenItemStacks[dest] != null && ovenItemStacks[dest].getItem() == TFCItems.ceramicMold)
						{
							ovenItemStacks[dest] = output.copy();
							ovenItemStacks[dest].setItemDamage(100 - leftover);
							TFC_ItemHeat.setTemp(ovenItemStacks[dest], temp);
						}
					}
				}
				else if(output != null)
				{
					if(ovenItemStacks[3] == null)
					{
						ovenItemStacks[3] = output.copy();
					}
					else if(ovenItemStacks[4] == null)
					{
						ovenItemStacks[4] = output.copy();
					}
					else if(ovenItemStacks[5] == null)
					{
						ovenItemStacks[5] = output.copy();
					}
					else if (ovenItemStacks[3] != null && ovenItemStacks[4] != null && ovenItemStacks[5] != null)
					{
						ovenItemStacks[slot] = output.copy();
					}
				}
			}
		}
	}

	private void combineMetals(ItemStack output, ItemStack mold, int outputSlot, int inputSlot, float temp, int damage, int leftover, boolean addLeftover)
	{
		int amt1 = 100 - damage;//the percentage of the output
		int amt2 = 100 - ovenItemStacks[outputSlot].getItemDamage();//the percentage currently in the out slot
		int amt3 = amt1 + amt2;//combined amount
		leftover = amt3 - 100;//assign the leftover so that we can add to the other slot if applicable
		if(leftover > 0)
			addLeftover = true;
		int amt4 = 100 - amt3;//convert the percent back to mc damage
		if(amt4 < 0)
			amt4 = 0;//stop the infinite glitch
		ovenItemStacks[outputSlot] = output.copy();
		ovenItemStacks[outputSlot].setItemDamage(amt4);

		TFC_ItemHeat.setTemp(ovenItemStacks[outputSlot], temp);

		if(ovenItemStacks[inputSlot] == null && mold != null)
			ovenItemStacks[inputSlot] = mold;
	}
	
	public void handleFuelStack()
	{
		if(ovenItemStacks[7] == null && ovenItemStacks[6] != null)
		{
			ovenItemStacks[7] = ovenItemStacks[6];
			ovenItemStacks[6] = null;
		}
		if(ovenItemStacks[8] == null && ovenItemStacks[7] != null)
		{
			ovenItemStacks[8] = ovenItemStacks[7];
			ovenItemStacks[7] = null;
		}
		if(ovenItemStacks[9] == null && ovenItemStacks[8] != null)
		{
			ovenItemStacks[9] = ovenItemStacks[8];
			ovenItemStacks[8] = null;
		}
	}
	
	@Override
	public int getSizeInventory() 
	{
		return ovenItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return this.ovenItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) 
	{		
		if (this.ovenItemStacks[slot] != null)
        {
            ItemStack itemstack;

            if (this.ovenItemStacks[slot].stackSize <= amount)
            {
                itemstack = this.ovenItemStacks[slot];
                this.ovenItemStacks[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.ovenItemStacks[slot].splitStack(amount);

                if (this.ovenItemStacks[slot].stackSize == 0)
                    this.ovenItemStacks[slot] = null;

                return itemstack;
            }
        }
        else
            return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		if (this.ovenItemStacks[slot] != null)
        {
            ItemStack itemstack = this.ovenItemStacks[slot];
            this.ovenItemStacks[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		this.ovenItemStacks[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
        	stack.stackSize = this.getInventoryStackLimit();
        }
	}

	@Override
	public String getInventoryName() 
	{
		 return this.hasCustomInventoryName() ? this.invName : "container.brickOven";
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return this.invName != null && this.invName.length() > 0;
	}
	
	public void setCustomInventoryName(String name)
    {
        this.invName = name;
    }

	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) 
	{
		if(slot == slotsTop[0] && ovenItemStacks[slotsTop[0]] == null)
		{
			return !(item.getItem() instanceof ItemOre) && HeatRegistry.getInstance().findMatchingIndex(item) != null;
		}
		else if(slot == slotsTop[1] && ovenItemStacks[slotsTop[1]] == null)
		{
			return !(item.getItem() instanceof ItemOre) && HeatRegistry.getInstance().findMatchingIndex(item) != null;
		}
		else if(slot == slotsTop[2] && ovenItemStacks[slotsTop[2]] == null)
		{
			return !(item.getItem() instanceof ItemOre) && HeatRegistry.getInstance().findMatchingIndex(item) != null;
		}
		else if(slot == slotsSides[0] && ovenItemStacks[slotsSides[0]] == null)
		{
			return FuelManager.getInstance().isFuel(item);
		}
		
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) 
	{
		return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) 
	{
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) 
	{
		return side == 0 && (slot == 3 || slot == 4 || slot == 5);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < ovenItemStacks.length; i++)
		{
			if(ovenItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				ovenItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		this.ovenItemStacks = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < ovenItemStacks.length)
				ovenItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}
}
