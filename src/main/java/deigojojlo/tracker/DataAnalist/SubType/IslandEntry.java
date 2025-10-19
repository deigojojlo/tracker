package deigojojlo.tracker.DataAnalist.SubType;

public class IslandEntry {
        String date;
        int count;
        Long time;

        public IslandEntry(String date, int count){
            this.date = date;
            this.count = count;
            this.time = null;
        }
        public String getDate(){
            return this.date;
        }

        public int getCount(){
            return this.count;
        }

        public Long getTime(){
            return this.time;
        }

        public void setTime(Long time){
            this.time = time;
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
            double count = this.count;
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
        
        public void add(JsonEntry e){
            if (e instanceof IslandEntry entry)
                this.count += entry.count;
        }
    }