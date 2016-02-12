//========================================================================
//                          Heating items
//========================================================================

//Add Recipe

//OutputStack, InputStack or InputOre, MeltingTemp(Default: 600, Normal Range 0-2000)[Optional], SpecificHeat(Default: 1, Normal Range 0-1)[Optional]
//mods.tfptweaks.ItemHeat.addRecipe(<minecraft:coal:1>,<terrafirmacraft:item.Log>);
//mods.tfptweaks.ItemHeat.addRecipe(<minecraft:coal:1>,<terrafirmacraft:item.Log>, 40);
//mods.tfptweaks.ItemHeat.addRecipe(<minecraft:coal:1>,<terrafirmacraft:item.Log>, 40, 1);
//mods.tfptweaks.ItemHeat.addRecipe(<minecraft:coal:1>,<ore:logWood>);
//mods.tfptweaks.ItemHeat.addRecipe(<minecraft:coal:1>,<ore:logWood>, 40);
//mods.tfptweaks.ItemHeat.addRecipe(<minecraft:coal:1>,<ore:logWood>, 40, 1);

//Remove Recipe

//InputStack
//mods.tfptweaks.ItemHeat.removeRecipe(<terrafirmacraft:Sand>);

//========================================================================
//                           Loom Recipes
//========================================================================

//Add Recipe

//OutputStack, InputStack, CosmeticLoomOverlay (Range 1-3: 1-String, 2-Silk, 3-Rope)[Optional]
//mods.tfptweaks.Loom.addRecipe(<minecraft:wool>, <terrafirmacraft:item.WoolCloth> * 4);
//mods.tfptweaks.Loom.addRecipe(<minecraft:wool>, <terrafirmacraft:item.WoolCloth> * 4, 1);

//OutputStack, InputStack, ImageOverlayLocation("modID:location")
//mods.tfptweaks.Loom.addRecipe(<minecraft:wool:11>, <terrafirmacraft:item.WoolCloth> * 4, "minecraft:blocks/wool_colored_blue");

//Remove Recipe

//InputStack {Stack size must be the same as the recipe to be removed}
//mods.tfptweaks.Loom.removeRecipe(<terrafirmacraft:item.WoolYarn> * 16);

//========================================================================
//                          Quern Recipes
//========================================================================

//Add Recipe

//OutputStack, InputStack
//mods.tfptweaks.Quern.addRecipe(<minecraft:dye:15> * 3, <minecraft:bone>);

//Remove Recipe

//OutputStack, InputStack {Stack sizes must be the same as the recipe to be removed}
//mods.tfptweaks.Quern.removeRecipe(<terrafirmacraft:item.dyePowder:15>, <minecraft:bone>);

//========================================================================
//                          Barrel Recipe(NOT FULLY IMPLEMENTED DO NOT USE)
//========================================================================

//Add Recipe

//OutputStack, OutputFluid, InputStack, InputFluid, sealtime, removesLiquid, sealed, minTechLevel, allowAnyStack
//mods.tfptweaks.Barrel.addRecipe();

//Add Multi-Item Recipe

//OutputStack, InputStack, inputFS, sealed, minTechLevel, allowAnyStack, keepstacksize
//mods.tfptweaks.Barrel.addMultiItemRecipe();