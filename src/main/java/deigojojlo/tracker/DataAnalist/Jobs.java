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
import deigojojlo.tracker.DataAnalist.SubType.JobEntry;
import deigojojlo.tracker.util.DateUtil;
import net.fabricmc.loader.api.FabricLoader;

public class Jobs {
    private static Jobs job = new Jobs();
    private static List<JobEntry> data;
    private static JobEntry dayJob;
    private static JobEntry allTimeJobs;
    private static JobEntry last30days;
    private static JobEntry lastMonth;
    {
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "/tracker/jobs.json";

        try (FileReader reader = new FileReader(path)){
            Type itemListType = new TypeToken<List<JobEntry>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType ); // items
            
            JobEntry lastDay =  data.getLast();
            LocalDate date = LocalDate.now();

            if (lastDay.getDate().equals(date.toString())){
                dayJob = lastDay ;
            } else {
                dayJob = new JobEntry();
                data.addLast(dayJob);
            }
        } catch (IOException error){
            data = new ArrayList<>();
            error.printStackTrace();
        }
    }
    
    public static void addXP(String job,int amount){
        dayJob.getWrapper(job).addXP(amount);
    };

    public static void addLevel(String job,int amount){
        dayJob.getWrapper(job).addLevel(amount);;
    }

    public static void addMoney(String job,int amount){
        dayJob.getWrapper(job).addMoney(amount);;
    }

    public static void save(){
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "/tracker/jobs.json";

        try (FileWriter writer = new FileWriter(path)){
            data.getLast().copy(dayJob);
            writer.write(gson.toJson(data));
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    public static void calculateLast30days(){
        last30days = new JobEntry();
        String[] today = LocalDate.now().toString().split("-");
        int id = DateUtil.createIdentifier(Integer.parseInt(today[2]), Integer.parseInt(today[1]), Integer.parseInt(today[0]));
        for (JobEntry entry : data){
            String[] splitedDate = entry.getDate().split("-");
            int entryId = DateUtil.createIdentifier(Integer.parseInt(splitedDate[2]), Integer.parseInt(splitedDate[1]), Integer.parseInt(splitedDate[0]));
            if (id - entryId < 31 ) last30days.add(entry);
        }
    }

    public static void calculateLastMonth(int month,int year){
        lastMonth = new JobEntry();
        for (JobEntry entry : data){
            String[] splitedDate = entry.getDate().split("-");
            if (Integer.parseInt(splitedDate[0]) == year && Integer.parseInt(splitedDate[1]) == month)
                lastMonth.add(entry);
        }
    }

}