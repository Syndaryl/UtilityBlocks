package org.syndaryl.utilityblocks.handler;

import org.lwjgl.input.Keyboard;
import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.UtilityBlocks;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {

    // Declare two KeyBindings, ping and pong
    public static KeyBinding ping;
    public static KeyBinding pong;

    public static void init() {
		UtilityBlocks.LOG.info("KeyBindings initialized");
        // Define the "ping" binding, with (unlocalized) name "key.ping" and
        // the category with (unlocalized) name "key.categories.mymod" and
        // key code 24 ("O", LWJGL constant: Keyboard.KEY_O)
        ping = new KeyBinding("Write a ping message to log", Keyboard.KEY_O, "key.categories." + NamespaceManager.GetModNameLC());
        // Define the "pong" binding, with (unlocalized) name "key.pong" and
        // the category with (unlocalized) name "key.categories.mymod" and
        // key code 25 ("P", LWJGL constant: Keyboard.KEY_P)
        pong = new KeyBinding("Write a pong message to log", Keyboard.KEY_P, "key.categories." + NamespaceManager.GetModNameLC());

        // Register both KeyBindings to the ClientRegistry
        ClientRegistry.registerKeyBinding(ping);
        ClientRegistry.registerKeyBinding(pong);
    }

}
