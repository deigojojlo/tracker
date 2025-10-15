package deigojojlo.chatutil;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import deigojojlo.chatutil.DataAnalist.Island;
import deigojojlo.chatutil.DataAnalist.Jobs;
import deigojojlo.chatutil.DataAnalist.Minion;
import deigojojlo.chatutil.GUI.Overlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Identifier;

public class ChatUtil implements ClientModInitializer {
	public static final String MOD_ID = "chatutil";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		ClientPlayConnectionEvents.DISCONNECT.register(this::onClientDisconnect);
		ClientPlayConnectionEvents.JOIN.register((handler,sender,client) -> onClientDisconnect(handler, client));
		HudRenderCallback.EVENT.register(Overlay::render);
		Timer saveTimer = new Timer();
        saveTimer.scheduleAtFixedRate(() -> {
			Island.save();
			Jobs.save();
			Minion.save();
		}, 300000, 300000);
	}

	private void onClientDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client){
		Island.save();
		Jobs.save();
		Minion.save();
	}
}