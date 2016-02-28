package org.syndaryl.animalsdropbones.handler;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler {

	public static Configuration config;
	
	public static boolean isEnabled;
	public static boolean animalsDropBones;
	public static boolean pigsDropLeather;
	public static boolean horsesDropLeather;
	public static boolean enableBlockCompresion;
	
	public static double smasherDurabilityMultiplier; 
	public static double smasherEfficiencyMultiplier; 

	public static boolean enableFood;

	public static boolean enableObsidianTools;

	public static double smasherExhaustionPerBonusBlock;
	
	public static void setConfig(File configFile) {
		config = new Configuration(configFile);
		syncConfig();
	}
	
	public static void syncConfig() {
		List<String> propOrder = new ArrayList<String>();
		Property currProp;
		
		// Sets whether the mod is enabled or disabled
		currProp = config.get(
				Configuration.CATEGORY_GENERAL,
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
				"pigsDropLeather",
				true,
				"Do pigs drop leather (as well as normal drops?)"
				);
		pigsDropLeather = currProp.getBoolean(true);
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
				0.5F,
				"Extra exhaustion tax for each 'bonus' block broken by a smasher. default 0.5",
				// Value range: 0+
				0, Float.MAX_VALUE
				);
		smasherExhaustionPerBonusBlock = currProp.getDouble(0.5F);
		propOrder.add(currProp.getName());

		//currProp = config.get(
		//		Configuration.CATEGORY_GENERAL,
		//		"dropFrequency",
		//		26000,
		//		"How often will feathers be shed?\nDrop chance is 1/dropFrequency.",
		//		// Value range: 6000+
		//		6000, Integer.MAX_VALUE
		//		);
		//dropFreq = currProp.getInt(26000);
		//propOrder.add(currProp.getName());
		
		// Order configurations
		config.setCategoryPropertyOrder(Configuration.CATEGORY_GENERAL, propOrder);
		config.setCategoryPropertyOrder("Drops", propOrder);
		config.setCategoryPropertyOrder("Tools", propOrder);
		config.setCategoryPropertyOrder("Items", propOrder);
		config.setCategoryPropertyOrder("Blocks", propOrder);
		config.setCategoryPropertyOrder("Drops", propOrder);
		
		if (config.hasChanged()) {
			config.save();
		}
	}
}