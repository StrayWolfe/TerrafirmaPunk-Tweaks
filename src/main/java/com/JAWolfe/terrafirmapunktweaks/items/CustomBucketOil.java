package com.JAWolfe.terrafirmapunktweaks.items;

import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.bioxx.tfc.Items.Tools.ItemCustomBucket;
import com.bioxx.tfc.api.Enums.EnumSize;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class CustomBucketOil extends ItemCustomBucket
{	
	public CustomBucketOil(Block contents) 
	{
		super(contents);
		this.setFolder("tools/");
		this.setSize(EnumSize.MEDIUM);
	}
	
	public CustomBucketOil(Block contents, Item container)
	{
		this(contents);
		this.setContainerItem(container);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(References.ModID + ":" + this.getUnlocalizedName().replace("item.", ""));
	}
}
