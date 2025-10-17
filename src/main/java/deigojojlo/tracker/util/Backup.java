package deigojojlo.tracker.util;

import java.nio.file.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Backup {

    public static void backup(String path, String principalFile) {
        Path dir = Paths.get(path);
        Path principalPath = dir.resolve(principalFile);

        // Vérifie que le fichier principal existe
        if (!Files.exists(principalPath)) {
            System.err.println("Le fichier principal n'existe pas : " + principalPath);
            return;
        }

        try {
            // Décale les sauvegardes existantes (de 9 à 1)
            for (int i = 9; i >= 1; i--) {
                Path source = dir.resolve(principalFile + i);
                Path target = dir.resolve(principalFile + (i + 1));

                // Si la sauvegarde source existe, on la déplace
                if (Files.exists(source)) {
                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            // Copie le fichier principal dans la première sauvegarde
            Path firstBackup = dir.resolve(principalFile + "1");
            Files.copy(principalPath, firstBackup, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Backup effectué avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors du backup : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
