package dao.PG;

import java.time.LocalDate;

public class DateFormater {

    public static String formate(LocalDate date) {
        return "'" + date.getYear() + "-" + date.getMonth() + "-" + date.getDayOfMonth() + "'";
    }
}
