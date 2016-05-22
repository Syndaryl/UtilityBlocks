package org.syndaryl.utilityblocks.handler.gui;

import java.util.LinkedList;
import java.util.List;

import org.syndaryl.utilityblocks.UtilityBlocks;
import org.syndaryl.utilityblocks.handler.ConfigurationHandler;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGUI extends GuiConfig {
	private static List<IConfigElement> list;

	public ConfigGUI(GuiScreen parentScreen) {
		
		super(
				parentScreen,
				//configElements
				getConfigElements(),
				//modID
				UtilityBlocks.MODID,
				//allRequireWorldRestart
				false,
				//allRequireMcRestart
				false,
				//title
				GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString())
				);
	}

	private static List<IConfigElement> getConfigElements() {
		// TODO Auto-generated method stub
		if (list != null)
			return list; // short circut.
		
		list = new LinkedList<IConfigElement>();
		
		list.addAll(new ConfigElement(ConfigurationHandler.config.getCategory("General")).getChildElements());
		list.addAll(new ConfigElement(ConfigurationHandler.config.getCategory("Tools")).getChildElements());
		list.addAll(new ConfigElement(ConfigurationHandler.config.getCategory("Items")).getChildElements());
		list.addAll(new ConfigElement(ConfigurationHandler.config.getCategory("Drops")).getChildElements());
		list.addAll(new ConfigElement(ConfigurationHandler.config.getCategory("Blocks")).getChildElements());
		
		return list;
	}

	

}
