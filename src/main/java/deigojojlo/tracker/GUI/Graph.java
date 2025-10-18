package deigojojlo.tracker.GUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class Graph {
    public static void drawGraph(TextRenderer renderer,DrawContext context, int sx, int sy, int sw, int sh,String title,String xName,String yName,List<Double> xDivision,List<Double> yDivision,List<Double> plotX,List<Double> plotY){
        int leftPadd = sw / 10;
        int bottomPadd = sh / 10;

        int startX = leftPadd * 2;
        int startY = bottomPadd * 2;
        // draw Axis
        context.drawVerticalLine(sx + startX, sy + sh - startY , sy + bottomPadd, 0xFFFFFF);
        context.drawHorizontalLine(sx + startX, sy + sh - startY, sx + startX + sw - leftPadd, 0xFFFFFF);

        // draw lines

        // draw xName yName title
        context.drawText(renderer, Text.translatable(title), startX, sy + bottomPadd, 0xFFFFFF, false);
        context.drawText(renderer, Text.translatable(xName), startX, sy + sh - 1, 0xFFFFFF, false);
        context.drawText(renderer, Text.translatable(yName), startX - renderer.getWidth(Text.translatable(yName))/2, sy + bottomPadd, 0xFFFFFF, false);

        // draw division
        Double maxX = Collections.max(xDivision);
        Double maxY = Collections.max(yDivision);
        List<Double> xNormal = normalize(xDivision,maxX);
        List<Double> yNormal = normalize(yDivision,maxY);
        int height = sh - 3 * bottomPadd;
        int width = sw - 3 * leftPadd;

        for (int i = 0 ; i < xNormal.size() ; i++){
            context.drawText(renderer, Text.of(xDivision.get(i) + ""), sx + startX + (int)(width * xNormal.get(i) - renderer.getWidth(xDivision + "") / 2), sy + sh - bottomPadd, 0xFFFFFF, false);
        }
        for (int i = 0 ; i < yNormal.size() ; i++){
            context.drawText(renderer, Text.of(yDivision.get(i) + ""), sx + leftPadd, sy + bottomPadd + (int)(height * yNormal.get(i)), 0xFFFFFF, false);
        }

        // draw Point
        List<Double> plotXNormal = normalize(plotX, maxX);
        List<Double> plotYNormal = normalize(plotY, maxY);
        for (int i = 0 ; i < plotXNormal.size() - 1 ; i++){
            drawObliqueLine(context,(int)(Math.floor(plotXNormal.get(i) * width)),(int)(Math.floor(plotXNormal.get(i+1) * width)),(int)(Math.floor(plotYNormal.get(i)* height)),(int)(Math.floor(plotYNormal.get(i+1) * height)), 0xFFFFFF);
        }
    }

    public static void drawObliqueLine(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        
        int err = dx - dy;
        
        int currentX = x1;
        int currentY = y1;

        while (true) {
            context.drawHorizontalLine(currentX, currentY, currentX,color);

            if (currentX == x2 && currentY == y2) {
                break;
            }

            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                currentX += sx;
            }

            if (e2 < dx) {
                err += dx;
                currentY += sy;
            }
        }
    }

// Exemple d'appel :
// drawObliqueLine(context, 10, 10, 100, 50, 0xFFFF0000); // Ligne rouge de (10,10) à (100,50)

    public static List<Double> normalize(List<Double> data,Double max) {
        // Gérer les cas extrêmes (liste vide ou null)
        if (data == null || data.size() == 0) {
            return new ArrayList<>();
        }


        if (max == 0) {
            List<Double> proportionsZero = new ArrayList<>();
            for (Double val : data) {
                proportionsZero.add(0.0);
            }
            return proportionsZero;
        }

        List<Double> proportions = new ArrayList<>();
        
        for (Double valeur : data) {
            double proportion = (double) valeur / max;
            proportions.add(proportion);
        }

        return proportions;
    }
}
