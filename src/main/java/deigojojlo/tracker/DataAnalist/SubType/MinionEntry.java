package deigojojlo.tracker.DataAnalist.SubType;

public class MinionEntry implements JsonEntry{
    String date;
    int count;

    public MinionEntry(String date, int count){
        this.date = date;
        this.count = count;
    }
    public String getDate(){
        return this.date;
    }

    public int getCount(){
        return this.count;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void add(JsonEntry e){
        if (e instanceof MinionEntry minion )
            this.count += minion.count;
    }
}