package deigojojlo.chatutil.mixin;

import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.text.Text;
import deigojojlo.chatutil.DataAnalist.Minion;
import deigojojlo.chatutil.ignore.Ignore;
import java.util.regex.Pattern;
@Mixin(MinecraftServer.class)
public class ChatFilter {
	private static final Pattern JobsLevelGain = Pattern.compile("✔ Vous avez atteint le niveau [0-9]+ dans le métier Fermier !");
	private static final Pattern sellPattern = Pattern.compile("✔ Vous avez gagné (\\d+\\.?\\d*)实 \\((\\d+)x objets\\) avec succès.");

	
	@Inject(at = @At("HEAD"), method = "atGameMessage", cancellable = true)
	private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci){
		Text message = packet.content();
		String messageText = message.getString();

		if (!Ignore.isInit()){ Ignore.load() ;}

		if (Ignore.toIgnore(messageText)){ci.cancel();}

		// Match money
		if (sellPattern.matcher(messageText).matches()){
			Minion.addMoney(Integer.parseInt(sellPattern.matcher(messageText).group(1)));
		}
	}
}