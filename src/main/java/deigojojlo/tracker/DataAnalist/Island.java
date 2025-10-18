package deigojojlo.tracker.DataAnalist;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import deigojojlo.tracker.DataAnalist.SubType.IslandEntry;
import deigojojlo.tracker.util.DateUtil;
import net.fabricmc.loader.api.FabricLoader;

public class Island implements Statistics{
    private static int level = 0;
    private static Integer time = null;
    private static IslandEntry dayLevel;
    private static List<IslandEntry> data ;
    private static int allTimeLevel = 0;
    private static int lastMonth = 0;
    private static int last30days = 0;
    {
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "/tracker/island.json";

        try (FileReader reader = new FileReader(path)){
            Type itemListType = new TypeToken<List<IslandEntry>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType ); // items
            
            IslandEntry lastDay =  data.getLast();
            LocalDate date = LocalDate.now();

            if (lastDay.getDate().equals(date.toString())){
                dayLevel = lastDay ;
            } else {
                dayLevel = new IslandEntry(date.toString(),0);
                data.addLast(dayLevel);
            }
        } catch (IOException error){
            data = new ArrayList<>();
            error.printStackTrace();
        }

        LocalDate date = LocalDate.now();
        data.forEach(level -> {
            allTimeLevel += level.getCount();
            String[] splitDate = level.getDate().split("-");
            if (Integer.parseInt(splitDate[1]) == date.getMonth().ordinal() && Integer.parseInt(splitDate[2]) == date.getYear()){ lastMonth += level.getCount();}
        });
    }

    public static void addLevel(int amount){
        level += amount;
    }

    public static void save(){
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "/tracker/island.json";

        try (FileWriter writer = new FileWriter(path)){
            writer.write(gson.toJson(data));
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    public static int getLevel(){
        return level;
    }

    public static int getAllTimeLevel(){
        return allTimeLevel;
    }

    public static int getLastMonth(){
        return lastMonth;
    }

    public static int getLast30days(){
        return last30days;
    }

    public static void calculateLast30days(){
        last30days = 0;
        String[] today = LocalDate.now().toString().split("-");
        int id = DateUtil.createIdentifier(Integer.parseInt(today[2]), Integer.parseInt(today[1]), Integer.parseInt(today[0]));
        for (IslandEntry entry : data){
            String[] splitedDate = entry.getDate().split("-");
            int entryId = DateUtil.createIdentifier(Integer.parseInt(splitedDate[2]), Integer.parseInt(splitedDate[1]), Integer.parseInt(splitedDate[0]));
            if (id - entryId < 31 ) last30days += entry.getCount();
        }
    }

    public static void calculateLastMonth(int month,int year){
        lastMonth = 0;
        for (IslandEntry entry : data){
            String[] splitedDate = entry.getDate().split("-");
            if (Integer.parseInt(splitedDate[0]) == year && Integer.parseInt(splitedDate[1]) == month)
                lastMonth += entry.getCount();
        }
    }
    
}
