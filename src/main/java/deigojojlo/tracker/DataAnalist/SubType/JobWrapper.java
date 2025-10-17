package deigojojlo.tracker.DataAnalist.SubType;

public class JobWrapper{
        private int xp;
        private int money;
        private int level;

        protected JobWrapper(){
            this.xp = 0;
            this.money = 0;
            this.level = 0;
        };

    public int getXp() {
        return this.xp;
    }

    protected void setXp(int xp) {
        this.xp = xp;
    }

    public int getMoney() {
        return this.money;
    }

    protected void setMoney(int money) {
        this.money = money;
    }

    public int getLevel() {
        return this.level;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    public void addMoney(int amount){
        this.money += amount;
    }

    public void addXP(int amount){
        this.xp += amount;
    }

    public void addLevel(int amount){
        this.level += amount;
    }


    public void copy(JobWrapper wrapper){
        this.level = wrapper.level;
        this.money = wrapper.money;
        this.xp = wrapper.xp;
    }

    public void add(JobWrapper wrapper){
        this.level += wrapper.level;
        this.money += wrapper.money;
        this.xp += wrapper.xp;
    }
}