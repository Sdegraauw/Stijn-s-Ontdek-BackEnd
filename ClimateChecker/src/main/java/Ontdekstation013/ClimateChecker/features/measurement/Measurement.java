package Ontdekstation013.ClimateChecker.features.measurement;

import io.micrometer.core.lang.Nullable;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
    private int id;
    private Instant timestamp;
    private float latitude;
    private float longitude;
    private Float temperature;
    private Float humidity;

    public static LinkedHashMap<LocalDate, Set<Measurement>> splitIntoDays (Collection<Measurement> measurements){
        LinkedHashMap<LocalDate, Set<Measurement>> dayMeasurements = new LinkedHashMap<>();
        for (Measurement measurement : measurements) {
            if (measurement.getTemperature() != null) {
                LocalDate date = LocalDate.ofInstant(measurement.getTimestamp(), ZoneId.systemDefault());
                if (!dayMeasurements.containsKey(date)) {
                    dayMeasurements.put(date, new HashSet<>());
                }

                dayMeasurements.get(date).add(measurement);
            }
        }
        return dayMeasurements;
    }
}
