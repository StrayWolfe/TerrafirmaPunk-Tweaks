package com.JAWolfe.terrafirmapunktweaks.tiles;

import java.util.HashSet;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.bioxx.tfc.api.TFCFluids;

import flaxbeard.steamcraft.api.ISteamTransporter;
import flaxbeard.steamcraft.api.steamnet.SteamNetwork;
import flaxbeard.steamcraft.tile.TileEntityFlashBoiler;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public class TEFlashBoiler extends TileEntityFlashBoiler implements IFluidHandler, ISidedInventory, ISteamTransporter
{
    private static int[][] bbl = new int[][]{
        new int[]{0, 0, 0}, new int[]{1, 0, 0}, new int[]{0, 0, 1}, new int[]{1, 0, 1},
        new int[]{0, 1, 0}, new int[]{1, 1, 0}, new int[]{0, 1, 1}, new int[]{1, 1, 1}};
    private static int[][] tbl = new int[][]{
        new int[]{0, -1, 0}, new int[]{1, -1, 0}, new int[]{0, -1, 1}, new int[]{1, -1, 1},
        new int[]{0, 0, 0}, new int[]{1, 0, 0}, new int[]{0, 0, 1}, new int[]{1, 0, 1}};
    private static int[][] bbr = new int[][]{
        new int[]{-1, 0, 0}, new int[]{0, 0, 0}, new int[]{-1, 0, 1}, new int[]{0, 0, 1},
        new int[]{-1, 1, 0}, new int[]{0, 1, 0}, new int[]{-1, 1, 1}, new int[]{0, 1, 1}};
    private static int[][] tbr = new int[][]{
        new int[]{-1, -1, 0}, new int[]{0, -1, 0}, new int[]{-1, -1, 1}, new int[]{0, -1, 1},
        new int[]{-1, 0, 0}, new int[]{0, 0, 0}, new int[]{-1, 0, 1}, new int[]{0, 0, 1}};
    private static int[][] btl = new int[][]{
        new int[]{0, 0, -1}, new int[]{1, 0, -1}, new int[]{0, 0, 0}, new int[]{1, 0, 0},
        new int[]{0, 1, -1}, new int[]{1, 1, -1}, new int[]{0, 1, 0}, new int[]{1, 1, 0}};
    private static int[][] ttl = new int[][]{
        new int[]{0, -1, -1}, new int[]{1, -1, -1}, new int[]{0, -1, 0}, new int[]{1, -1, 0},
        new int[]{0, 0, -1}, new int[]{1, 0, -1}, new int[]{0, 0, 0}, new int[]{1, 0, 0}};
    private static int[][] btr = new int[][]{
        new int[]{-1, 0, -1}, new int[]{0, 0, -1}, new int[]{-1, 0, 0}, new int[]{0, 0, 0},
        new int[]{-1, 1, -1}, new int[]{0, 1, -1}, new int[]{-1, 1, 0}, new int[]{0, 1, 0}};
    private static int[][] ttr = new int[][]{
        new int[]{-1, -1, -1}, new int[]{0, -1, -1}, new int[]{-1, -1, 0}, new int[]{0, -1, 0},
        new int[]{-1, 0, -1}, new int[]{0, 0, -1}, new int[]{-1, 0, 0}, new int[]{0, 0, 0}};
	private static int[][][] validConfigs = new int[][][]{
        bbl, tbl, bbr, tbr, btl, ttl, btr, ttr};

	public TEFlashBoiler()
	{
		super();
	}
	
	@Override
    public void updateEntity() 
	{
		super.updateEntity();
		
		if (!worldObj.isRemote && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1) 
		{
			ItemStack is = this.getStackInSlot(1);
			
            if (is != null && is.getItem() != Items.water_bucket) 
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
	
	@Override
	public void destroyMultiblock() {
        updateMultiblock(this.getValidClusterFromMetadata(), false, -1);
    }
	
	@Override
	public void checkMultiblock(boolean isBreaking, int frontSide) 
	{
        if (!worldObj.isRemote)
        {
            if (!isBreaking) 
            {
                int[] validClusters = getValidClusters();

                if (validClusters.length == 1) 
                    updateMultiblock(validClusters[0], true, frontSide);
            }
        }

    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateMultiblock(int clusterIndex, boolean isMultiblock, int frontSide) {
        int[][] cluster = getClusterCoords(clusterIndex);
        HashSet<TEFlashBoiler> boilers = new HashSet();
        for (int pos = 7; pos >= 0; pos--) {
            int x = cluster[pos][0], y = cluster[pos][1], z = cluster[pos][2];
            if (worldObj.getBlock(x, y, z) == TFPBlocks.tweakedFlashBoiler) {
                worldObj.setBlockMetadataWithNotify(
                        cluster[pos][0], cluster[pos][1], cluster[pos][2],
                        isMultiblock ? pos + 1 : 0,
                        2
                );
                TEFlashBoiler boiler = (TEFlashBoiler) worldObj.getTileEntity(cluster[pos][0], cluster[pos][1], cluster[pos][2]);
                boiler.setFront(frontSide, false);
                boilers.add(boiler);

            } else {
                ////Steamcraft.log.debug("ERROR! ("+x+","+y+","+z+") is not a flashBoiler!");
            }

        }
        for (TEFlashBoiler boiler : boilers) {
            if (isMultiblock) {
                SteamNetwork.newOrJoin(boiler);
            } else {
                if (this.getNetwork() != null) {
                    this.getNetwork().split(boiler, true);
                }
            }
        }
    }
	
	private int[] getValidClusters() {
        int[] valid = new int[8];
        int[] out;
        int count = 0;
        for (int clusterIndex = 0; clusterIndex < 8; clusterIndex++) {
            if (checkCluster(validConfigs[clusterIndex]) == 8) {
                valid[count] = clusterIndex;
                count++;
            }
        }
        out = new int[count];
        for (int i = 0; i < count; i++) {
            out[i] = valid[i];
        }
        return out;
    }
	
	private int checkCluster(int[][] cluster) {
        int count = 0;
        for (int pos = 0; pos < 8; pos++) {
            int x = cluster[pos][0] + xCoord, y = cluster[pos][1] + yCoord, z = cluster[pos][2] + zCoord;
            Block b = worldObj.getBlock(x, y, z);
            if (b == TFPBlocks.tweakedFlashBoiler) {
                if (!(worldObj.getBlockMetadata(x, y, z) > 0)) {
                    count++;
                }

            }

        }

        return count;
    }
	
	private int[][] getClusterCoords(int clusterIndex) {
        int[][] cluster = validConfigs[clusterIndex];
        int[][] out = new int[8][3];
        for (int pos = 0; pos < 8; pos++) {
            out[pos] = new int[]{cluster[pos][0] + xCoord, cluster[pos][1] + yCoord, cluster[pos][2] + zCoord};
        }
        return out;
    }
	
	@Override
	public TEFlashBoiler getPrimaryTileEntity() {
        int[][] cluster = getClusterCoords(getValidClusterFromMetadata());
        int x = cluster[0][0], y = cluster[0][1], z = cluster[0][2];
        TEFlashBoiler boiler = null;
        if (worldObj.getBlock(x, y, z) == TFPBlocks.tweakedFlashBoiler && worldObj.getBlockMetadata(x, y, z) > 0) {
            boiler = (TEFlashBoiler) worldObj.getTileEntity(x, y, z);
        }

        return boiler;
    }
	
	private int getValidClusterFromMetadata() {
        int validCluster = -1;
        // Because the clusters at the top are doofy and not in the right order =\
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) {
            case 1:
                validCluster = 0;
                break;
            case 2:
                validCluster = 2;
                break;
            case 3:
                validCluster = 4;
                break;
            case 4:
                validCluster = 6;
                break;
            case 5:
                validCluster = 1;
                break;
            case 6:
                validCluster = 3;
                break;
            case 7:
                validCluster = 5;
                break;
            case 8:
                validCluster = 7;
                break;
        }

        return validCluster;
    }
	
	@Override
	public boolean isInCluster(int x, int y, int z) {
        int[][] cluster = this.getClusterCoords(this.getValidClusterFromMetadata());
        for (int pos = 0; pos < cluster.length; pos++) {
            if (x == cluster[pos][0] && y == cluster[pos][1] && z == cluster[pos][1]) {
                return worldObj.getBlock(x, y, z) == TFPBlocks.tweakedFlashBoiler && worldObj.getBlockMetadata(x, y, z) > 0;
            }
        }
        return false;
    }
}
