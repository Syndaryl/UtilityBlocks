package org.syndaryl.utilityblocks.handler;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler {

	public static Configuration config;
	
	public static boolean isEnabled;
	
	public static boolean animalsDropBones;
	public static int     boneFrequency;
	public static double     bonemealFrequency;

	public static boolean pigsDropLeather;
	public static int     pigsLeatherFrequency;
	public static boolean horsesDropLeather;
	public static int     horseLeatherFrequency;
	public static boolean enableBlockCompresion;
	
	public static boolean enableFood;
	public static boolean enableDrinkPotions;
	public static boolean isEggnogGross;
	public static int     drinkPotionDuration;

	public static boolean enableObsidianTools;
	
	public static double  smasherDurabilityMultiplier; 
	public static double  smasherEfficiencyMultiplier; 
	public static double  smasherExhaustionPerBonusBlock;
	public static boolean smashersSpecific;
	
	public static void setConfig(File configFile) {
		config = new Configuration(configFile);
		syncConfig();
	}
	
	public static void syncConfig() {
		List<String> propOrder = new ArrayList<String>();
		Property currProp;
		
		// Sets whether the mod is enabled or disabled
		currProp = config.get(
				"General",
				"enable",
				true,
				"Enables the mod.\nSet to false to disable the mod."
				);
		isEnabled = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Drops",
				"animalsDropBones",
				true,
				"Do animnals drop bones (as well as normal drops?)"
				);
		animalsDropBones = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Drops",
				"boneFrequency",
				5,
				"What is the rate of animal bone drops (drops 1 in X kills, so smaller numbers = more drops)"
				);
		boneFrequency = currProp.getInt(5);
		
		propOrder.add(currProp.getName());
		currProp = config.get(
				"Drops",
				"bonemealFrequency",
				5,
				"What is the %age of bone drops that are bonemeal instead (decimal number between 0 and 1 - default is 0.6)",
				0, 1
				);
		bonemealFrequency = currProp.getDouble(0.6);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Drops",
				"pigsDropLeather",
				true,
				"Do pigs drop leather (as well as normal drops?)"
				);
		pigsDropLeather = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Drops",
				"pigsLeatherFrequency",
				5,
				"What is the rate of Pig leather drops (drops 1 in X kills, so smaller numbers = more drops)"
				);
		pigsLeatherFrequency = currProp.getInt(5);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Drops",
				"horsesDropLeather",
				true,
				"Do horses drop leather (as well as normal drops?)"
				);
		horsesDropLeather = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Drops",
				"horseLeatherFrequency",
				5,
				"What is the rate of Horse leather drops (drops 1 in X kills, so smaller numbers = more drops)"
				);
		horseLeatherFrequency = currProp.getInt(5);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Blocks",
				"enableBlockCompresion",
				true,
				"Is block compression enabled?"
				);
		enableBlockCompresion = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Items",
				"enableFood",
				true,
				"Are the new food items enabled?"
				);
		enableFood = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Items",
				"enableDrinkPotions",
				true,
				"If the food items are enabled, do the new drinks apply short duration potion effects?"
				);
		enableDrinkPotions = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Items",
				"isEggnogGross",
				false,
				"Is eggnogg gross? (Gross eggnog briefly applies nausea, otherwise briefly applies jump boost)"
				);
		isEggnogGross = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		//drinkPotionDuration
		currProp = config.get(
				"Tools",
				"drinkPotionDuration",
				3,
				"Duration of the brief potion effect associated with drinks? (1-5 seconds)",
				// Value range: 0+
				1, 5
				);
		drinkPotionDuration = currProp.getInt(3);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Tools",
				"enableObsidianTools",
				true,
				"Are the new obsidian tools and armor enabled?"
				);
		enableObsidianTools = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Tools",
				"smasherDurabilityMultiplier",
				6.0F,
				"Durability multiplier for smashers, relative to normal tools. Default is 6.0",
				// Value range: 0+
				0, Float.MAX_VALUE
				);
		smasherDurabilityMultiplier = currProp.getDouble(6.0F);
		propOrder.add(currProp.getName());
		
		//smasherEfficiencyMultiplier
		currProp = config.get(
				"Tools",
				"smasherEfficiencyMultiplier",
				0.25F,
				"Mining speed multiplier for smashers, relative to normal tools. Default is 0.25 (one quarter normal speed)",
				// Value range: 0+
				0, Float.MAX_VALUE
				);
		smasherEfficiencyMultiplier = currProp.getDouble(0.25F);
		propOrder.add(currProp.getName());
		
		currProp = config.get(
				"Tools",
				"smasherExhaustionPerBonusBlock",
				0.25F,
				"Extra exhaustion tax for each 'bonus' block broken by a smasher. default 0.25",
				// Value range: 0+
				0, Float.MAX_VALUE
				);
		smasherExhaustionPerBonusBlock = currProp.getDouble(0.25F);

		currProp = config.get(
				"Tools",
				"smashersSpecific",
				true,
				"Do smashers only 'chain' onto identical materials? (Default true - false means that eg if you break stone, you can also break andesite)"
				);
		smashersSpecific = currProp.getBoolean(true);
		propOrder.add(currProp.getName());
		
		// Order configurations
		config.setCategoryPropertyOrder("General", propOrder);
		config.setCategoryPropertyOrder("Tools", propOrder);
		config.setCategoryPropertyOrder("Items", propOrder);
		config.setCategoryPropertyOrder("Blocks", propOrder);
		config.setCategoryPropertyOrder("Drops", propOrder);
		
		if (config.hasChanged()) {
			config.save();
		}
	}
}