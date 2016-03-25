package com.JAWolfe.terrafirmapunktweaks.minetweaker.TFC;

import java.util.List;
import java.util.Random;

import com.JAWolfe.terrafirmapunktweaks.reference.ReferenceLists;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfptweaks.Anvil")
public class Anvil 
{
	@ZenMethod
	public static void addAnvilRecipe(IItemStack Output, IItemStack Input1, IItemStack Input2, String plan, int AnvilReq, int craftingValue)
	{
		ItemStack result = MineTweakerMC.getItemStack(Output);
		ItemStack input1 = MineTweakerMC.getItemStack(Input1);
		ItemStack input2 = null;
		if(Input2 != null)
			input2 = MineTweakerMC.getItemStack(Input2);
		
		MineTweakerAPI.apply(new addAnvilRecipeAction(result, input1, input2, plan, AnvilReq, craftingValue));
	}
	
	@ZenMethod
	public static void addAnvilRecipe(IItemStack Output, IItemStack Input1, String plan, int AnvilReq, int craftingValue)
	{
		addAnvilRecipe(Output, Input1, null, plan, AnvilReq, craftingValue);
	}
	
	@ZenMethod
	public static void removeAnvilRecipe(IItemStack Output, IItemStack Input1, IItemStack Input2, String plan, int AnvilReq)
	{
		ItemStack result = MineTweakerMC.getItemStack(Output);
		ItemStack input1 = MineTweakerMC.getItemStack(Input1);
		ItemStack input2 = null;
		if(Input2 != null)
			input2 = MineTweakerMC.getItemStack(Input2);
		
		MineTweakerAPI.apply(new removeAnvilRecipeAction(result, input1, input2, plan, AnvilReq));
	}
	
	@ZenMethod
	public static void removeAnvilRecipe(IItemStack Output, IItemStack Input1, String plan, int AnvilReq)
	{
		removeAnvilRecipe(Output, Input1, null, plan, AnvilReq);
	}
	
	@ZenMethod
	public static void addWeldRecipe(IItemStack Output, IItemStack Input1, IItemStack Input2, int AnvilReq)
	{
		ItemStack result = MineTweakerMC.getItemStack(Output);
		ItemStack input1 = MineTweakerMC.getItemStack(Input1);
		ItemStack input2 = MineTweakerMC.getItemStack(Input2);
		
		MineTweakerAPI.apply(new addWeldRecipeAction(result, input1, input2, AnvilReq));
	}
	
	@ZenMethod
	public static void removeWeldRecipe(IItemStack Output, IItemStack Input1, IItemStack Input2, int AnvilReq)
	{
		ItemStack result = MineTweakerMC.getItemStack(Output);
		ItemStack input1 = MineTweakerMC.getItemStack(Input1);
		ItemStack input2 = MineTweakerMC.getItemStack(Input2);
		
		MineTweakerAPI.apply(new removeWeldRecipeAction(result, input1, input2, AnvilReq));
	}
	
	@ZenMethod
	public static void addPlanRecipe(String planName, int lastHitRef, int Hit2Ref, int Hit3Ref)
	{
		MineTweakerAPI.apply(new addPlanRecipeAction(planName, lastHitRef, Hit2Ref, Hit3Ref));
	}
	
	@ZenMethod
	public static void removePlanRecipe(String planName, int lastHitRef, int Hit2Ref, int Hit3Ref)
	{
		MineTweakerAPI.apply(new removePlanRecipeAction(planName, lastHitRef, Hit2Ref, Hit3Ref));
	}
	
	private static class addAnvilRecipeAction implements IUndoableAction 
	{
		ItemStack input1;
		ItemStack input2;
		ItemStack result;
		String plan;
		int craftingvalue;
		int anvilReq;
		
		public addAnvilRecipeAction(ItemStack result, ItemStack input1, ItemStack input2, String plan, int anvilReq, int craftingValue)
		{
			this.input1 = input1;
			this.input2 = input2;
			this.result = result;
			this.plan = plan;
			this.anvilReq = anvilReq;
			this.craftingvalue = 50 + craftingValue;
		}
		
