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

import deigojojlo.tracker.DataAnalist.SubType.JobEntry;
import deigojojlo.tracker.util.Backup;
import deigojojlo.tracker.util.DateUtil;

public class Jobs implements Statistics{
    private static List<JobEntry> data;
    private static JobEntry dayJob;
    private static JobEntry allTimeJobs;
    private static JobEntry last30days;
    private static JobEntry lastMonth;
    public static final String[] JobsList = {"Miner","Hunter","Farmer","Lumberjack","Fisher","Explorer"};
    {
        Gson gson = new Gson();
        Path path = createFile("Jobs.json");

        try (FileReader reader = new FileReader(path.toAbsolutePath().toString())){
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

        last30days = new JobEntry();
        lastMonth = new JobEntry();
        allTimeJobs = new JobEntry();
        data.forEach(entry -> {
            allTimeJobs.add(entry);
        });
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
        Path path = Backup.backup("Jobs.json");
        try (FileWriter writer = new FileWriter(path.toAbsolutePath().toString())){
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


    public static int[][] getJob(){
        return dayJob.serialize();
    }

    public static int[] getJob(String job){
        return dayJob.getWrapper(job).serialize();
    }

    public static int[][] getDay(String date){
        for (JobEntry jobEntry : data) {
            if (jobEntry.getDate().equals(date))
                return jobEntry.serialize();
        }
        return null;
    }

    public static int[][] getLastMonth(){
        String[] splitedDate = LocalDate.now().toString().split("-");
        calculateLastMonth(Integer.parseInt(splitedDate[1]), Integer.parseInt(splitedDate[0]));
        return lastMonth.serialize();
    }

    public static int[][] getLast30days(){
        calculateLast30days();
        return last30days.serialize();
    }

    public static int[] getLastMonth(String job){
        String[] splitedDate = LocalDate.now().toString().split("-");
        calculateLastMonth(Integer.parseInt(splitedDate[1]), Integer.parseInt(splitedDate[0]));

        return lastMonth.getWrapper(job).serialize();
    }

    public static int[] getLast30days(String job){
        calculateLast30days();
        return last30days.getWrapper(job).serialize();
    }
    
}