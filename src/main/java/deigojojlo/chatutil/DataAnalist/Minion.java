package deigojojlo.chatutil.DataAnalist;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import net.fabricmc.loader.api.FabricLoader;

public class Minion {
    private static Money dayMoney;
    private static List<Money> data ;
    private static int allTimeMoney = 0;
    private static int monthlyTimeMoney = 0;
    {
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "chatUtil/minon.json";

        try (FileReader reader = new FileReader(path)){
            Type itemListType = new TypeToken<List<Money>>(){}.getType(); // the type of the list
            data = gson.fromJson(reader, itemListType ); // items
            
            Money lastDay =  data.getLast();
            LocalDate date = LocalDate.now();

            if (lastDay.getDate().equals(date.toString())){
                dayMoney = lastDay ;
            } else {
                dayMoney = new Money(date.toString(), 0);
                data.addLast(dayMoney);
            }
        } catch (IOException error){
            data = new ArrayList<>();
            error.printStackTrace();
        }

        LocalDate date = LocalDate.now();
        data.forEach(level -> {
            allTimeMoney += level.getCount();
            String[] splitDate = level.getDate().split("-");
            if (Integer.parseInt(splitDate[1]) == date.getMonth().ordinal() && Integer.parseInt(splitDate[2]) == date.getYear()){ monthlyTimeMoney += level.getCount();}
        });
    }

    public static void addMoney(int amount){
        dayMoney.setCount(dayMoney.getCount() + amount);
    }

    public static int getMoney(){
        return dayMoney.getCount();
    }


    public static int getAllTimeMoney(){
        return allTimeMoney;
    }

    public static int getMonth(){
        return monthlyTimeMoney;
    }

    public static void save(){
        Gson gson = new Gson();
        String path = FabricLoader.getInstance().getGameDir().toString() + "chatUtil/minion.json";

        try (FileWriter writer = new FileWriter(path)){
            writer.write(gson.toJson(data));
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    private class Money {
        String date;
        int count;

        private Money(String date, int count){
            this.date = date;
            this.count = count;
        }
        private String getDate(){
            return this.date;
        }

        private int getCount(){
            return this.count;
        }

        private void setDate(String date){
            this.date = date;
        }

        private void setCount(int count){
            this.count = count;
        }
    }
}
