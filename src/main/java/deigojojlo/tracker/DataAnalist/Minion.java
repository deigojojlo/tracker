package deigojojlo.tracker.DataAnalist;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import deigojojlo.tracker.DataAnalist.SubType.MinionEntry;
import deigojojlo.tracker.util.Backup;
import deigojojlo.tracker.util.DateUtil;

public class Minion implements Statistics {
    private static MinionEntry dayMoney;
    private static List<MinionEntry> data ;
    private static MinionEntry allTimeMoney = new MinionEntry(null,0.0,0);
    private static MinionEntry last30days = new MinionEntry(null,0.0,0);
    private static MinionEntry lastMonth = new MinionEntry(null,0.0,0);
    private static Long time = null;

    public static void load(){
        Gson gson = new Gson();
        Path path = Backup.createFile("Minion.json");

        try (FileReader reader = new FileReader(path.toAbsolutePath().toString())){
            Type itemListType = new TypeToken<List<MinionEntry>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType ); // items
            if (data == null) data = new ArrayList<>();
            MinionEntry lastDay =  data.getLast();
            LocalDate date = LocalDate.now();

            if ( lastDay == null || !lastDay.getDate().equals(date.toString())){
                dayMoney = new MinionEntry(date.toString(), 0,0);
                data.addLast(dayMoney);
            } else {
                dayMoney = lastDay ;
            }
        } catch (IOException error){
            data = new ArrayList<>();
            dayMoney = new MinionEntry(LocalDate.now().toString(), 0,0);
            data.add(dayMoney);
            error.printStackTrace();
        }

        data.forEach(level -> {
            allTimeMoney.add(level);
        });
    }

    public static void addMoney(double amount){
        if (time == null)
            time = new Date().getTime();
        if (dayMoney != null)
            dayMoney.setCount(dayMoney.getCount() + amount);
        if (allTimeMoney != null)
            allTimeMoney.setCount(allTimeMoney.getCount() + amount);
    }

    public static void addItems(int items){
        if (dayMoney != null)
            dayMoney.setItems(dayMoney.getItems() + items);
        if (allTimeMoney != null)
            allTimeMoney.setItems(allTimeMoney.getItems() + items);
    }

    public static double getMoney(){
        if (dayMoney != null)
            return dayMoney.getCount();
        return 0;
    }

    public static String getTime(){
        if (time == null) return "0s";
        long t = new Date().getTime();
        long millis = t - time;
        long totalSeconds = millis / 1000;
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long hours = totalMinutes / 60;
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    public static String getFormatMoney(){
        if (dayMoney == null) return "0";
        return dayMoney.getFormatMoney();
    }

    public static String getFormatItems(){
        if (dayMoney == null) return "0";
        return dayMoney.getFormatItems();
    }

    public static int getItems(){
        if (dayMoney != null)
            return dayMoney.getItems();
        return 0;
    }

    public static MinionEntry getAllTimeMoney(){
        return allTimeMoney;
    }

    public static MinionEntry getLastMonth(){
        String[] splitedDate = LocalDate.now().toString().split("-");
        calculateLastMonth(Integer.parseInt(splitedDate[1]), Integer.parseInt(splitedDate[0]));
        return lastMonth;
    }

    public static MinionEntry getLast30days(){
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
        last30days = new MinionEntry(null, 0, 0);
        String[] today = LocalDate.now().toString().split("-");
        int id = DateUtil.createIdentifier(Integer.parseInt(today[2]), Integer.parseInt(today[1]), Integer.parseInt(today[0]));
        for (MinionEntry entry : data){
            String[] splitedDate = entry.getDate().split("-");
            int entryId = DateUtil.createIdentifier(Integer.parseInt(splitedDate[2]), Integer.parseInt(splitedDate[1]), Integer.parseInt(splitedDate[0]));
            if (id - entryId < 31 ) last30days.add(entry);
        }
    }

    public static void calculateLastMonth(int month,int year){
        lastMonth = new MinionEntry(null, 0, 0);
        for (MinionEntry entry : data){
            String[] splitedDate = entry.getDate().split("-");
            if (Integer.parseInt(splitedDate[0]) == year && Integer.parseInt(splitedDate[1]) == month)
                lastMonth.add(entry);
        }
    }
}