package deigojojlo.tracker.util;

public class DateUtil {
    public static int createIdentifier(int day, int month, int year){
        int id = day;
        switch (month) {
            case 1,3,5,7,8,10,12 : id += 31 ; break;
            case 2 : id += (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28; break;
            default : id += 30;
        }
        id += year * ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 366 : 365);
        return id;
    }
}
