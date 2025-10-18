package deigojojlo.tracker.DataAnalist.SubType;

import java.time.LocalDate;

public class JobEntry implements JsonEntry {
    private String date;
    private JobWrapper miner;
    private JobWrapper hunter;
    private JobWrapper farmer;
    private JobWrapper lumberjack;
    private JobWrapper fisher;
    private JobWrapper explorer;

    public JobEntry(){
        this.date = LocalDate.now().toString();
        this.miner = new JobWrapper();
        this.hunter = new JobWrapper();
        this.farmer = new JobWrapper();
        this.lumberjack = new JobWrapper();
        this.fisher = new JobWrapper();
        this.explorer = new JobWrapper();
    }

    public JobWrapper getWrapper(String job){
        switch (job){
            case "MINER" : return this.miner;
            case "HUNTER" : return this.hunter;
            case "FARMER" : return this.farmer;
            case "LUMBERJACK" : return this.lumberjack;
            case "FISHER" : return this.fisher;
            case "EXPLORER" : return this.explorer;
            default : return null;
        }
    }
    public String getDate(){
        return this.date;
    }
    
    public void add(JsonEntry jsonEntry){
        if (jsonEntry instanceof JobEntry copy){
            this.date = copy.date;
            this.farmer.add(copy.farmer);
            this.explorer.add(copy.explorer);
            this.miner.add(copy.miner);
            this.lumberjack.add(copy.lumberjack);
            this.fisher.add(copy.fisher);
        }
    }
    public void copy(JobEntry copy){
        this.date = copy.date;
        this.farmer.copy(copy.farmer);
        this.explorer.copy(copy.explorer);
        this.miner.copy(copy.miner);
        this.lumberjack.copy(copy.lumberjack);
        this.fisher.copy(copy.fisher);
    }

    public int[][] serialize(){
        return new int[][]
            {
                this.miner.serialize(),
                this.hunter.serialize(),
                this.farmer.serialize(),
                this.lumberjack.serialize(),
                this.fisher.serialize(),
                this.explorer.serialize()
            };
    }
}