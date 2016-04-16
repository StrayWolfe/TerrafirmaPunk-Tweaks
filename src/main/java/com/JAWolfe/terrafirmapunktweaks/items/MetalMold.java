package com.JAWolfe.terrafirmapunktweaks.items;

import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class MetalMold extends ItemTerra
{
	public MetalMold()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(References.ModID + ":" + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.TINY;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return EnumWeight.LIGHT;
	}
}
