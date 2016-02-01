package com.onewolfe.tfptweaks;

import java.util.Random;

import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class PlayerHandler 
{
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event)
	{
		EntityItem item = event.item;
		ItemStack is = item.getEntityItem();
		EntityPlayer player = event.entityPlayer;
		Item droppedItem = is.getItem();

		if(droppedItem.equals(Item.getItemFromBlock(Blocks.chest)))
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
	public void onDamaged(LivingHurtEvent event)
	{
		if(event.entity instanceof EntityPlayer && event.source == DamageSource.magic && event.ammount <= 20)
		{
			event.ammount = event.ammount * 50;
			
			if(event.source == DamageSource.magic && (event.entityLiving.getHealth() - event.ammount) <= 0)
				event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onHeal(LivingHealEvent event)
	{
		if(event.amount > 1 && event.amount < 9)
			event.amount = event.amount * 50;
	}
}