		@Override
		public void apply() 
		{
			if(input1 != null)
				ReferenceLists.getInstance().addAnvilIngred(input1);
			
			if(input2 != null)
				ReferenceLists.getInstance().addAnvilIngred(input2);
			
			AnvilManager.getInstance().addRecipe(new AnvilRecipe(input1, input2, plan, craftingvalue, false, anvilReq, result));
		}
		
		@Override
		public String describe() 
		{
			if(input2 != null)
				return "Adding '" + input1.getDisplayName() + "' with '" + input2.getDisplayName() + "' to anvil yeilding '" + result.getDisplayName() + "'";
			else
				return "Adding '" + input1.getDisplayName() + "' to anvil yeilding '" + result.getDisplayName() + "'";
		}

		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{			
			List<AnvilRecipe> AnvilList = AnvilManager.getInstance().getRecipeList();
			for (int i = 0; i < AnvilList.size(); i++)
			{
				if (AnvilList.get(i) != null)
				{
					if(input2 != null)
					{
						if (AnvilList.get(i).matches(new AnvilRecipe(input1, input2, plan, craftingvalue, false, anvilReq, result)))
							AnvilList.remove(i--);
					}
					else
						if (AnvilList.get(i).matches(new AnvilRecipe(input1, null, plan, craftingvalue, false, anvilReq, result)))
							AnvilList.remove(i--);
				}
			}
		}

