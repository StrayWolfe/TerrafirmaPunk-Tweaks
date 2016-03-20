package com.JAWolfe.terrafirmapunktweaks.tiles;

import com.bioxx.tfc.api.TFCFluids;

import flaxbeard.steamcraft.tile.TileEntityBoiler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class TEBoiler extends TileEntityBoiler
{
	public TEBoiler()
	{
		super(50000);
	}
	
	@Override
    public void updateEntity() 
	{
        super.updateEntity();
        
        ItemStack is = this.getStackInSlot(1);
        
        if(is != null && is.getItem() != Items.water_bucket)
        {
        	if (is.getItem() instanceof IFluidContainerItem) 
        	{
        		IFluidContainerItem fluidContainerItem = (IFluidContainerItem) is.getItem();
        		FluidStack fluid = fluidContainerItem.getFluid(is);
        		if(fluid != null && (fluid.getFluid() == TFCFluids.FRESHWATER))
        		{
        			int containerSpace = getTank().getCapacity() - getTank().getFluidAmount();
        			if(containerSpace > 0)
        			{
        				FluidStack drained = fluidContainerItem.drain(is, containerSpace, true);
        				if(drained != null)
        					getTank().fill(new FluidStack(FluidRegistry.WATER, drained.amount), true);
        			}
        		}        		
        	}
        	else
        	{
        		FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(is);
        		if(fluid != null && (fluid.getFluid() == FluidRegistry.WATER || fluid.getFluid() == TFCFluids.FRESHWATER))
        		{
        			int amountAdded = getTank().fill(new FluidStack(FluidRegistry.WATER, fluid.amount), true);
        			if (amountAdded > 0)
        			{
        				FluidStack leftovers = fluid.copy();
        		        leftovers.amount = fluid.amount - amountAdded;
        		        
        		        ItemStack oldContainer = FluidContainerRegistry.drainFluidContainer(is);
        		        
        		        if (leftovers.amount > 0) 
        		        {
        		        	ItemStack newContainer = FluidContainerRegistry.fillFluidContainer(leftovers, oldContainer);
        		        	
        		        	if(newContainer != null)
        		        		oldContainer = newContainer;
        				}
        		        
        		        this.setInventorySlotContents(1, oldContainer);
        			}
        		}
        	}
        }
    }
}
