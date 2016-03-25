package com.JAWolfe.terrafirmapunktweaks.handlers;

import java.util.Random;

import com.JAWolfe.terrafirmapunktweaks.blocks.TFPBlocks;
import com.JAWolfe.terrafirmapunktweaks.items.TFPItems;
import com.JAWolfe.terrafirmapunktweaks.reference.ConfigSettings;
import com.JAWolfe.terrafirmapunktweaks.reference.ReferenceLists;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemMetalSheet;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.ItemUnfinishedArmor;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;

import buildcraft.BuildCraftEnergy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import flaxbeard.steamcraft.SteamcraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PlayerInteractionHandler 
{
	@SubscribeEvent
	public void onToolTip(ItemTooltipEvent event)
	{
		ItemStack object = event.itemStack;
		
		/*if(!(object.getItem() instanceof ISize))
			event.toolTip.add("\u2696" + TFC_Core.translate("gui.Weight." + EnumWeight.LIGHT.getName()) + " \u21F2" + 
				TFC_Core.translate("gui.Size." + EnumSize.VERYSMALL.getName().replace(" ", "")));*/
		
		if(!(object.getItem() instanceof ItemTerra || object.getItem() instanceof ItemTerraBlock))
		{
			if (object.hasTagCompound())
			{
				if(TFC_ItemHeat.hasTemp(object))
				{
					float temp = TFC_ItemHeat.getTemp(object);
					float meltTemp = -1;
					HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(object);
					if(hi != null)
						meltTemp = hi.meltTemp;
	
					if(meltTemp != -1)
					{
						event.toolTip.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
					}
				}
			}
		}
		
		if(!(object.getItem() instanceof ItemIngot ||
			object.getItem() instanceof ItemMetalSheet ||
			object.getItem() instanceof ItemUnfinishedArmor ||
			object.getItem() instanceof ItemBloom ||
			object.getItem() == TFCItems.wroughtIronKnifeHead))
		{
			if (object.hasTagCompound())
			{
				if(TFC_ItemHeat.hasTemp(object))
				{
					
					if(ReferenceLists.getInstance().isAnvilInged(object))
					{
						String s = "";
						if(HeatRegistry.getInstance().isTemperatureDanger(object))
						{
							s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.danger") + " | ";
						}
		
						if(HeatRegistry.getInstance().isTemperatureWeldable(object))
						{
							s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.weldable") + " | ";
						}
		
						if(HeatRegistry.getInstance().isTemperatureWorkable(object))
						{
							s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.workable");
						}
		
						if (!"".equals(s))
							event.toolTip.add(s);
					}
				}
			}
		}
		
		if(!(object.getItem() instanceof ItemTerra))
		{
			if (object.hasTagCompound())
			{
				if (object.getTagCompound().hasKey(TEAnvil.ITEM_CRAFTING_VALUE_TAG) || object.getTagCompound().hasKey(TEAnvil.ITEM_CRAFTING_RULE_1_TAG))
					event.toolTip.add(TFC_Core.translate("gui.ItemWorked"));
			}
		}
	}
	
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event)
	{
		EntityItem item = event.item;
		ItemStack is = item.getEntityItem();
		EntityPlayer player = event.entityPlayer;
		Item droppedItem = is.getItem();
		
		if(droppedItem.equals(Item.getItemFromBlock(Blocks.chest)) && ConfigSettings.VanillaChestConversion)
		{	
			item.delayBeforeCanPickup = 100;
			item.setDead();
			item.setInvisible(true);
			Random rand = player.worldObj.rand;
			player.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			
			player.inventory.addItemStackToInventory(new ItemStack(TFCBlocks.chest, 1, 0));
		}
	}
	
	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event)
	{
		ItemStack itemInHand = event.entityPlayer.getCurrentEquippedItem();

		if(event.getResult() != Result.DENY && itemInHand.getItem() == TFCItems.woodenBucketEmpty)
		{
			if(event.result != null && event.result.getItem() == BuildCraftEnergy.bucketOil)
			{
				event.result = new ItemStack(TFPItems.CustomBucketOil);
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockPlaced(PlayerInteractEvent event)
	{
		if (event.entityPlayer.worldObj.isRemote)
			return;

		ItemStack itemInHand = event.entityPlayer.getCurrentEquippedItem();
		
		if(itemInHand == null)
			return;
		
		if(event.action == Action.RIGHT_CLICK_BLOCK && event.getResult() != Result.DENY)
		{
			if(Loader.isModLoaded("Steamcraft"))
			{
				if(event.entityPlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(SteamcraftBlocks.boiler) && ConfigSettings.FSPBoilerWaterFix)
				{
					event.setCanceled(true);
					handleBlockPlacement(event.entityPlayer, event.entityLiving, event.world, TFPBlocks.tweakedboiler, event.x, event.y, event.z, event.face);
				}			
				else if(event.entityPlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(SteamcraftBlocks.flashBoiler) && ConfigSettings.FSPFlashBoilerWaterFix)
				{
					event.setCanceled(true);
					switch(event.face)
					{
						case 0: PlaceBlock(event.entityLiving, event.world, event.x, event.y - 1, event.z, TFPBlocks.tweakedFlashBoiler, 0); break;
						case 1: PlaceBlock(event.entityLiving, event.world, event.x, event.y + 1, event.z, TFPBlocks.tweakedFlashBoiler, 0); break;
						case 2: PlaceBlock(event.entityLiving, event.world, event.x, event.y, event.z - 1, TFPBlocks.tweakedFlashBoiler, 0); break;
						case 3: PlaceBlock(event.entityLiving, event.world, event.x, event.y, event.z + 1, TFPBlocks.tweakedFlashBoiler, 0); break;
						case 4: PlaceBlock(event.entityLiving, event.world, event.x - 1, event.y, event.z, TFPBlocks.tweakedFlashBoiler, 0); break;
						case 5: PlaceBlock(event.entityLiving, event.world, event.x + 1, event.y, event.z, TFPBlocks.tweakedFlashBoiler, 0); break;
						default: break;
					}			
					
					if(event.entityPlayer.getCurrentEquippedItem().stackSize == 1)
						event.entityPlayer.setCurrentItemOrArmor(0, null);
					else
						event.entityPlayer.getCurrentEquippedItem().stackSize--;
				}
			}
		}
	}
	
	private void PlaceBlockRotation(EntityLivingBase player, World world, int x, int y, int z, Block block)
	{
		int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		int meta = 2;
		switch(l) 
		{
			case 1: meta = 5; break;
			case 2: meta = 3; break;
			case 3: meta = 4; break;
		}
		
		PlaceBlock(player, world, x, y, z, block, meta);
	}
	
	private void PlaceBlock(EntityLivingBase player, World world, int x, int y, int z, Block block, int meta)
	{		
		if(world.setBlock(x, y, z, block, meta, 3))
		{
			block.onBlockPlacedBy(world, x, y, z, player, new ItemStack(Item.getItemFromBlock(block)));
	    	block.onPostBlockPlaced(world, x, y, z, 0);
		}
	}
	
	private void handleBlockPlacement(EntityPlayer player, EntityLivingBase elb, World world, Block block, int x, int y, int z, int face)
	{
		switch(face)
		{
			case 0: PlaceBlockRotation(elb, world, x, y - 1, z, block); break;
			case 1: PlaceBlockRotation(elb, world, x, y + 1, z, block); break;
			case 2: PlaceBlockRotation(elb, world, x, y, z - 1, block); break;
			case 3: PlaceBlockRotation(elb, world, x, y, z + 1, block); break;
			case 4: PlaceBlockRotation(elb, world, x - 1, y, z, block); break;
			case 5: PlaceBlockRotation(elb, world, x + 1, y, z, block); break;
			default: break;
		}			
		
		if(player.getCurrentEquippedItem().stackSize == 1)
			player.setCurrentItemOrArmor(0, null);
		else
			player.getCurrentEquippedItem().stackSize--;
	}
}
