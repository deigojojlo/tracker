package deigojojlo.chatutil.DataAnalist;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.icu.impl.Pair;

import net.fabricmc.loader.api.FabricLoader;

public class Island {
    private static int level = 0;
    private static Integer time = null;
    private static Level dayLevel;
    private static List<Level> data ;
    private static int allTimeLevel = 0;
    private static int monthlyTimeLevel = 0;
    {
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "chatUtil/island.json";

        try (FileReader reader = new FileReader(path)){
            Type itemListType = new TypeToken<List<Level>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType ); // items
            
            Level lastDay =  data.getLast();
            LocalDate date = LocalDate.now();

            if (lastDay.getDate().equals(date.toString())){
                dayLevel = lastDay ;
            } else {
                dayLevel = new Level(date.toString(),0);
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
            if (Integer.parseInt(splitDate[1]) == date.getMonth().ordinal() && Integer.parseInt(splitDate[2]) == date.getYear()){ monthlyTimeLevel += level.getCount();}
        });
    }

    public static void addLevel(int amount){
        level += amount;
    }

    public static void save(){
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "chatUtil/island.json";

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

    public static int getMonth(){
        return monthlyTimeLevel;
    }

    private class Level {
        String date;
        int count;

        private Level(String date, int count){
            this.date = date;
            this.count = count;
        }
        private String getDate(){
            return this.date;
        }

        private int getCount(){
            return this.count;
        }

        private void setDate(String date){
            this.date = date;
        }

        private void setCount(int count){
            this.count = count;
        }
    }
}
