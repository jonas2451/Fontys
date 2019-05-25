package dao.util;

import java.time.LocalDate;

/**
 * Utility class to format a {@code LocalDate}
 */
public class DateFormater {

    public static String formate(LocalDate date) {
        return "'" + date.getYear() + "-" + date.getMonth() + "-" + date.getDayOfMonth() + "'";
    }
}
