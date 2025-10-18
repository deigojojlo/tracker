package deigojojlo.tracker.GUI;


import deigojojlo.tracker.DataAnalist.Island;
import deigojojlo.tracker.DataAnalist.Minion;
import deigojojlo.tracker.Setting.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

public class Overlay {
    public static void render(DrawContext drawContext,RenderTickCounter tickCounter){

        MinecraftClient mcCli = MinecraftClient.getInstance();
        if (mcCli.player == null || mcCli.getDebugHud().shouldShowDebugHud()) return ;

        int x = 10;
        int y = 10;


        // TITRE
        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(Config.TITLE_SCALE, Config.TITLE_SCALE, 1.0f);

        // GLOBAL
        y = 10;
        x = 10;
        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(Config.SUBTITLE_SCALE, Config.SUBTITLE_SCALE, 1.0f);
        drawContext.drawText(mcCli.textRenderer, Text.literal("Money [" +Minion.getTime() + "] : " + Minion.getMoney() + "§f实"), x, y, 0xFFFFFF, true);
        y += 12;
        drawContext.drawText(mcCli.textRenderer, Text.literal("Level [" + Island.getTime() + "] : " + Island.getFormatLevel() + " levels"), x, y, 0xFFFFFF, true);
        y += 12;
        drawContext.getMatrices().pop();
    }
}
