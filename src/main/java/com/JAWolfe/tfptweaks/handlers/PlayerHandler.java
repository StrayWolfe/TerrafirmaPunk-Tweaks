package com.JAWolfe.tfptweaks.handlers;

import java.util.Random;

import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Events.EntityArmorCalcEvent;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
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
		if(event.entity instanceof EntityPlayer)
		{			
			if (event.source == DamageSource.onFire || event.source == DamageSource.fall || event.source == DamageSource.drown ||
					event.source == DamageSource.lava || event.source == DamageSource.inWall || event.source == DamageSource.fallingBlock ||
					event.source.isExplosion() || event.source == DamageSource.inFire || event.source == DamageSource.starve)
				return;
			else if(event.source == DamageSource.magic && event.ammount <= 20)
			{
				event.ammount = event.ammount * 50;
				
				if(event.source == DamageSource.magic && (event.entityLiving.getHealth() - event.ammount) <= 0)
					event.setCanceled(true);
			}
			else
			{
				if(!event.source.isUnblockable())
				{
					event.ammount = applyArmorCalculations(event.entityLiving, event.source, event.ammount);
				}
				else if(event.source.isUnblockable() && event.source.getSourceOfDamage() instanceof EntityLivingBase)
				{
					float damage = (float)((EntityLivingBase)event.source.getSourceOfDamage()).getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
					
					if(damage <= 20)
						damage *= 50;
					
					event.ammount = applyArmorCalculations(event.entityLiving, event.source, damage);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onHeal(LivingHealEvent event)
	{
		if(event.amount > 1 && event.amount < 9)
			event.amount = event.amount * 50;
	}
	
	@SubscribeEvent
	public void entityAttack(LivingAttackEvent event)
	{
		if(event.entityLiving.worldObj.isRemote)
			return;
		
		if(event.entity instanceof EntityPlayerMP)
		{						
			if(event.source == DamageSource.onFire || event.source == DamageSource.fall || event.source == DamageSource.drown ||
				event.source == DamageSource.lava || event.source == DamageSource.inWall || event.source == DamageSource.fallingBlock ||
				event.source.isExplosion() || event.source == DamageSource.inFire || event.source == DamageSource.starve)
				return;
			else if(event.ammount < 20)
			{				
				event.entity.attackEntityFrom(event.source, event.ammount * 50);
			}
		}
	}
	
	protected int applyArmorCalculations(EntityLivingBase entity, DamageSource source, float originalDamage)
	{
		ItemStack[] armor = entity.getLastActiveItems();
		int pierceRating = 0;
		int slashRating = 0;
		int crushRating = 0;

		EntityArmorCalcEvent eventPre = new EntityArmorCalcEvent(entity, originalDamage, EntityArmorCalcEvent.EventType.PRE);
		MinecraftForge.EVENT_BUS.post(eventPre);
		float damage = eventPre.incomingDamage;

		if (armor != null)
		{
			//1. Get Random Hit Location
			int location = getRandomSlot(entity.getRNG());

			//2. Get Armor Rating for armor in hit Location
			if(armor[location] != null && armor[location].getItem() instanceof ItemTFCArmor)
			{
				pierceRating = ((ItemTFCArmor)armor[location].getItem()).armorTypeTFC.getPiercingAR();
				slashRating = ((ItemTFCArmor)armor[location].getItem()).armorTypeTFC.getSlashingAR();
				crushRating = ((ItemTFCArmor)armor[location].getItem()).armorTypeTFC.getCrushingAR();
				if(entity instanceof IInnateArmor)
				{
					pierceRating += ((IInnateArmor)entity).getPierceArmor();
					slashRating += ((IInnateArmor)entity).getSlashArmor();
					crushRating += ((IInnateArmor) entity).getCrushArmor();
				}

				//3. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);

				//4. Reduce incoming damage
				damage = processDamageSource(source, damage, pierceMult,
						slashMult, crushMult);

				//5. Damage the armor that was hit
				armor[location].damageItem((int) processArmorDamage(armor[location], damage), entity);
			}
			else if (armor[location] == null || armor[location] != null && !(armor[location].getItem() instanceof ItemTFCArmor))
			{
				if(entity instanceof IInnateArmor)
				{
					pierceRating += ((IInnateArmor)entity).getPierceArmor();
					slashRating += ((IInnateArmor)entity).getSlashArmor();
					crushRating += ((IInnateArmor) entity).getCrushArmor();
				}
				//1. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);
				//4. Reduce incoming damage
				damage = processDamageSource(source, damage, pierceMult, slashMult, crushMult);

				//a. If the attack hits an unprotected head, it does 75% more damage
				//b. If the attack hits unprotected feet, it applies a slow to the player
				if(location == 3)
					damage *= 1.75f;
				else if(location == 0)
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 100, 1));
			}
			//6. Apply the damage to the player
			EntityArmorCalcEvent eventPost = new EntityArmorCalcEvent(entity, damage, EntityArmorCalcEvent.EventType.POST);
			MinecraftForge.EVENT_BUS.post(eventPost);
			//LogHelper.info(entity + " " + source.getDamageType() +", "+eventPre.incomingDamage+", "+eventPost.incomingDamage);
			float hasHealth = entity.getHealth();
			entity.setHealth(entity.getHealth()-eventPost.incomingDamage);
			entity.func_110142_aN().func_94547_a(source, hasHealth, eventPost.incomingDamage);
		}
		return 0;
	}
	
	private int getRandomSlot(Random rand)
	{
		int chance = rand.nextInt(100);
		if(chance < 10)
			return 3;//Helm
		else if(chance < 20)
			return 0;//Feet
		else if(chance < 80)
			return 2;//Chest
		else
			return 1;//Legs
	}
	
	protected float getDamageReduction(int armorRating)
	{
		if(armorRating == -1000)
			armorRating=-999;
		return 1000f / (1000f + armorRating);
	}
	
	private float processDamageSource(DamageSource source, float damage,
			float pierceMult, float slashMult, float crushMult)
	{
		EnumDamageType damageType = getDamageType(source);
		//4.2 Reduce the damage based upon the incoming Damage Type
		if(damageType == EnumDamageType.PIERCING)
		{
			damage *= pierceMult;
		}
		else if(damageType == EnumDamageType.SLASHING)
		{
			damage *= slashMult;
		}
		else if(damageType == EnumDamageType.CRUSHING)
		{
			damage *= crushMult;
		}
		else if(damageType == EnumDamageType.GENERIC)
		{
			damage *= (crushMult + slashMult + pierceMult) / 3 - 0.25;
		}
		return Math.max(0, damage);
	}
	
	private EnumDamageType getDamageType(DamageSource source) 
	{
		//4.1 Determine the source of the damage and get the appropriate Damage Type
		if(source.getSourceOfDamage() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)source.getSourceOfDamage();
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ICausesDamage)
			{
				return ((ICausesDamage)player.getCurrentEquippedItem().getItem()).getDamageType();
			}
		}

		if(source.getSourceOfDamage() instanceof EntityLiving)
		{
			EntityLiving el = (EntityLiving)source.getSourceOfDamage();
			if(el.getHeldItem() != null && el.getHeldItem().getItem() instanceof ICausesDamage)
			{
				return ((ICausesDamage)el.getHeldItem().getItem()).getDamageType();
			}
		}

		if(source.getSourceOfDamage() instanceof ICausesDamage)
		{
			return ((ICausesDamage)source.getSourceOfDamage()).getDamageType();
		}

		return EnumDamageType.GENERIC;
	}
	
	private float processArmorDamage(ItemStack armor, float baseDamage)
	{
		if(armor.hasTagCompound())
		{
			NBTTagCompound nbt = armor.getTagCompound();
			if(nbt.hasKey("armorReductionBuff"))
			{
				float reductBuff = nbt.getByte("armorReductionBuff")/100f;
				return baseDamage - (baseDamage * reductBuff);
			}
		}
		return baseDamage;
	}
}
