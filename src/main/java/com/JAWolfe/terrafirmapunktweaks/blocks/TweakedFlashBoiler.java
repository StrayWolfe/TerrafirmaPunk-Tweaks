package com.JAWolfe.terrafirmapunktweaks.blocks;

import java.util.Random;

import com.JAWolfe.terrafirmapunktweaks.tiles.TEFlashBoiler;
import com.bioxx.tfc.api.TFCFluids;

import flaxbeard.steamcraft.Steamcraft;
import flaxbeard.steamcraft.SteamcraftBlocks;
import flaxbeard.steamcraft.block.BlockFlashBoiler;
import flaxbeard.steamcraft.tile.TileEntityFlashBoiler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class TweakedFlashBoiler extends BlockFlashBoiler
{
	private final Random rand = new Random();
	
	public TweakedFlashBoiler()
	{
		super();
	}
	
	@Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TEFlashBoiler();
    }
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
    {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
		
		float f = this.rand.nextFloat() * 0.8F + 0.1F;
        float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
        float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
        
		EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(SteamcraftBlocks.flashBoiler, 1, 0));
		
		float f3 = 0.05F;
        entityitem.motionX = (double) ((float) this.rand.nextGaussian() * f3);
        entityitem.motionY = (double) ((float) this.rand.nextGaussian() * f3 + 0.2F);
        entityitem.motionZ = (double) ((float) this.rand.nextGaussian() * f3);
        world.spawnEntityInWorld(entityitem);
    }
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xf, float yf, float zf) 
	{
		TileEntityFlashBoiler tileentityboiler = (TileEntityFlashBoiler) world.getTileEntity(x, y, z);
		
        if (world.getBlockMetadata(x, y, z) > 0 && tileentityboiler != null) 
        {
            ItemStack heldItem = player.getHeldItem();

            if(heldItem != null)
            {
            	if (heldItem.getItem() instanceof IFluidContainerItem) 
            	{
            		IFluidContainerItem fluidContainerItem = (IFluidContainerItem) heldItem.getItem();
            		FluidStack fluid = fluidContainerItem.getFluid(heldItem);
            		if(fluid != null && (fluid.getFluid() == FluidRegistry.WATER || fluid.getFluid() == TFCFluids.FRESHWATER))
            		{
            			int containerSpace = tileentityboiler.getTank().getCapacity() - tileentityboiler.getTank().getFluidAmount();
            			if(containerSpace > 0)
            			{
            				FluidStack drained;
            				if(!player.capabilities.isCreativeMode)
            					drained = fluidContainerItem.drain(heldItem, containerSpace, true);
            				else
            					drained = fluidContainerItem.drain(heldItem, containerSpace, false);
            				
            				if(drained != null)
            				{
            					tileentityboiler.fill(ForgeDirection.UP, new FluidStack(FluidRegistry.WATER, drained.amount), true);

            					return true;
            				}
            			}
            		}        		
            	}
            	else
            	{
            		FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(heldItem);
            		if(fluid != null && (fluid.getFluid() == FluidRegistry.WATER || fluid.getFluid() == TFCFluids.FRESHWATER))
            		{
            			int amountAdded = tileentityboiler.fill(ForgeDirection.UP, new FluidStack(FluidRegistry.WATER, fluid.amount), true);
            			if (amountAdded > 0)
            			{
            				FluidStack leftovers = fluid.copy();
            		        leftovers.amount = fluid.amount - amountAdded;
            		        
            		        ItemStack oldContainer = FluidContainerRegistry.drainFluidContainer(heldItem);
            		        
            		        if (leftovers.amount > 0) 
            		        {
            		        	ItemStack newContainer = FluidContainerRegistry.fillFluidContainer(leftovers, oldContainer);
            		        	
            		        	if(newContainer != null)
            		        		oldContainer = newContainer;
            				}
            		        
            		        if(!player.capabilities.isCreativeMode)
            		        	player.setCurrentItemOrArmor(0, oldContainer);
            		        
            		        return true;
            			}
            		}
            	}
            }

            if (!world.isRemote) 
            {

                if (tileentityboiler != null)
                    player.openGui(Steamcraft.instance, 0, world, x, y, z);

            }
            
            return true;
        }
        
        return false;
    }
}
