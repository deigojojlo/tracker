package deigojojlo.tracker.DataAnalist.SubType;

public class IslandEntry {
        String date;
        int count;

        public IslandEntry(String date, int count){
            this.date = date;
            this.count = count;
        }
        public String getDate(){
            return this.date;
        }

        public int getCount(){
            return this.count;
        }

        public void addLevel(int amount){
            this.count += amount;
        }

        protected void setDate(String date){
            this.date = date;
        }

        protected void setCount(int count){
            this.count = count;
        }

        public String getFormatLevel(){
            if (this.count >= 1_000_000_000){
                return (this.count / 1_000_000_000.0) + "B";
            } else if (this.count >= 1_000_000){
                return (this.count / 1_000_000) + "M";
            } else if (this.count >= 1_000){
                return (this.count >= 1_000) + "K";
            } return this.count + "";
        }
        
        public void add(JsonEntry e){
            if (e instanceof IslandEntry entry)
                this.count += entry.count;
        }
    }