package deigojojlo.tracker;

import java.util.Timer;

import org.slf4j.LoggerFactory;

import deigojojlo.tracker.DataAnalist.Island;
import deigojojlo.tracker.DataAnalist.Jobs;
import deigojojlo.tracker.DataAnalist.Minion;
import deigojojlo.tracker.GUI.Overlay;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class ObTrackerMod implements ClientModInitializer {
	public static final String MOD_ID = "chatutil";

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
	}

	private void onClientDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client){
		Island.save();
		Jobs.save();
		Minion.save();
	}
}