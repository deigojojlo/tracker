package deigojojlo.tracker.ignore;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import deigojojlo.tracker.util.Backup;

public class Ignore {
    private static List<IgnoreJson> data;
    private static Path path;
    public static boolean isInit(){
        return data != null;
    }

    public static void load(){
        Gson gson = new Gson();
        path = Backup.createFile("ignore.json");

        try (FileReader reader = new FileReader(path.toAbsolutePath().toString())){
            Type itemListType = new TypeToken<List<IgnoreJson>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType); // items
            if (data == null) data = new ArrayList<>();
        } catch (IOException error){
            error.printStackTrace();
            data = new ArrayList<>();
        }
    }

    public static void save(){
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(path.toAbsolutePath().toString())){
            writer.write(gson.toJson(data,(Type) (new TypeToken<List<IgnoreJson>>(){}.getType())));
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    public static boolean toIgnore(String message){
        if (data == null) return false ;
        for (IgnoreJson item : data){
            if (message.matches(item.getText())){
                return true;
            }
        }
        return false;
    }

    public static int getDataLength(){
        return data.size();
    }

    
}
