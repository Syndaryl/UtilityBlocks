package org.syndaryl.utilityblocks.handler.gui;

import java.util.List;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory.RuntimeOptionGuiHandler;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class DropsRuntimeGUI extends GuiConfig implements RuntimeOptionGuiHandler {

	public DropsRuntimeGUI(GuiScreen parentScreen,
			List<IConfigElement> configElements, String modID, String configID,
			boolean allRequireWorldRestart, boolean allRequireMcRestart,
			String title, String titleLine2) {
		super(parentScreen, configElements, modID, configID, allRequireWorldRestart,
				allRequireMcRestart, title, titleLine2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addWidgets(List<Gui> widgetList, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionCallback(int actionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
