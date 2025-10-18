package deigojojlo.tracker;

import java.util.Timer;

import org.lwjgl.glfw.GLFW;

import deigojojlo.tracker.DataAnalist.Island;
import deigojojlo.tracker.DataAnalist.Jobs;
import deigojojlo.tracker.DataAnalist.Minion;
import deigojojlo.tracker.GUI.LogScreen;
import deigojojlo.tracker.GUI.Overlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class ObTrackerMod implements ClientModInitializer {
	public static final String MOD_ID = "chatutil";
	private static KeyBinding logKeyBinding;
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	@Override
	public void onInitializeClient() {
		ClientPlayConnectionEvents.DISCONNECT.register(this::onClientDisconnect);
		ClientPlayConnectionEvents.JOIN.register((handler,sender,client) -> onClientDisconnect(handler, client));
		HudRenderCallback.EVENT.register(Overlay::render);
		Timer saveTimer = new Timer();
		/*
        saveTimer.scheduleAtFixedRate(() -> {
			Island.save();
			Jobs.save();
			Minion.save();
		}, 300000, 300000); */

		// 1. Créer le KeyBinding (par exemple, touche L)
        logKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.votremod.open_log", // Clé de traduction
            InputUtil.Type.KEYSYM, 
            GLFW.GLFW_KEY_L, // Touche L
            "category.votremod.log" // Catégorie
        ));

        // 2. Événement de tick client pour vérifier si la touche est pressée
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (logKeyBinding.wasPressed()) {
                // Ouvre l'écran
                client.setScreen(new LogScreen()); 
            }
        });
	}

	private void onClientDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client){
		Island.save();
		Jobs.save();
		Minion.save();
	}
}