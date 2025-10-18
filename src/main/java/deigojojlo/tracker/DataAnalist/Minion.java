package deigojojlo.tracker.DataAnalist;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import deigojojlo.tracker.DataAnalist.SubType.MinionEntry;
import deigojojlo.tracker.util.Backup;
import deigojojlo.tracker.util.DateUtil;
import net.fabricmc.loader.api.FabricLoader;

public class Minion implements Statistics {
    private static MinionEntry dayMoney;
    private static List<MinionEntry> data ;
    private static int allTimeMoney = 0;
    private static int last30days = 0;
    private static int lastMonth = 0;
    {
        Gson gson = new Gson();
        Path path = createFile("Minion.json");

        try (FileReader reader = new FileReader(path.toAbsolutePath().toString())){
            Type itemListType = new TypeToken<List<MinionEntry>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType ); // items
            
            MinionEntry lastDay =  data.getLast();
            LocalDate date = LocalDate.now();

            if (lastDay.getDate().equals(date.toString())){
                dayMoney = lastDay ;
            } else {
                dayMoney = new MinionEntry(date.toString(), 0);
                data.addLast(dayMoney);
            }
        } catch (IOException error){
            data = new ArrayList<>();
            error.printStackTrace();
        }

        data.forEach(level -> {
            allTimeMoney += level.getCount();
        });
    }

    public static void addMoney(int amount){
        if (dayMoney != null)
            dayMoney.setCount(dayMoney.getCount() + amount);
    }

    public static int getMoney(){
        if (dayMoney != null)
            return dayMoney.getCount();
        return 0;
    }


    public static int getAllTimeMoney(){
        return allTimeMoney;
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
    public static void save(){
        Gson gson = new Gson();
        
        Path path = Backup.backup("Minion.json");
        try (FileWriter writer = new FileWriter(path.toAbsolutePath().toString())){
            writer.write(gson.toJson(data));
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    public static void calculateLast30days(){
        last30days = 0;
        String[] today = LocalDate.now().toString().split("-");
        int id = DateUtil.createIdentifier(Integer.parseInt(today[2]), Integer.parseInt(today[1]), Integer.parseInt(today[0]));
        for (MinionEntry entry : data){
            String[] splitedDate = entry.getDate().split("-");
            int entryId = DateUtil.createIdentifier(Integer.parseInt(splitedDate[2]), Integer.parseInt(splitedDate[1]), Integer.parseInt(splitedDate[0]));
            if (id - entryId < 31 ) last30days += entry.getCount();
        }
    }

    public static void calculateLastMonth(int month,int year){
        lastMonth = 0;
        for (MinionEntry entry : data){
            String[] splitedDate = entry.getDate().split("-");
            if (Integer.parseInt(splitedDate[0]) == year && Integer.parseInt(splitedDate[1]) == month)
                lastMonth += entry.getCount();
        }
    }
}