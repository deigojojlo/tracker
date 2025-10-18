package deigojojlo.tracker.DataAnalist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface Statistics {
    default public Path createFile(String path){
        Path modDir = Paths.get(System.getenv("APPDATA"), "tracker");
        Path file = Paths.get(System.getenv("APPDATA"), "tracker", path);
        try {
            Files.createDirectory(modDir);
            Files.createFile(file);
            return file;
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return null;
    }
}
