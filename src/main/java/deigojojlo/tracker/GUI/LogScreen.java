package deigojojlo.tracker.GUI;

import java.util.ArrayList;
import java.util.List;

import deigojojlo.tracker.DataAnalist.Island;
import deigojojlo.tracker.DataAnalist.Jobs;
import deigojojlo.tracker.DataAnalist.Minion;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;

public class LogScreen extends Screen {
    
    // ... (Variables de classe inchangées) ...
    private static final float WIDTH_RATIO = 0.75f;
    private static final float HEIGHT_RATIO = 0.75f;
    private static final int MIN_WIDTH = 250;
    private int currentTab = 0;
    private int windowWidth;
    private int windowHeight;
    private int x, y;

    public LogScreen() {
        super(Text.translatable("gui.votremod.log.title"));
    }

    @Override
    protected void init() {
        super.init();
        
        this.windowWidth = Math.max(MIN_WIDTH, MathHelper.floor(this.width * WIDTH_RATIO));
        this.windowHeight = MathHelper.floor(this.height * HEIGHT_RATIO);
        
        this.x = (this.width - windowWidth) / 2;
        this.y = (this.height - windowHeight) / 2;

        // ... (Logique des boutons d'onglet inchangée) ...
        int tabCount = 3;
        int tabTotalWidth = windowWidth - 10;
        int tabWidth = tabTotalWidth / tabCount;
        int tabY = y + 5;

        this.clearChildren();

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.votremod.log.tab.metier"), button -> {
            this.currentTab = 0;
        }).position(x + 5, tabY).size(tabWidth - 1, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.votremod.log.tab.argent"), button -> {
            this.currentTab = 1;
        }).position(x + 5 + tabWidth, tabY).size(tabWidth - 1, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.votremod.log.tab.niveaux"), button -> {
            this.currentTab = 2;
        }).position(x + 5 + 2 * tabWidth, tabY).size(tabWidth - 1, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        // Dessiner le cadre de la fenêtre (Noir semi-transparent)
        context.fill(x, y, x + windowWidth, y + windowHeight, 0xD0000000);

        // Dessiner la bordure
        context.drawBorder(x, y, windowWidth, windowHeight, 0xFFFFFFFF);

        // Séparateur entre les onglets et le contenu
        int contentY = y + 30;
        context.drawHorizontalLine(x + 5, x + windowWidth - 5, contentY, 0xFF888888);
        
        // affichage de 3 bouttons
        int tabY = y + 5;
        int tabWidth = (windowWidth - 10) / 3;
        context.fill(x + 5, tabY , x + 5 + tabWidth -1 , tabY + 20, 0x999393FB);
        context.drawBorder(x + 5, tabY , tabWidth -1 , 20, 0x997979f7);
        context.drawText(textRenderer, Text.translatable("gui.tracker.log.metier"), x + 5 + tabWidth / 2 - textRenderer.getWidth(Text.translatable("gui.tracker.log.metier"))/2, tabY + 6 , 0xFF000000, false);
        context.fill(x + 5 + tabWidth, tabY ,x + 5 + 2 * tabWidth -1 , tabY + 20,0x999393FB );
        context.drawBorder(x + 5 + tabWidth, tabY , tabWidth -1 , 20, 0x997979f7);
        context.drawText(textRenderer, Text.translatable("gui.tracker.log.argent"),x + 5 + tabWidth + 1 + tabWidth / 2 - textRenderer.getWidth(Text.translatable("gui.tracker.log.argent"))/2, tabY + 6, 0xFF000000, false);
        context.fill(x + 5 + 2 * tabWidth, tabY,  x + 5 +3 * tabWidth -1 , tabY + 20, 0x999393FB);
        context.drawBorder(x + 5 + 2 * tabWidth, tabY, tabWidth -1 , 20, 0x997979f7);
        context.drawText(textRenderer, Text.translatable("gui.tracker.log.niveaux"),x + 5 + 2 * tabWidth + tabWidth / 2 - textRenderer.getWidth(Text.translatable("gui.tracker.log.niveaux"))/2, tabY + 6, 0xFF000000, false);
        switch (currentTab) {
            case 0:
                drawContents(context, "Métier", contentY);
                break;
            case 1:
                drawContents(context, "Argent", contentY);
                break;
            case 2:
                drawContents(context, "Niveaux", contentY);
                break;
        }
        
    }
    
    private void draw4casesBackgrounds(DrawContext context, int startY) {
        int contentWidth = windowWidth - 10;
        int contentHeight = windowHeight - (startY - y) - 5;
        
        int cellWidth = (contentWidth / 2) - 2;
        int cellHeight = (contentHeight / 2) - 2;

        int paddingX = x + 5;
        int paddingY = startY + 5;

        // Fonds des 4 cases
        context.fill(paddingX, paddingY, paddingX + cellWidth, paddingY + cellHeight, 0x80222222); // Case 1 (Stat)
        context.drawBorder(paddingX, paddingY, cellWidth, cellHeight, 0xFF444444);

        context.fill(paddingX + cellWidth + 4, paddingY, paddingX + cellWidth + 4 + cellWidth, paddingY + cellHeight, 0x80222222); // Case 2 (G1)
        context.drawBorder(paddingX + cellWidth + 4, paddingY, cellWidth, cellHeight, 0xFF444444);

        context.fill(paddingX, paddingY + cellHeight + 4, paddingX + cellWidth, paddingY + cellHeight + 4 + cellHeight, 0x80222222); // Case 3 (G2)
        context.drawBorder(paddingX, paddingY + cellHeight + 4, cellWidth, cellHeight, 0xFF444444);

        context.fill(paddingX + cellWidth + 4, paddingY + cellHeight + 4, paddingX + cellWidth + 4 + cellWidth, paddingY + cellHeight + 4 + cellHeight, 0x80222222); // Case 4 (G3)
        context.drawBorder(paddingX + cellWidth + 4, paddingY + cellHeight + 4, cellWidth, cellHeight, 0xFF444444);
    }

    private ArrayList<int[]> drawCasesBackgrounds(DrawContext context,int startY, int number){
        int contentWidth = windowWidth - 10;
        int contentHeight = windowHeight - (startY - y) - 5;
        
        int cellWidth = (contentWidth / (number - number / 2)) - 2;
        int cellHeight = (contentHeight / 2) - 2;

        int paddingX = x + 5;
        int paddingY = startY + 5;

        // 2 lines and number/ 2 columns
        ArrayList<int[]> contentPlacement = new ArrayList<>();
        int caseNum = 0;
        while (caseNum < number){
            int line = caseNum >= number - number / 2 ? 1 : 0;
            int y = paddingY + (cellHeight + 4) * line;
            int x = paddingX + (cellWidth + 4) * (caseNum % (number - number /2)) + ( number % 2 == 1 && line == 1 ? cellWidth / 2 : 0);
            contentPlacement.add(new int[] {x,y,cellWidth,cellHeight});
            context.fill(x, y, x + cellWidth, y + cellHeight, 0x80222222); // Case 3 (G2)
            context.drawBorder(x, y, cellWidth, cellHeight, 0xFF444444);
            caseNum++;
        }
        return contentPlacement;
    }
    
    /**
     * Dessine uniquement le texte et les graphiques par-dessus les fonds.
     */
    private void drawContents(DrawContext context, String category, int startY) {
        // Dimensions et positions
        int contentWidth = windowWidth - 10;
        int contentHeight = windowHeight - (startY - y) - 5; 
        
        int cellWidth = (contentWidth / 2) - 2;
        int cellHeight = (contentHeight / 2) - 2;

        int paddingX = x + 5;
        int paddingY = startY + 5;

        // Case 1: Statistiques (Haut-Gauche)
        switch (category) {
            case "Argent" :
                draw4casesBackgrounds(context, startY);
                renderClassicStatistics(context, paddingX, paddingY, cellWidth, cellHeight, category+" : Argent");
                renderClassicStatistics(context, paddingX + 4 + cellWidth, paddingY , cellWidth, cellHeight, category+" : Items");
                break;
            case "Niveaux" :
                draw4casesBackgrounds(context, startY);
                renderClassicStatistics(context, paddingX, paddingY, cellWidth, cellHeight, category);
                renderGraphContent(context, paddingX + cellWidth + 4, paddingY, cellWidth, cellHeight, "Graph 1: Données " + category + " Journalières");
                Pair<List<Double>,List<Double>> g1 = Island.getLast7daysGraph();
                Graph.drawGraph(textRenderer,context, paddingX, paddingY + cellHeight + 4, cellWidth, cellHeight, "gui.tracker.log.last7days", "gui.tracker.log.jour", "gui.tracker.log.level",g1.getLeft(), g1.getRight(), g1.getLeft(), g1.getRight());
                break;
            case "Métier" :
                drawCasesBackgrounds(context, startY, Jobs.JobsList.length);
                int i = 0;
                int number = Jobs.JobsList.length;
                int w = contentWidth / (number - number/2);
                int h = cellHeight;
                int halfInf = number - number / 2;
                int halfSup = number / 2;
                int numberParity = number % 2 ;
                for (String job : Jobs.JobsList){
                    int y = paddingY + ( i >= halfInf ? h + 2 : 0) ;
                    int x = paddingX + ( i > halfInf && numberParity == 1 ? w / 2 : 0) + (w + 2) * (i % halfSup);
                    
                    renderJobsStatistcs(context, x, y, w, h, job);
                    i++;
                }

        }
        // Case 3: Graphique 2 (Bas-Gauche)
        //renderGraphContent(context, paddingX, paddingY + cellHeight + 4, cellWidth, cellHeight, "Graph 2: Données " + category + " Mensuelles");

        // Case 4: Graphique 3 (Bas-Droite)
        //renderGraphContent(context, paddingX + cellWidth + 4, paddingY + cellHeight + 4, cellWidth, cellHeight, "Graph 3: Progression " + category + " Global");
    }

    private void renderClassicStatistics(DrawContext context, int sx, int sy, int sw, int sh, String category){
        TextRenderer textRenderer = client.textRenderer;

        // Titre
        context.drawText(textRenderer, Text.literal("Statistiques " + category), sx + 5, sy + 5, 0xFFFFFFFF, false);
        context.drawHorizontalLine(sx + 5, sx + sw - 5, sy + 15, 0xFF888888);

        // Liste des périodes (texte)
        int currentY = sy + 20;
        String[] periods = {"Jour", "30 Derniers Jours", "Ce Mois", "Global"};
        String[] values = {"error", "error", "error", "error"};

        switch (category) {
            case "Argent : Argent" : values = new String[] {Minion.getFormatMoney() + "", Minion.getLast30days().getFormatMoney() + "", Minion.getLastMonth().getFormatMoney() + "", Minion.getAllTimeMoney().getFormatMoney() + ""};break;
            case "Argent : Items" : values = new String[] {Minion.getFormatItems() + "", Minion.getLast30days().getFormatItems() + "", Minion.getLastMonth().getFormatItems() + "", Minion.getAllTimeMoney().getFormatItems() + ""};break;
            case "Niveaux" : values = new String[] {Island.getFormatLevel() + "", Island.getFormatLast30days() + "", Island.getFormatLastMonth() + "", Island.getFormatAllTime() + ""};break;
        }


        for (int i = 0; i < periods.length; i++) {
            context.drawText(textRenderer, periods[i] + ":", sx + 10, currentY, 0xFFCCCCCC, false);
            context.drawText(textRenderer, values[i], sx + sw - textRenderer.getWidth(values[i]) - 10, currentY, 0xFF00FF00, false);
            currentY += 12;
        }
    }

    private void renderJobsStatistcs(DrawContext context, int sx, int sy, int sw, int sh,String job){
        TextRenderer textRenderer = client.textRenderer;

        // Titre
        context.drawText(textRenderer, Text.literal("Statistiques " + job), sx + 5, sy + 5, 0xFFFFFFFF, false);
        context.drawHorizontalLine(sx + 5, sx + sw - 5, sy + 15, 0xFF888888);

        int currentY = sy + 20;
        String[] lines = {"Argent", "Niveaux", "XP"};
        int[] serializedJob = Jobs.getJob(job);
        String[] values = {serializedJob[0] + "", serializedJob[1] + "", serializedJob[2] + ""};

        for (int i = 0; i < lines.length; i++) {
            context.drawText(textRenderer, lines[i], sx + 10, currentY, 0xFFCCCCCC, false);
            context.drawText(textRenderer, values[i], sx + sw - textRenderer.getWidth(values[i]) - 10, currentY, 0xFF00FF00, false);
            currentY += 12;
        }
    }

    /**
     * Dessine le contenu de l'emplacement réservé pour un graphique (titre et placeholder texte)
     */
    private void renderGraphContent(DrawContext context, int gx, int gy, int gw, int gh, String title) {
        TextRenderer textRenderer = client.textRenderer;

        // Titre
        context.drawText(textRenderer, Text.literal(title), gx + 5, gy + 5, 0xFFFFFF00, false);
        context.drawHorizontalLine(gx + 5, gx + gw - 5, gy + 15, 0xFF888888);

        // Espace réservé pour le tracé de graphique (texte)
        Text placeholder = Text.literal("Attente de valeurs pour tracé...");
        int textWidth = textRenderer.getWidth(placeholder);
        int textX = gx + (gw - textWidth) / 2;
        int textY = gy + (gh - 9) / 2;
        context.drawText(textRenderer, placeholder, textX, textY, 0xFFCCCCCC, false);

        // C'est ici que le code de tracé de lignes/points irait.
    }

    // ... (Méthodes shouldPause, isPauseScreen, keyPressed inchangées) ...

    @Override public boolean shouldPause() { return false; }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { this.close(); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}