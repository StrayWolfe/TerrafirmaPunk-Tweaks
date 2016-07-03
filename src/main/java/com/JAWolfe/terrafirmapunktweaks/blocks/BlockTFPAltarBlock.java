package com.JAWolfe.terrafirmapunktweaks.blocks;

import com.JAWolfe.terrafirmapunktweaks.TerraFirmaPunkTweaks;
import com.JAWolfe.terrafirmapunktweaks.reference.GUIs;
import com.sirolf2009.necromancy.block.BlockAltarBlock;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTFPAltarBlock extends BlockAltarBlock
{
	public BlockTFPAltarBlock()
	{
		super();
	}
	
	 private TileEntity getTileEntity(World par1World, int x, int y, int z)
	    {
	        switch (par1World.getBlockMetadata(x, y, z))
	        {
	        case 2:
	            if (par1World.getBlock(x, y, z + 1) == this)
	                return par1World.getTileEntity(x, y, z + 2);
	            else
	                return par1World.getTileEntity(x, y, z + 1);
	        case 0:
	            if (par1World.getBlock(x, y, z - 1) == this)
	                return par1World.getTileEntity(x, y, z - 2);
	            else
	                return par1World.getTileEntity(x, y, z - 1);
	        case 1:
	            if (par1World.getBlock(x + 1, y, z) == this)
	                return par1World.getTileEntity(x + 2, y, z);
	            else
	                return par1World.getTileEntity(x + 1, y, z);
	        case 3:
	            if (par1World.getBlock(x - 1, y, z) == this)
	                return par1World.getTileEntity(x - 2, y, z);
	            else
	                return par1World.getTileEntity(x - 1, y, z);
	        }
	        return null;
	    }
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
    {
        TileEntity tileEntity = getTileEntity(world, x, y, z);
        if (tileEntity == null || player.isSneaking())
            return false;
        else
        {
            player.openGui(TerraFirmaPunkTweaks.instance, GUIs.TFPALTAR.ordinal(), world, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            return true;
        }
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
	            if (world.getBlock(x, y, z + 1) == this)
	            {
	            	x1 = x; y1 = y; z1 = z + 2;
	            }
	            else
	            {
	            	x1 = x; y1 = y; z1 = z + 1;
	            }
	            break;
	        }
	        case 0:
	        {
	            if (world.getBlock(x, y, z - 1) == this)
	            {
	            	x1 = x; y1 = y; z1 = z - 2;
	            }
	            else
	            {
	            	x1 = x; y1 = y; z1 = z - 1;
	            }
	            break;
	        }
	        case 1:
	        {
	            if (world.getBlock(x + 1, y, z) == this)
	            {
	            	x1 = x + 2; y1 = y; z1 = z;
	            }
	            else
	            {
	            	x1 = x + 1; y1 = y; z1 = z;
	            }
	            break;
	        }
	        case 3:
        	{
	            if (world.getBlock(x - 1, y, z) == this)
	            {
	            	x1 = x - 2; y1 = y; z1 = z;
	            }
	            else
	            {
	            	x1 = x - 1; y1 = y; z1 = z;
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
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
    {
        
    }
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = Blocks.cobblestone.getIcon(0, 0);
	}
}
