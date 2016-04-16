package com.JAWolfe.terrafirmapunktweaks.items;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.bioxx.tfc.api.TFCItems;

import buildcraft.BuildCraftEnergy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class TFPItems 
{
	public static Item CustomBucketOil;
	public static Item CustomBucketBlood;
	public static Item BlockMold;
	public static Item FenceMold;
	public static Item HalfSlabMold;
	public static Item MechCompMold;
	public static Item PistonMold;
	public static Item WireCoilMold;
	
	public static void initialise()
	{
		if(Loader.isModLoaded("BuildCraft|Core"))
		{
			CustomBucketOil = new CustomBucketOil(BuildCraftEnergy.blockOil, TFCItems.woodenBucketEmpty).setUnlocalizedName("Wooden Bucket Oil");
			
			GameRegistry.registerItem(CustomBucketOil, CustomBucketOil.getUnlocalizedName());
		}
		
		if(Loader.isModLoaded("necromancy"))
		{
			CustomBucketBlood = new CustomBucketBlood(TFPBlocks.tfpBlood, TFCItems.woodenBucketEmpty).setUnlocalizedName("Wooden Bucket Blood");
			GameRegistry.registerItem(CustomBucketBlood, CustomBucketBlood.getUnlocalizedName());
		}
		
		if(Loader.isModLoaded("ImmersiveEngineering"))
		{
			BlockMold = new MetalMold().setUnlocalizedName("Block Mold");
			FenceMold = new MetalMold().setUnlocalizedName("Fence Mold");
			HalfSlabMold = new MetalMold().setUnlocalizedName("Half Slab Mold");
			MechCompMold = new MetalMold().setUnlocalizedName("Mechanical Component Mold");
			PistonMold = new MetalMold().setUnlocalizedName("Piston Mold");
			WireCoilMold = new MetalMold().setUnlocalizedName("Wire Coil Mold");
			
			GameRegistry.registerItem(BlockMold, BlockMold.getUnlocalizedName());
			GameRegistry.registerItem(FenceMold, FenceMold.getUnlocalizedName());
			GameRegistry.registerItem(HalfSlabMold, HalfSlabMold.getUnlocalizedName());
			GameRegistry.registerItem(MechCompMold, MechCompMold.getUnlocalizedName());
			GameRegistry.registerItem(PistonMold, PistonMold.getUnlocalizedName());
			GameRegistry.registerItem(WireCoilMold, WireCoilMold.getUnlocalizedName());
		}
	}
}
