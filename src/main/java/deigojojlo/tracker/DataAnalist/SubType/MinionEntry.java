package deigojojlo.tracker.DataAnalist.SubType;

public class MinionEntry implements JsonEntry{
    private String date;
    private double count;
    private int items;

    public MinionEntry(String date, double count, int items){
        this.date = date;
        this.count = count;
        this.items = items;
    }
    public String getDate(){
        return this.date;
    }

    public double getCount(){
        return this.count;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setCount(double count){
        this.count = count;
    }

    public void add(JsonEntry e){
        if (e instanceof MinionEntry minion ){
            this.count += minion.count;
            this.items += minion.items;
        }
    }

    public int getItems() {
        return this.items;
    }

    public void setItems(int items) {
        this.items = items;
    }


    public String getFormatMoney(){
        if (this.getCount() > 1_000_000_000){
            return (this.getCount() / 1_000_000_000 ) + "B";
        } else if (this.getCount() > 1_000_000){
            return (this.getCount() / 1_000_000 ) + "M";
        } else if (this.getCount() > 1_000){
            return (this.getCount() / 1_000) + "K";
        } else {
            return this.getCount() + "";
        }
    }

    public String getFormatItems(){
        if (this.getItems() > 1_000_000_000){
            return (this.getItems() / 1_000_000_000 ) + "B";
        } else if (this.getItems() > 1_000_000){
            return (this.getItems() / 1_000_000 ) + "M";
        } else if (this.getItems() > 1_000){
            return (this.getItems() / 1_000) + "K";
        } else {
            return this.getItems() + "";
        }
    }
    
}