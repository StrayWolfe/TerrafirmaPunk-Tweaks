package com.JAWolfe.terrafirmapunktweaks.proxy;

import com.JAWolfe.terrafirmapunktweaks.items.TFPItems;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBoiler;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEFlashBoiler;
import com.bioxx.tfc.api.TFCItems;

import buildcraft.BuildCraftEnergy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class CommonProxy 
{
	public void registerTileEntities()
	{
		if(Loader.isModLoaded("Steamcraft"))
		{
			GameRegistry.registerTileEntity(TEBoiler.class, "TEBoiler");
			GameRegistry.registerTileEntity(TEFlashBoiler.class, "TEFlashBoiler");
		}
	}
	
	public void setupFluids()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(BuildCraftEnergy.fluidOil.getName()), new ItemStack(TFPItems.CustomBucketOil), new ItemStack(TFCItems.woodenBucketEmpty));
	}
	
	public void registerWAILA()
	{
		FMLInterModComms.sendMessage("Waila", "register", "com.JAWolfe.terrafirmapunktweaks.WAILAInfo.callbackRegister");
	}
}
