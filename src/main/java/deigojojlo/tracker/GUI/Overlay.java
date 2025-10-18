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

        int titleX = (int)(x /Config.TITLE_SCALE);
        int titleY = (int)(y /Config.TITLE_SCALE);
        int titleWidth = mcCli.textRenderer.getWidth("Tracker") + 8;
        int titleHeight = 16;
        int bgPadding = 4;

        drawContext.fill(titleX,titleY - bgPadding, titleX + titleWidth, titleY - bgPadding + titleHeight, 0xFF00FF);
        drawContext.drawText(mcCli.textRenderer,Text.literal("Tracker"), titleX + bgPadding, titleY, 0xFFFFFF, true);
        drawContext.getMatrices().pop();

        // GLOBAL
        /*
         * Total all time
         * Total monthly
         * Total day
         */

        y = titleY + bgPadding *2 + titleHeight;
        
        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(Config.SUBTITLE_SCALE, Config.SUBTITLE_SCALE, 1.0f);
        drawContext.drawText(mcCli.textRenderer, Text.literal("GLOBAL :"), titleX, y, 0xFFFFFF, true);
        y += 12;
        drawContext.drawText(mcCli.textRenderer, Text.literal("Money of the day : " + Minion.getMoney() + "§f实"), titleX, y, 0xFFFFFF, true);
        y += 12;
        drawContext.drawText(mcCli.textRenderer, Text.literal("Level of the day : " + Island.getLevel() + " levels"), titleX, y, 0xFFFFFF, true);

        drawContext.getMatrices().pop();

        // Minion
        // TODO : stats by H and minute
        /*
        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(Config.SUBTITLE_SCALE, Config.SUBTITLE_SCALE, 1.0f);
        drawContext.drawText(mcCli.textRenderer, Text.literal("MINION :"), titleX, y, 0xFFFFFF, true);

        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(Config.SUBTITLE_SCALE, Config.SUBTITLE_SCALE, 1.0f);
        drawContext.drawText(mcCli.textRenderer, Text.literal("NIVEAU :"), titleX, y, 0xFFFFFF, true);

        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(Config.SUBTITLE_SCALE, Config.SUBTITLE_SCALE, 1.0f);
        drawContext.drawText(mcCli.textRenderer, Text.literal("METIER :"), titleX, y, 0xFFFFFF, true);
        */
    }
}
