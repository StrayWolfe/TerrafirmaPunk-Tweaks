package com.JAWolfe.terrafirmapunktweaks.items;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEAltar;
import com.bioxx.tfc.BlockSetup;
import com.bioxx.tfc.Blocks.Terrain.BlockCobble;
import com.bioxx.tfc.Items.ItemTerra;
import com.sirolf2009.necromancy.achievement.AchievementNecromancy;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import steamcraft.common.blocks.machines.BlockLightningRod;

public class TFPNecronomicon extends ItemTerra
{
	public TFPNecronomicon()
	{
		super();
		setMaxStackSize(1);
	}
	
	@Override
    public boolean onItemUse(ItemStack usedItem, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (world.getBlock(x, y, z) == BlockSetup.planks || world.getBlock(x, y, z) == BlockSetup.planks2)
        {
            if (world.getBlock(x + 1, y, z) instanceof BlockCobble && world.getBlock(x + 2, y, z) instanceof BlockCobble)
            {
            	if(Loader.isModLoaded("Steamcraft"))
            	{
            		if(world.getBlock(x + 4, y, z + 2) instanceof BlockLightningRod && world.getBlock(x - 2, y, z + 2) instanceof BlockLightningRod &&
                    world.getBlock(x + 4, y, z - 2) instanceof BlockLightningRod && world.getBlock(x - 2, y, z - 2) instanceof BlockLightningRod)
            		{
            			setLightningRods(3, x + 4, x - 2, x + 4, x - 2, z + 2, z + 2, z - 2, z - 2, y, world);
            			setBlocks(3, x + 1, z, x + 2, z, world, x, y, z, player, usedItem);
            		}
            	}
            	else
            		setBlocks(3, x + 1, z, x + 2, z, world, x, y, z, player, usedItem);
            	
                return true;
                
            }
            if (world.getBlock(x - 1, y, z) instanceof BlockCobble && world.getBlock(x - 2, y, z) instanceof BlockCobble)
            {
            	if(Loader.isModLoaded("Steamcraft"))
            	{
            		if(world.getBlock(x - 4, y, z + 2) instanceof BlockLightningRod && world.getBlock(x + 2, y, z + 2) instanceof BlockLightningRod &&
                    world.getBlock(x - 4, y, z - 2) instanceof BlockLightningRod && world.getBlock(x + 2, y, z - 2) instanceof BlockLightningRod)
            		{
            			setLightningRods(1, x - 4, x + 2, x - 4, x + 2, z + 2, z + 2, z - 2, z - 2, y, world);
            			setBlocks(1, x - 1, z, x - 2, z, world, x, y, z, player, usedItem);
            		}
            	}
            	else
            		setBlocks(1, x - 1, z, x - 2, z, world, x, y, z, player, usedItem);
            	              	
                return true;
            }
            if (world.getBlock(x, y, z + 1) instanceof BlockCobble && world.getBlock(x, y, z + 2) instanceof BlockCobble)
            {
            	if(Loader.isModLoaded("Steamcraft"))
            	{
            		if(world.getBlock(x + 2, y, z + 4) instanceof BlockLightningRod && world.getBlock(x + 2, y, z - 2) instanceof BlockLightningRod &&
                    world.getBlock(x - 2, y, z + 4) instanceof BlockLightningRod && world.getBlock(x - 2, y, z - 2) instanceof BlockLightningRod)
            		{
            			setLightningRods(0, x + 2, x + 2, x - 2, x - 2, z + 4, z - 2, z + 4, z - 2, y, world);
            			setBlocks(0, x, z + 1, x, z + 2, world, x, y, z, player, usedItem);
            		}
            	}
            	else
            		setBlocks(0, x, z + 1, x, z + 2, world, x, y, z, player, usedItem);
            	
                return true;
            }
            if (world.getBlock(x, y, z - 1) instanceof BlockCobble && world.getBlock(x, y, z - 2) instanceof BlockCobble)
            {
            	if(Loader.isModLoaded("Steamcraft"))
            	{
            		if(world.getBlock(x + 2, y, z - 4) instanceof BlockLightningRod && world.getBlock(x + 2, y, z + 2) instanceof BlockLightningRod &&
                            world.getBlock(x - 2, y, z - 4) instanceof BlockLightningRod && world.getBlock(x - 2, y, z + 2) instanceof BlockLightningRod)
            		{
            			setLightningRods(2, x + 2, x + 2, x - 2, x - 2, z - 4, z + 2, z - 4, z + 2, y, world);
            			setBlocks(2, x, z - 1, x, z - 2, world, x, y, z, player, usedItem);
            		}
            	}
            	else
            		setBlocks(2, x, z - 1, x, z - 2, world, x, y, z, player, usedItem);

                return true;
            }
        }

        return false;
    }
	
	private void setBlocks(int meta, int x1, int z1, int x2, int z2, World world, int x, int y, int z, EntityPlayer player, ItemStack usedItem)
	{
		ItemStack[] blockTypes = new ItemStack[3];	        	
    	
    	blockTypes[0] = new ItemStack(Item.getItemFromBlock(world.getBlock(x, y, z)), 1, world.getBlockMetadata(x, y, z));    	
		blockTypes[1] = new ItemStack(Item.getItemFromBlock(world.getBlock(x1, y, z1)), 1, world.getBlockMetadata(x1, y, z1));
    	blockTypes[2] = new ItemStack(Item.getItemFromBlock(world.getBlock(x2, y, z2)), 1, world.getBlockMetadata(x2, y, z2));
    	
    	world.setBlock(x, y, z, TFPBlocks.tfpAltar, meta, 3);
    	world.setBlock(x1, y, z1, TFPBlocks.tfpAltarBlock, meta, 3);
    	world.setBlock(x2, y, z2, TFPBlocks.tfpAltarBlock, meta, 3);
    	player.addStat(AchievementNecromancy.AltarAchieve, 1);
        --usedItem.stackSize;
        if(world.getTileEntity(x, y, z) instanceof TEAltar)
        	((TEAltar)world.getTileEntity(x, y, z)).setBlockTypes(blockTypes);
	}
	
	private void setLightningRods(int meta, int x1, int x2, int x3, int x4, int z1, int z2, int z3, int z4, int y, World world)
	{
		world.setBlock(x1, y, z1, TFPBlocks.tfpLightningRod, meta, 3);
		world.setBlock(x2, y, z2, TFPBlocks.tfpLightningRod, meta, 3);
		world.setBlock(x3, y, z3, TFPBlocks.tfpLightningRod, meta, 3);
		world.setBlock(x4, y, z4, TFPBlocks.tfpLightningRod, meta, 3);
	}
}
