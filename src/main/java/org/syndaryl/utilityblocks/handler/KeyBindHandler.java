package org.syndaryl.utilityblocks.handler;
import org.syndaryl.utilityblocks.UtilityBlocks;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindHandler {

	public static org.syndaryl.utilityblocks.handler.KeyBindings Keys = new KeyBindings();
	
	public KeyBindHandler() {
		// TODO Auto-generated constructor stub
	}
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(KeyBindings.ping.isPressed())
        	UtilityBlocks.LOG.warn("ping keypress");
        if(KeyBindings.pong.isPressed())
        	UtilityBlocks.LOG.warn("pong keypress");
    }
	public static void initialiseKeyBinds() {
		UtilityBlocks.LOG.info("KeyBindHandler initialized");
		org.syndaryl.utilityblocks.handler.KeyBindings.init();
		
	}
}

