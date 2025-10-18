package deigojojlo.tracker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import deigojojlo.tracker.DataAnalist.Island;
import net.minecraft.text.Text;

@Mixin(net.minecraft.client.gui.hud.InGameHud.class)
public class TitleCapture {

    @Inject(method = "setTitle", at = @At("HEAD"))
    private void onTitleSet(Text title, CallbackInfo ci) {
        // Vérifie que le titre n'est pas null
        if (title == null) {
            return;
        }
        System.out.println("[Mixin] Titre intercepté : " + title.getString());
        String message = title.getString();
        if (message == null){
            System.out.println("Le contenu est vide");
            return;
        }
        if (message.contains("隔")) {
            Island.addLevel(1);
            System.out.println("[Mixin] Capture level !");
        }
    }
}
