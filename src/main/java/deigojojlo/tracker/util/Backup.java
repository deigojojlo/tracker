package deigojojlo.tracker.util;

import java.nio.file.*;
import java.io.IOException;

public class Backup {

    public static Path backup(String principalFile) {
        Path dir = Paths.get(System.getenv("APPDATA"), "tracker");
        Path principalPath = Paths.get(System.getenv("APPDATA"), "tracker", principalFile);

        // Vérifie que le fichier principal existe
        if (!Files.exists(principalPath)) {
            System.err.println("Le fichier principal n'existe pas : " + principalPath);
            return principalPath;
        }

        try {
            // Décale les sauvegardes existantes (de 9 à 1)
            for (int i = 9; i >= 1; i--) {
                String sourceName = getBackupName(principalFile, i);
                String targetName = getBackupName(principalFile, i + 1);

                Path source = dir.resolve(sourceName);
                Path target = dir.resolve(targetName);

                // Si la sauvegarde source existe, on la déplace
                if (Files.exists(source)) {
                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            // Copie le fichier principal dans la première sauvegarde
            String firstBackupName = getBackupName(principalFile, 1);
            Path firstBackup = dir.resolve(firstBackupName);
            Files.copy(principalPath, firstBackup, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Backup effectué avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors du backup : " + e.getMessage());
            e.printStackTrace();
        }
        return principalPath;
    }

    // Méthode utilitaire pour générer le nom de la sauvegarde
    private static String getBackupName(String principalFile, int index) {
        int dotIndex = principalFile.lastIndexOf('.');
        if (dotIndex == -1) {
            return principalFile + index; // Si pas d'extension, ajoute juste l'index
        } else {
            String nameWithoutExt = principalFile.substring(0, dotIndex);
            String ext = principalFile.substring(dotIndex);
            return nameWithoutExt + index + ext; // Insère l'index avant l'extension
        }
    }
}

