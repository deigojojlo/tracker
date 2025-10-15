package deigojojlo.chatutil.Notify;

import com.mojang.authlib.minecraft.client.MinecraftClient;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.session.report.log.ReceivedMessage.ChatMessage;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

public class Notify implements ModInitializer {
    @Override
    public void onInitialize(){
        ServerMessageEvents.CHAT_MESSAGE.register((message,sender,param) -> {
            
        });
    }

    private static void playSound(MinecraftClient mcClient){
    }
}
