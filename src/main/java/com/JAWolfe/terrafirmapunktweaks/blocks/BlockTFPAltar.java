package com.JAWolfe.terrafirmapunktweaks.blocks;

import java.util.Random;

import com.JAWolfe.terrafirmapunktweaks.TerraFirmaPunkTweaks;
import com.JAWolfe.terrafirmapunktweaks.items.TFPItems;
import com.JAWolfe.terrafirmapunktweaks.reference.GUIs;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEAltar;
import com.sirolf2009.necromancy.block.BlockAltar;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steamcraft.common.init.InitBlocks;

public class BlockTFPAltar extends BlockAltar
{	
	public BlockTFPAltar()
	{
		super();
		setCreativeTab(null);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.5F, 1.0F);
	}
	
	@Override
    public TileEntity createNewTileEntity(World var1, int i)
    {
        return new TEAltar();
    }
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
    {
	 	TEAltar tileEntity = (TEAltar) world.getTileEntity(x, y, z);
        if (tileEntity == null)
            return false;
        else if (player.isSneaking() && (tileEntity.canSpawn() || player.capabilities.isCreativeMode))
        {
            tileEntity.spawn(player);
            return true;
        }
        else
        {
            player.openGui(TerraFirmaPunkTweaks.instance, GUIs.TFPALTAR.ordinal(), world, x, y, z);
            return true;
        }
    }
	
	@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		TEAltar te = (TEAltar) world.getTileEntity(x, y, z);
        Random rand = new Random();
        ItemStack[] blockTypes = null;
        if (te != null)
        {
        	blockTypes = te.getBlockTypes();
        	
            for (int i = 0; i < te.getSizeInventory(); ++i)
            {
                ItemStack var9 = te.getStackInSlot(i);

                if (var9 != null)
                {
                    float var10 = rand.nextFloat() * 0.8F + 0.1F;
                    float var11 = rand.nextFloat() * 0.8F + 0.1F;
                    float var12 = rand.nextFloat() * 0.8F + 0.1F;

                    while (var9.stackSize > 0)
                    {
                        int var13 = rand.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        EntityItem var14 =
                                new EntityItem(world, x + var10, y + var11, z + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));

                        var14.motionX = (float) rand.nextGaussian() * 0.05F;
                        var14.motionY = (float) rand.nextGaussian() * 0.05F + 0.2F;
                        var14.motionZ = (float) rand.nextGaussian() * 0.05F;
                        world.spawnEntityInWorld(var14);
                    }
                }
            }
            
            EntityItem book = new EntityItem(world, x + 0.5f, y + 0.5f, z + 0.5f, new ItemStack(TFPItems.TFPNecronomicon));
            book.motionX = (float) rand.nextGaussian() * 0.05F;
            book.motionY = (float) rand.nextGaussian() * 0.05F + 0.2F;
            book.motionZ = (float) rand.nextGaussian() * 0.05F;
            world.spawnEntityInWorld(book);
        }
        
        world.removeTileEntity(x, y, z);
        
        if(blockTypes != null && blockTypes[0] != null && blockTypes[1] != null && blockTypes[2] != null)
        {
	        world.setBlock(x, y, z, Block.getBlockFromItem(blockTypes[0].getItem()), blockTypes[0].getItemDamage(), 3);
	        switch (meta)
	        {
		        case 2:
		        {
		        	world.setBlock(x, y, z - 1, Block.getBlockFromItem(blockTypes[1].getItem()), blockTypes[1].getItemDamage(), 3);
		        	world.setBlock(x, y, z - 2, Block.getBlockFromItem(blockTypes[2].getItem()), blockTypes[2].getItemDamage(), 3);
		        	if(Loader.isModLoaded("Steamcraft"))
	            	{
		        		world.setBlock(x + 2, y, z - 4, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x + 2, y, z + 2, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x - 2, y, z - 4, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x - 2, y, z + 2, InitBlocks.blockLightningRod, 0, 3);
	            	}
		            break;
		        }
		        case 0:
		        {
		        	world.setBlock(x, y, z + 1, Block.getBlockFromItem(blockTypes[1].getItem()), blockTypes[1].getItemDamage(), 3);
		            world.setBlock(x, y, z + 2, Block.getBlockFromItem(blockTypes[2].getItem()), blockTypes[2].getItemDamage(), 3);
		            if(Loader.isModLoaded("Steamcraft"))
	            	{
		        		world.setBlock(x + 2, y, z + 4, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x + 2, y, z - 2, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x - 2, y, z + 4, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x - 2, y, z - 2, InitBlocks.blockLightningRod, 0, 3);
	            	}
		            break;
		        }
		        case 1:
		        {
		        	world.setBlock(x - 1, y, z, Block.getBlockFromItem(blockTypes[1].getItem()), blockTypes[1].getItemDamage(), 3);
		        	world.setBlock(x - 2, y, z, Block.getBlockFromItem(blockTypes[2].getItem()), blockTypes[2].getItemDamage(), 3);
		        	if(Loader.isModLoaded("Steamcraft"))
	            	{
		        		world.setBlock(x - 4, y, z + 2, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x + 2, y, z + 2, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x - 4, y, z - 2, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x + 2, y, z - 2, InitBlocks.blockLightningRod, 0, 3);
	            	}
		        	break;
		        }
		        case 3:
		        {
		        	world.setBlock(x + 1, y, z, Block.getBlockFromItem(blockTypes[1].getItem()), blockTypes[1].getItemDamage(), 3);
		        	world.setBlock(x + 2, y, z, Block.getBlockFromItem(blockTypes[2].getItem()), blockTypes[2].getItemDamage(), 3);
		        	if(Loader.isModLoaded("Steamcraft"))
	            	{
		        		world.setBlock(x + 4, y, z + 2, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x - 2, y, z + 2, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x + 4, y, z - 2, InitBlocks.blockLightningRod, 0, 3);
		        		world.setBlock(x - 2, y, z - 2, InitBlocks.blockLightningRod, 0, 3);
	            	}
		        	break;
		        }
	        }
        }
    }
	
	@Override
    public void onBlockDestroyedByPlayer(World par1World, int x, int y, int z, int par5)
    {
		
    }
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = Blocks.planks.getIcon(0, 0);
	}
}
