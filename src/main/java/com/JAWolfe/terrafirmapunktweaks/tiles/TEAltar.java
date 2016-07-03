package com.JAWolfe.terrafirmapunktweaks.tiles;

import com.sirolf2009.necromancy.tileentity.TileEntityAltar;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TEAltar extends TileEntityAltar
{
	ItemStack[] blockTypes = new ItemStack[3];
	
	public TEAltar()
	{
		super();
	}
	
	public void setBlockTypes(ItemStack[] types)
	{
		blockTypes = types;
	}
	
	public ItemStack[] getBlockTypes()
	{
		return blockTypes;
	}
	
	@Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < blockTypes.length; i++)
		{
			if(blockTypes[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				blockTypes[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		par1NBTTagCompound.setTag("blockTypes", nbttaglist);
    }
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("blockTypes", 10);
        blockTypes = new ItemStack[3];		
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < blockTypes.length)
				blockTypes[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
    }
}
