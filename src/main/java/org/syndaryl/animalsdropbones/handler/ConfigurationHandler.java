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

	public static boolean enableFood;
	
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
		
		if (config.hasChanged()) {
			config.save();
		}
	}
}