package deigojojlo.tracker.ignore;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import net.fabricmc.loader.api.FabricLoader;

public class Ignore {
    private static List<IgnoreJson> data;

    public static boolean isInit(){
        return data != null;
    }

    public static void load(){
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "chatUtil/ignore.json";

        try (FileReader reader = new FileReader(path)){
            Type itemListType = new TypeToken<List<IgnoreJson>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType); // items
            
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    private static void save(){
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "chatUtil/ignore.json";
        try (FileWriter writer = new FileWriter(path)){
            writer.write(gson.toJson(data,(Type) (new TypeToken<List<IgnoreJson>>(){}.getType())));
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    public static boolean toIgnore(String message){
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
