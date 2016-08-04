package com.JAWolfe.terrafirmapunktweaks.items;

import java.util.List;

import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemTFPOreSmall extends ItemTFPOre
{
	public ItemTFPOreSmall()
	{
		super();
		setWeight(EnumWeight.HEAVY);
		setSize(EnumSize.TINY);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) 
	{
		list.add(new ItemStack(this, 1, 0));
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[1];
		metaIcons[0] = registerer.registerIcon(References.ModID + ":" + textureFolder + metaNames[0] + " Small Ore");
	}
	
	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		switch (is.getItemDamage())
		{
			case 0: return (short) TFCOptions.smallOreUnits;
			default: return 0;
		}
	}
}
