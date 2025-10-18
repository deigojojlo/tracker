package deigojojlo.tracker.mixin;

import java.util.regex.Matcher; // Ajout de l'import pour le Matcher
import java.util.regex.Pattern;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import deigojojlo.tracker.DataAnalist.Minion;
import deigojojlo.tracker.ignore.Ignore;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
@Mixin(ClientPlayNetworkHandler.class)
public class ChatFilter {
    private static final Pattern JobsLevelGain = Pattern.compile("✔ Vous avez atteint le niveau [0-9]+ dans le métier Fermier !");
    private static final Pattern sellPattern = Pattern.compile("^✔ Vous avez gagné (\\d+\\.\\d+)实 \\((\\d+)x objets\\) avec succès\\.$");
	private static final Pattern headPattern = Pattern.compile("✔ Vous avez reçu 1约 avec succès.");

    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true )
    private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci){
        Text message = packet.content();
        String messageText = message.getString();
        if (!Ignore.isInit()){ Ignore.load() ;}

        if (Ignore.toIgnore(messageText)){
            ci.cancel();
            return;
        }

		System.out.println("OnGameMessage : " + messageText);

        // Match money
        Matcher moneyMatcher = sellPattern.matcher(messageText);
		Matcher headMatcher = headPattern.matcher(messageText);

        if (moneyMatcher.matches()){
			System.out.println("[Mixin] match money");
            ci.cancel();
            String moneyString = moneyMatcher.group(1).replace(",", ".");
            String itemsString = moneyMatcher.group(2);
            try {
                double money = Double.parseDouble(moneyString);
                Minion.addMoney(money);
				Minion.addItems(Integer.parseInt(itemsString));
            } catch (NumberFormatException e) {
                System.err.println("Erreur de conversion du montant d'argent : " + moneyString);
            }
			return ;
        }
		if (headMatcher.matches()){
			System.out.println("[Mixin] match head");
			ci.cancel();
			return;
		}

        // On peut ajouter une condition pour le gain de niveau ici si on veut aussi l'annuler
        if (JobsLevelGain.matcher(messageText).matches()){
            ci.cancel();
            // Pas de Minion.add... ici, car c'est juste un message de niveau
        }
    }
}