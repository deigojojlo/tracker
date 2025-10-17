package deigojojlo.tracker.DataAnalist.SubType;

public class MinionEntry {
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
}