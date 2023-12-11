package Ontdekstation013.ClimateChecker.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatUtil {

    public static Instant toInstant(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}
