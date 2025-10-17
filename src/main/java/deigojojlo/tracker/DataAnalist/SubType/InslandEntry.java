package deigojojlo.tracker.DataAnalist.SubType;

public class InslandEntry {
        String date;
        int count;

        public InslandEntry(String date, int count){
            this.date = date;
            this.count = count;
        }
        public String getDate(){
            return this.date;
        }

        public int getCount(){
            return this.count;
        }

        protected void setDate(String date){
            this.date = date;
        }

        protected void setCount(int count){
            this.count = count;
        }
    }