		@Override
		public String describeUndo() 
		{
			if(input2 != null)
				return "Removing '" + input1.getDisplayName() + "' with '" + input2.getDisplayName() + "' to anvil yeild '" + result.getDisplayName() + "'";
			else
				return "Removing '" + input1.getDisplayName() + "' to anvil yeild '" + result.getDisplayName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
	
	private static class removeAnvilRecipeAction implements IUndoableAction 
	{
		Random r = new Random();
		ItemStack input1;
		ItemStack input2;
		ItemStack result;
		String plan;
		int anvilReq;
		
		public removeAnvilRecipeAction(ItemStack result, ItemStack input1, ItemStack input2, String plan, int anvilReq)
		{
			this.input1 = input1;
			this.input2 = input2;
			this.result = result;
			this.plan = plan;
			this.anvilReq = anvilReq;
		}
		
		@Override
		public void apply() 
		{			
			List<AnvilRecipe> AnvilList = AnvilManager.getInstance().getRecipeList();
			for (int i = 0; i < AnvilList.size(); i++)
			{
				if (AnvilList.get(i) != null)
				{
					if(input2 != null)
					{
						if (AnvilList.get(i).matches(new AnvilRecipe(input1, input2, plan, 1, false, anvilReq, result)))
							AnvilList.remove(i--);
					}
					else
						if (AnvilList.get(i).matches(new AnvilRecipe(input1, null, plan, 1, false, anvilReq, result)))
							AnvilList.remove(i--);
				}
			}
		}
		
		@Override
		public String describe() 
		{
			if(input2 != null)
				return "Removing '" + input1.getDisplayName() + "' with '" + input2.getDisplayName() + "' to anvil yeild '" + result.getDisplayName() + "'";
			else
				return "Removing '" + input1.getDisplayName() + "' to anvil yeild '" + result.getDisplayName() + "'";
		}

		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{	
			AnvilManager.getInstance().addRecipe(new AnvilRecipe(input1, input2, plan, 70 + r.nextInt(50), false, anvilReq, result));
		}

		@Override
		public String describeUndo() 
		{
			if(input2 != null)
				return "Adding '" + input1.getDisplayName() + "' with '" + input2.getDisplayName() + "' to anvil yeilding '" + result.getDisplayName() + "'";
			else
				return "Adding '" + input1.getDisplayName() + "' to anvil yeilding '" + result.getDisplayName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
	}
	
	private static class addWeldRecipeAction implements IUndoableAction 
	{
		ItemStack input1;
		ItemStack input2;
		ItemStack result;
		AnvilReq anvilReq;

		public addWeldRecipeAction(ItemStack result, ItemStack input1, ItemStack input2, int anvilReq)
		{
			this.input1 = input1;
			this.input2 = input2;
			this.result = result;
			this.anvilReq = AnvilReq.getReqFromInt(anvilReq);
		}
		
		@Override
		public void apply() 
		{
			if(input1 != null)
				ReferenceLists.getInstance().addAnvilIngred(input1);
			
			if(input2 != null)
				ReferenceLists.getInstance().addAnvilIngred(input2);
			
			AnvilManager.getInstance().addWeldRecipe(new AnvilRecipe(input1, input2, anvilReq, result));
		}

		@Override
		public String describe() 
		{
			return "Adding weld'" + input1.getDisplayName() + "' with '" + input2.getDisplayName() + "' to anvil yeilding '" + result.getDisplayName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			List<AnvilRecipe> AnvilList = AnvilManager.getInstance().getWeldRecipeList();
			for (int i = 0; i < AnvilList.size(); i++)
			{
				if (AnvilList.get(i) != null)
				{					
					if( areItemStacksEqual(input1, AnvilList.get(i).input1) && 
						areItemStacksEqual(input2, AnvilList.get(i).input2) &&
						areItemStacksEqual(result, AnvilList.get(i).result) &&
						AnvilReq.matches(anvilReq.Tier, AnvilList.get(i).anvilreq))
					{
						AnvilList.remove(i--);
					}						
				}
			}
		}

		@Override
		public String describeUndo() 
		{
			return "Removing weld'" + input1.getDisplayName() + "' with '" + input2.getDisplayName() + "' to anvil yeilding '" + result.getDisplayName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
		
		private boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
		{
			if (is1 != null && is2 != null)
			{
				if (is1.getItem() != is2.getItem())
					return false;

				if (is1.getItemDamage() != 32767 && is1.getItemDamage() != is2.getItemDamage())
					return false;
			}
			else if (is1 == null && is2 != null || is1 != null && is2 == null) // XOR, if both are null return true
				return false;

			return true;
		}
	}
	
	private static class removeWeldRecipeAction implements IUndoableAction 
	{
		ItemStack input1;
		ItemStack input2;
		ItemStack result;
		int anvilReq;

		public removeWeldRecipeAction(ItemStack result, ItemStack input1, ItemStack input2, int anvilReq)
		{
			this.input1 = input1;
			this.input2 = input2;
			this.result = result;
			this.anvilReq = anvilReq;
		}
		
		@Override
		public void apply() 
		{
			List<AnvilRecipe> AnvilList = AnvilManager.getInstance().getWeldRecipeList();
			for (int i = 0; i < AnvilList.size(); i++)
			{
				if (AnvilList.get(i) != null)
				{					
					if( areItemStacksEqual(input1, AnvilList.get(i).input1) && 
						areItemStacksEqual(input2, AnvilList.get(i).input2) &&
						areItemStacksEqual(result, AnvilList.get(i).result) &&
						AnvilReq.matches(anvilReq, AnvilList.get(i).anvilreq))
					{
						AnvilList.remove(i--);
					}						
				}
			}
		}

		@Override
		public String describe() 
		{
			return "Removing weld'" + input1.getDisplayName() + "' with '" + input2.getDisplayName() + "' to anvil yeilding '" + result.getDisplayName() + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			if(input1 != null)
				ReferenceLists.getInstance().addAnvilIngred(input1);
			
			if(input2 != null)
				ReferenceLists.getInstance().addAnvilIngred(input2);
			
			AnvilManager.getInstance().addWeldRecipe(new AnvilRecipe(input1, input2, AnvilReq.getReqFromInt(anvilReq), result));
		}

		@Override
		public String describeUndo() 
		{
			return "Adding weld'" + input1.getDisplayName() + "' with '" + input2.getDisplayName() + "' to anvil yeilding '" + result.getDisplayName() + "'";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}		
		
		private boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
		{
			if (is1 != null && is2 != null)
			{
				if (is1.getItem() != is2.getItem())
					return false;

				if (is1.getItemDamage() != 32767 && is1.getItemDamage() != is2.getItemDamage())
					return false;
			}
			else if (is1 == null && is2 != null || is1 != null && is2 == null) // XOR, if both are null return true
				return false;

			return true;
		}
	}
	
	private static class addPlanRecipeAction implements IUndoableAction 
	{
		String planName;
		RuleEnum lastHit;
		RuleEnum hit2Last;
		RuleEnum hit3Last;

		public addPlanRecipeAction(String planName, int lastHit, int hit2Last, int hit3Last)
		{
			this.lastHit = getRule(lastHit);
			this.hit2Last = getRule(hit2Last);
			this.hit3Last = getRule(hit3Last);
			this.planName = planName;
		}
		
		@Override
		public void apply() 
		{
			AnvilManager.getInstance().addPlan(planName, new PlanRecipe(new RuleEnum[]{lastHit, hit2Last, hit3Last}));
		}

		@Override
		public String describe() 
		{
			return "Adding plan '" + planName + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			AnvilManager.getInstance().getPlans().remove(planName);
		}

		@Override
		public String describeUndo() 
		{
			return "Removing plan '" + planName + "'";
		}

		@Override
		public Object getOverrideKey() 
		{
			return null;
		}
	}
	
	private static class removePlanRecipeAction implements IUndoableAction 
	{
		String planName;
		RuleEnum lastHit;
		RuleEnum hit2Last;
		RuleEnum hit3Last;

		public removePlanRecipeAction(String planName, int lastHit, int hit2Last, int hit3Last)
		{
			this.lastHit = getRule(lastHit);
			this.hit2Last = getRule(hit2Last);
			this.hit3Last = getRule(hit3Last);
			this.planName = planName;
		}
		
		@Override
		public void apply() 
		{
			AnvilManager.getInstance().getPlans().remove(planName);
		}

		@Override
		public String describe() 
		{
			return "Removing plan '" + planName + "'";
		}
		
		@Override
		public boolean canUndo() 
		{
			return true;
		}
		
		@Override
		public void undo() 
		{
			AnvilManager.getInstance().addPlan(planName, new PlanRecipe(new RuleEnum[]{lastHit, hit2Last, hit3Last}));
		}

		@Override
		public String describeUndo() 
		{
			return "Adding plan '" + planName + "'";
		}

		@Override
		public Object getOverrideKey() 
		{
			return null;
		}
	}
	
	private static RuleEnum getRule(int ruleRef)
	{
		switch(ruleRef)
		{
			case 1: return RuleEnum.ANY;
			case 2: return RuleEnum.BENDANY;
			case 3: return RuleEnum.BENDLAST;
			case 4: return RuleEnum.BENDLASTTWO;
			case 5: return RuleEnum.BENDNOTLAST;
			case 6: return RuleEnum.BENDSECONDFROMLAST;
			case 7: return RuleEnum.BENDTHIRDFROMLAST;
			case 8: return RuleEnum.DRAWANY;
			case 9: return RuleEnum.DRAWLAST;
			case 10: return RuleEnum.DRAWLASTTWO;
			case 11: return RuleEnum.DRAWNOTLAST;
			case 12: return RuleEnum.DRAWSECONDFROMLAST;
			case 13: return RuleEnum.DRAWTHIRDFROMLAST;
			case 14: return RuleEnum.HITANY;
			case 15: return RuleEnum.HITLAST;
			case 16: return RuleEnum.HITLASTTWO;
			case 17: return RuleEnum.HITNOTLAST;
			case 18: return RuleEnum.HITSECONDFROMLAST;
			case 19: return RuleEnum.HITTHIRDFROMLAST;
			case 20: return RuleEnum.PUNCHANY;
			case 21: return RuleEnum.PUNCHLAST;
			case 22: return RuleEnum.PUNCHLASTTWO;
			case 23: return RuleEnum.PUNCHNOTLAST;
			case 24: return RuleEnum.PUNCHSECONDFROMLAST;
			case 25: return RuleEnum.PUNCHTHIRDFROMLAST;
			case 26: return RuleEnum.SHRINKANY;
			case 27: return RuleEnum.SHRINKLAST;
			case 28: return RuleEnum.SHRINKLASTTWO;
			case 29: return RuleEnum.SHRINKNOTLAST;
			case 30: return RuleEnum.SHRINKSECONDFROMLAST;
			case 31: return RuleEnum.SHRINKTHIRDFROMLAST;
			case 32: return RuleEnum.UPSETANY;
			case 33: return RuleEnum.UPSETLAST;
			case 34: return RuleEnum.UPSETLASTTWO;
			case 35: return RuleEnum.UPSETNOTLAST;
			case 36: return RuleEnum.UPSETSECONDFROMLAST;
			case 37: return RuleEnum.UPSETTHIRDFROMLAST;
			default: return RuleEnum.ANY;
		}
	}
}
