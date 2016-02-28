package org.syndaryl.utilityblocks.handler;

import org.syndaryl.utilityblocks.UtilityBlocks;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGUI extends GuiConfig {

	public ConfigGUI(GuiScreen parentScreen) {
		super(
				parentScreen,
				//configElements
				new ConfigElement(ConfigurationHandler.config.getCategory("general")).getChildElements(),
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

	

}
