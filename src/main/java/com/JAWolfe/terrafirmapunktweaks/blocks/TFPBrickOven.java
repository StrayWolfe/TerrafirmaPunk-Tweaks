package com.JAWolfe.terrafirmapunktweaks.blocks;

import java.util.Random;

import com.JAWolfe.terrafirmapunktweaks.TerraFirmaPunkTweaks;
import com.JAWolfe.terrafirmapunktweaks.reference.GUIs;
import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBrickOven;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TFPBrickOven extends BlockTerraContainer 
{
	@SideOnly(Side.CLIENT)
	private IIcon OvenFrontOnIcon;
	@SideOnly(Side.CLIENT)
	private IIcon OvenFrontOffIcon;
	@SideOnly(Side.CLIENT)
	private IIcon OvenSideIcon;
	
	private final Random random = new Random();
	private int OvenType;
	
	public TFPBrickOven(int ovenType)
	{
		this.OvenType = ovenType;
		this.blockHardness = 16;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		String iconPrefix = References.ModID + ":";
		OvenFrontOnIcon = register.registerIcon(iconPrefix + "TFPOvenFrontOn");
		OvenFrontOffIcon = register.registerIcon(iconPrefix + "TFPOvenFrontOff");
		OvenSideIcon = register.registerIcon(References.MODID_TFC + ":" + "Invisible");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		if (side == 1)
			return this.OvenSideIcon;
		else
		{
			if(metadata == 0 || metadata == 2)
			{
				if(side == 3)
					return this.OvenFrontOffIcon;
				else if(side == 4 || side == 5 || side == 2)
					return this.OvenSideIcon;
			}
			else if(metadata == 1)
			{
				if(side == 2)
					return this.OvenFrontOffIcon;
				else if(side == 3 || side == 4 || side == 5)
					return this.OvenSideIcon;
			}
			else if(metadata == 3)
			{
				if(side == 2 || side == 3 || side == 5)
					return this.OvenSideIcon;
				else if(side == 4)
					return this.OvenFrontOffIcon;
			}
			else if(metadata == 4)
			{
				if(side == 2 || side == 3 || side == 4)
					return this.OvenSideIcon;
				else if(side == 5)
					return this.OvenFrontOffIcon;
			}			
			else if(metadata == 5)
			{
				if(side == 2)
					return this.OvenFrontOnIcon;
				else if(side == 3 || side == 4 || side == 5)
					return this.OvenSideIcon;
			}
			else if(metadata == 6)
			{
				if(side == 3)
					return this.OvenFrontOnIcon;
				else if(side == 4 || side == 5 || side == 2)
					return this.OvenSideIcon;
			}
			else if(metadata == 7)
			{
				if(side == 2 || side == 3 || side == 5)
					return this.OvenSideIcon;
				else if(side == 4)
					return this.OvenFrontOnIcon;
			}
			else if(metadata == 8)
			{
				if(side == 2 || side == 3 || side == 4)
					return this.OvenSideIcon;
				else if(side == 5)
					return this.OvenFrontOnIcon;
			}
			
			return this.OvenFrontOffIcon;
		}
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return TFPBlocks.brickOvenRenderID;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{			
		if(world.isRemote)
			return true;	
		
		ItemStack heldItem = entityplayer.getHeldItem();
		TEBrickOven TE = (TEBrickOven)world.getTileEntity(x, y, z);
		
		if(heldItem != null && (heldItem.getItem() == TFCItems.fireStarter || heldItem.getItem() == TFCItems.flintSteel))
		{
			if(TE != null)
			{
				if(TE.fireTemp < 210 && TE.ovenItemStacks[9] != null)
				{
					TE.fireTemp = 300;
					int ss = entityplayer.inventory.getCurrentItem().stackSize;
					int dam = entityplayer.inventory.getCurrentItem().getItemDamage();
					
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
							new ItemStack(entityplayer.getCurrentEquippedItem().getItem(), ss, dam));
					
					int metadata = world.getBlockMetadata(x, y, z);
					world.setBlockMetadataWithNotify(x, y, z, metadata + 4, 3);
				}
			}
		}
		else if(heldItem != null && heldItem.getItem() == Item.getItemFromBlock(TFCBlocks.torch))
		{
			if(TE != null)
			{
				if(TE.fireTemp < 210 && TE.ovenItemStacks[9] != null)
				{
					TE.fireTemp = 300;
					int ss = entityplayer.inventory.getCurrentItem().stackSize - 1;
					
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
							new ItemStack(entityplayer.getCurrentEquippedItem().getItem(), ss));

					int metadata = world.getBlockMetadata(x, y, z);
					world.setBlockMetadataWithNotify(x, y, z, metadata + 4, 3);
				}
			}
		}
		else
		{
			if(TE != null)
			{
				entityplayer.openGui(TerraFirmaPunkTweaks.instance, GUIs.BRICKOVEN.ordinal(), world, x, y, z);
				return true;
			}
		}
	
		return true;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        if (!world.isRemote)
        {
        	 Block block1 = world.getBlock(x, y, z - 1);
             Block block2 = world.getBlock(x, y, z + 1);
             Block block3 = world.getBlock(x - 1, y, z);
             Block block4 = world.getBlock(x + 1, y, z);
             byte b0 = 3;
             
             if (block1.func_149730_j() && !block2.func_149730_j())
             {
                 b0 = 2;
             }

             if (block2.func_149730_j() && !block1.func_149730_j())
             {
                 b0 = 1;
             }

             if (block3.func_149730_j() && !block4.func_149730_j())
             {
                 b0 = 4;
             }

             if (block4.func_149730_j() && !block3.func_149730_j())
             {
                 b0 = 3;
             }

             world.setBlockMetadataWithNotify(x, y, z, b0, 2);
        }
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack stack)
    {
        int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
        	world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }

        if (l == 1)
        {
        	world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        if (l == 2)
        {
        	world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 3)
        {
        	world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        
        if (stack.hasDisplayName())
        {
            ((TEBrickOven)world.getTileEntity(x, y, z)).setCustomInventoryName(stack.getDisplayName());
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
    {
		return new TEBrickOven();
    }
	
	public int getOvenType()
	{
		return this.OvenType;
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta >= 5)
		{
			if (rand.nextInt(24) == 0)
				world.playSound(x, y, z, "fire.fire", 0.4F + (rand.nextFloat() / 2), 0.7F + rand.nextFloat(), false);
			
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.6F - 0.3F;

            if (meta == 7)
            {
            	world.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            	world.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (meta == 8)
            {
            	world.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            	world.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (meta == 5)
            {
            	world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            	world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            }
            else if (meta == 6)
            {
            	world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            	world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }
		}
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta > 4)
			return 10;
		else 
			return 0;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		if (world.getTileEntity(x, y, z) instanceof TEBrickOven)
		{
			TEBrickOven te = (TEBrickOven)world.getTileEntity(x, y, z);
			for (int i1 = 0; i1 < te.getSizeInventory(); ++i1)
			{
				ItemStack itemstack = te.getStackInSlot(i1);

				if (itemstack != null)
				{
					while (itemstack.stackSize > 0)
					{
						int j1 = this.random.nextInt(21) + 10;

						if (j1 > itemstack.stackSize)
						{
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + 0.5f, y + 0.5f, z + 0.5f, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}

						//float f3 = 0.05F;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, metadata);
	}
}
