package com.JAWolfe.terrafirmapunktweaks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import steamcraft.common.blocks.machines.BlockLightningRod;
import steamcraft.common.init.InitBlocks;

public class BlockTFPLigntningRod extends BlockLightningRod
{
	public BlockTFPLigntningRod()
	{
		super(Material.iron);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		Block altar = null;
        int metaAltar = 0, x1 = 0, y1 = 0, z1 = 0;
        
		switch (meta)
        {
	        case 2:
		    {        		
	            if (world.getBlock(x - 2, y, z + 4) instanceof BlockTFPAltar)
	            {
	            	x1 = x - 2; y1 = y; z1 = z + 4;
	            }
	            else if (world.getBlock(x - 2, y, z - 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x - 2; y1 = y; z1 = z - 2;
	            }
	            else if (world.getBlock(x + 2, y, z + 4) instanceof BlockTFPAltar)
	            {
	            	x1 = x + 2; y1 = y; z1 = z + 4;
	            }
	            else if (world.getBlock(x + 2, y, z - 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x + 2; y1 = y; z1 = z - 2;
	            }
	            break;
	        }
	        case 0:
	        {        		
	        	if (world.getBlock(x - 2, y, z - 4) instanceof BlockTFPAltar)
	            {
	            	x1 = x - 2; y1 = y; z1 = z - 4;
	            }
	            else if (world.getBlock(x - 2, y, z + 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x - 2; y1 = y; z1 = z + 2;
	            }
	            else if (world.getBlock(x + 2, y, z - 4) instanceof BlockTFPAltar)
	            {
	            	x1 = x + 2; y1 = y; z1 = z - 4;
	            }
	            else if (world.getBlock(x + 2, y, z + 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x + 2; y1 = y; z1 = z + 2;
	            }
	            break;
	        }
	        case 1:
	        {        		
	        	if (world.getBlock(x + 4, y, z - 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x + 4; y1 = y; z1 = z - 2;
	            }
	            else if (world.getBlock(x - 2, y, z - 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x - 2; y1 = y; z1 = z - 2;
	            }
	            else if (world.getBlock(x + 4, y, z + 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x + 4; y1 = y; z1 = z + 2;
	            }
	            else if (world.getBlock(x - 2, y, z + 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x - 2; y1 = y; z1 = z + 2;
	            }
	            break;
	        }
	        case 3:
        	{        		
        		if (world.getBlock(x - 4, y, z - 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x - 4; y1 = y; z1 = z - 2;
	            }
	            else if (world.getBlock(x + 2, y, z - 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x + 2; y1 = y; z1 = z - 2;
	            }
	            else if (world.getBlock(x - 4, y, z + 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x - 4; y1 = y; z1 = z + 2;
	            }
	            else if (world.getBlock(x + 2, y, z + 2) instanceof BlockTFPAltar)
	            {
	            	x1 = x + 2; y1 = y; z1 = z + 2;
	            }
	            break;
        	}
        }
		
		altar = world.getBlock(x1, y1, z1);
        metaAltar = world.getBlockMetadata(x1, y1, z1);
		
		if(altar instanceof BlockTFPAltar)
		{
        	((BlockTFPAltar)altar).breakBlock(world, x1, y1, z1, altar, metaAltar);
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = Blocks.anvil.getIcon(0, 0);
	}
}
