package com.JAWolfe.terrafirmapunktweaks.items;

import com.JAWolfe.terrafirmapunktweaks.reference.Globals;
import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCOptions;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemTFPOre extends ItemOre
{
	public ItemTFPOre()
	{
		super();
		metaNames = new String[] {"Wolframite", "Rich Wolframite", "Poor Wolframite"};
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[metaNames.length];
		for(int i = 0; i < metaNames.length; i++)
			metaIcons[i] = registerer.registerIcon(References.ModID + ":" + textureFolder + metaNames[i] + " Ore");
	}
	
	@Override
	public Metal getMetalType(ItemStack is)
	{
		switch(is.getItemDamage())
		{
			case 0: case 1: case 2: return Globals.TUNGSTEN;
			default: return null;
		}
	}
	
	@Override
	public short getMetalReturnAmount(ItemStack is) 
	{
		switch (is.getItemDamage()) 
		{
			case 0: return (short) TFCOptions.normalOreUnits;
			case 1: return (short) TFCOptions.richOreUnits;
			case 2: return (short) TFCOptions.poorOreUnits;
			default: return 0;
		}
	}
	
	@Override
	public boolean isSmeltable(ItemStack is) 
	{
		switch (is.getItemDamage()) 
		{
			case 0: case 1: case 2: return true;
			default: return false;
		}
	}
	
	@Override
	public EnumTier getSmeltTier(ItemStack is) 
	{
		switch (is.getItemDamage())
		{
			case 0: case 1: case 2: return EnumTier.TierI;
			default: return EnumTier.TierX;
		}
	}
}
