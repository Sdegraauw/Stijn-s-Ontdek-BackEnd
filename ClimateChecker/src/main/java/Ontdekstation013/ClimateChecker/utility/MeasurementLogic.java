package Ontdekstation013.ClimateChecker.utility;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class MeasurementLogic {
    /**
     * Filters out measurements of the same station that are further away from the given timestamp
     *
     * @return closest measurement to given dateTime for each unique station in given collection
     */
    public static List<Measurement> filterClosestMeasurements(Collection<Measurement> measurements, Instant dateTime) {
        Map<Integer, Measurement> measurementHashMap = new HashMap<>();
        for (Measurement measurement : measurements) {
            int id = measurement.getId();
            if (!measurementHashMap.containsKey(id))
                measurementHashMap.put(id, measurement);
            else {
                Duration existingDifference = Duration.between(dateTime, measurementHashMap.get(id).getTimestamp()).abs();
                Duration newDifference = Duration.between(dateTime, measurement.getTimestamp()).abs();
                if (existingDifference.toSeconds() > newDifference.toSeconds())
                    measurementHashMap.put(id, measurement);
            }
        }
        return new ArrayList<>(measurementHashMap.values());
    }
}
