package deigojojlo.tracker.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class Graph {
    public static void drawGraph(TextRenderer renderer,DrawContext context, int sx, int sy, int sw, int sh,String title,String xName,String yName,int[] xDivision,int[] yDivision,int[] plotX,int[] plotY){
        int leftPadd = sw / 10;
        int bottomPadd = sh / 10;

        int startX = leftPadd * 2;
        int startY = bottomPadd * 2;
        // draw Axis
        context.drawVerticalLine(sx + startX, sy + sh - startY , sx + startX, sy + bottomPadd);
        context.drawHorizontalLine(sx + startX, sy + sh - startY, sx + startX + sw - leftPadd, sy + sh - startY);

        // draw lines

        // draw xName yName title
        context.drawText(renderer, Text.translatable("title"), startX, sy + bottomPadd, 0xFFFFFF, false);
        context.drawText(renderer, Text.translatable("xName"), startX, sy + sh - 1, 0xFFFFFF, false);
        // TODO : draw verticaly ???

        // draw division
        List<Double> xNormal = normalize(xDivision);
        List<Double> yNormal = normalize(yDivision);
        int height = sh - 3 * bottomPadd;
        int width = sw - 3 * leftPadd;

        for (int i = 0 ; i < xNormal.size() ; i++){
            context.drawText(renderer, Text.of(xDivision[i] + ""), sx + startX + (int)(width * xNormal.get(i) - renderer.getWidth(xDivision + "") / 2), sy + sh - bottomPadd, 0xFFFFFF, false);
        }
        for (int i = 0 ; i < yNormal.size() ; i++){
            context.drawText(renderer, Text.of(yDivision[i] + ""), sx + leftPadd, sy + bottomPadd + (int)(height * yNormal.get(i)), 0xFFFFFF, false);
        }

        // draw Point
        
    }

    public static List<Double> normalize(int[] data) {
        // Gérer les cas extrêmes (liste vide ou null)
        if (data == null || data.length == 0) {
            return new ArrayList<>();
        }

        // 1. Trouver la valeur maximale de la liste
        List<Integer> l = Arrays.stream(data).boxed().collect(Collectors.toList());
        int max = Collections.max(l);

        // Gérer le cas où le maximum est 0 pour éviter la division par zéro
        if (max == 0) {
            // Si le max est 0 (et donc tous les éléments sont 0), 
            // la proportionnalité est 0 pour tous.
            List<Double> proportionsZero = new ArrayList<>();
            for (int val : l) {
                proportionsZero.add(0.0);
            }
            return proportionsZero;
        }

        // 2. Calculer la proportionnalité pour chaque entrée
        List<Double> proportions = new ArrayList<>();
        
        for (int valeur : l) {
            // Le (double) est essentiel pour effectuer une division en virgule flottante
            double proportion = (double) valeur / max;
            proportions.add(proportion);
        }

        return proportions;
    }
}
