package deigojojlo.chatutil.mixin;

import org.apache.logging.log4j.core.tools.picocli.CommandLine.Help.Ansi.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import deigojojlo.chatutil.DataAnalist.Island;
import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class TitleCapture {
    
    @Inject(method = "setTitle", at = @At("HEAD"))
    private void onTitleSet(Text title, CallbackInfo ci){
        String message = title.plainString();
        for (int i = 0 ; i < message.length() ; i++){
            char c = message.charAt(i);
            switch (c) {
                case '隔' : Island.addLevel(1); break;
            }
        }
    }
}
