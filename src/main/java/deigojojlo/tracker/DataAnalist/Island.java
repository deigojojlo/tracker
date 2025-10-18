package deigojojlo.tracker.DataAnalist;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import deigojojlo.tracker.DataAnalist.SubType.IslandEntry;
import deigojojlo.tracker.util.Backup;
import deigojojlo.tracker.util.DateUtil;

public class Island implements Statistics{
    private static Integer time = null;
    private static IslandEntry dayLevel;
    private static List<IslandEntry> data ;
    private static int allTimeLevel = 0;
    private static int lastMonth = 0;
    private static int last30days = 0;


    public static void load(){
        Gson gson = new Gson();

        Path path = Backup.createFile("Island.json");

        try (FileReader reader = new FileReader(path.toAbsolutePath().toString())){
            Type itemListType = new TypeToken<List<IslandEntry>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType ); // items
            if (data == null) data = new ArrayList<>();

            IslandEntry lastDay =  data.getLast();
            LocalDate date = LocalDate.now();
            if (lastDay == null || !lastDay.getDate().equals(date.toString())){
                dayLevel = new IslandEntry(date.toString(), 0);
                data.add(dayLevel);
            } else {
                dayLevel = lastDay ;
            }
        } catch (IOException error){
            data = new ArrayList<>();
            dayLevel = new IslandEntry(LocalDate.now().toString(), 0);
            data.add(dayLevel);
            error.printStackTrace();
        }

        allTimeLevel = 0;
        data.forEach(level -> {
            allTimeLevel += level.getCount();
        });
    }

    public static void addLevel(int amount){
        if (dayLevel != null)
            dayLevel.addLevel(amount);
        allTimeLevel += amount;
    }

    public static void save(){
        Gson gson = new Gson();
        Path path = Backup.backup("Island.json");
        try (FileWriter writer = new FileWriter(path.toAbsolutePath().toString())){
            writer.write(gson.toJson(data));
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    public static int getLevel(){
        if (dayLevel != null)
            return dayLevel.getCount();
        return 0;
    }

    public static int getDay(String date){
        for (IslandEntry islandEntry : data) {
            if (islandEntry.getDate().equals(date))
                return islandEntry.getCount();
        }
        return 0;
    }

    public static int getAllTimeLevel(){
        return allTimeLevel;
    }

    public static int getLastMonth(){
        String[] splitedDate = LocalDate.now().toString().split("-");
        calculateLastMonth(Integer.parseInt(splitedDate[1]), Integer.parseInt(splitedDate[0]));
        return lastMonth;
    }

    public static int getLast30days(){
        calculateLast30days();
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
