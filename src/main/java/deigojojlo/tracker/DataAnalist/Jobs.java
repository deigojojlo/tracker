package deigojojlo.tracker.DataAnalist;
import java.util.Map;

import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator.Context.Local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import deigojojlo.tracker.ignore.IgnoreJson;
import net.fabricmc.loader.api.FabricLoader;

import static java.util.Map.entry;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Jobs {
    private static Jobs job = new Jobs();
    private static List<JobJson> data;
    private static JobJson dayJob;
    private static JobJson allTimeJobs;
    {
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "chatUtil/jobs.json";

        try (FileReader reader = new FileReader(path)){
            Type itemListType = new TypeToken<List<JobJson>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType ); // items
            
            JobJson lastDay =  data.getLast();
            LocalDate date = LocalDate.now();

            if (lastDay.date.equals(date.toString())){
                dayJob = lastDay ;
            } else {
                dayJob = new JobJson();
                data.addLast(dayJob);
            }
        } catch (IOException error){
            data = new ArrayList<>();
            error.printStackTrace();
        }
    }
    
    public static void addXP(String job,int amount){
        dayJob.getWrapper(job).xp += amount;
    };

    public static void addLevel(String job,int amount){
        dayJob.getWrapper(job).level += amount;
    }

    public static void addMoney(String job,int amount){
        dayJob.getWrapper(job).money += amount;
    }

    public static void save(){
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "chatUtil/jobs.json";

        try (FileWriter writer = new FileWriter(path)){
            data.getLast().update(dayJob);
            writer.write(gson.toJson(data));
        } catch (IOException error){
            error.printStackTrace();
        }
    }


    private class Wrapper{
        private int xp;
        private int money;
        private int level;

        private Wrapper(){
            this.xp = 0;
            this.money = 0;
            this.level = 0;
        };
    }

    private class JobJson {
        private String date;
        private Wrapper farmer;
        private Wrapper fisher;
        private Wrapper lumberjack;
        private Wrapper explorer;
        private Wrapper miner;

        private JobJson(){
            this.date = LocalDate.now().toString();
            this.farmer = new Wrapper();
            this.explorer = new Wrapper();
            this.miner = new Wrapper();
            this.lumberjack = new Wrapper();
            this.fisher = new Wrapper();
        }

        private Wrapper getWrapper(String job){
            switch (job){
                case "FARMER" : return this.farmer;
                case "LUMBERJACK" : return this.lumberjack;
                case "MINER" : return this.miner;
                case "FISHER" : return this.fisher;
                case "EXPLORER" : return this.explorer;
                default : return null;
            }
        }

        private void update(JobJson copy){
            this.date = copy.date;
            this.farmer = copy.farmer;
            this.explorer = copy.explorer;
            this.miner = copy.miner;
            this.lumberjack = copy.lumberjack;
            this.fisher = copy.fisher;
        }
    }


}
