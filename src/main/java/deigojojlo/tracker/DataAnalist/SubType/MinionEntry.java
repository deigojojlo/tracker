package deigojojlo.tracker.DataAnalist.SubType;

public class MinionEntry implements JsonEntry{
    private String date;
    private double count;
    private int items;
    private Long time;

    public MinionEntry(String date, double count, int items){
        this.date = date;
        this.count = count;
        this.items = items;
        this.time = null;
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

    public void setTime(Long time){
        this.time = time;
    }

    public Long getTime(){
        return this.time;
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
        double count = this.getCount();
        if (count > 1_000_000_000L) {
            double value = (double) count / 1_000_000_000.0;
            return String.format("%.2f", value) + "B";
        } else if (count > 1_000_000L) {
            double value = (double) count / 1_000_000.0;
            return String.format("%.2f", value) + "M";
        } else if (count > 1_000L) {
            double value = (double) count / 1_000.0;
            return String.format("%.2f", value) + "K";
        } else {
            return String.valueOf(count);
        }
    }

    public String getFormatItems(){
        double count = this.getItems();
        if (count > 1_000_000_000L) {
            double value = (double) count / 1_000_000_000.0;
            return String.format("%.2f", value) + "B";
        } else if (count > 1_000_000L) {
            double value = (double) count / 1_000_000.0;
            return String.format("%.2f", value) + "M";
        } else if (count > 1_000L) {
            double value = (double) count / 1_000.0;
            return String.format("%.2f", value) + "K";
        } else {
            return String.valueOf(count);
        }
    }
}