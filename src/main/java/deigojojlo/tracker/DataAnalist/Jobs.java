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

import deigojojlo.tracker.DataAnalist.SubType.JobEntry;
import net.fabricmc.loader.api.FabricLoader;

public class Jobs {
    private static Jobs job = new Jobs();
    private static List<JobEntry> data;
    private static JobEntry dayJob;
    private static JobEntry allTimeJobs;
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

}