package Ontdekstation013.ClimateChecker.utility;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;
import java.time.Duration;
import java.time.Instant;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;

/**
 * We use this class for measurement-related logic that is used in both MeasurementService and NeighbourhoodService
 */
public class MeasurementLogic {

    public static List<DayMeasurementResponse> splitIntoDayMeasurements(Collection<Measurement> measurements) {
        // split measurements into days
        LinkedHashMap<LocalDate, Set<Measurement>> dayMeasurements = new LinkedHashMap<>();
        for (Measurement measurement : measurements) {
            if (measurement.getTemperature() != null) {
                LocalDate date = LocalDate.ofInstant(measurement.getTimestamp(), ZoneId.systemDefault());
                if (!dayMeasurements.containsKey(date)) {
                    dayMeasurements.put(date, new HashSet<>());
                }

                dayMeasurements.get(date).add(measurement);
                IncorrectValueFilter(dayMeasurements.get(date).stream().toList());
            }
        }

        // process into DayMeasurementResponses
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM");
        List<DayMeasurementResponse> responseList = new ArrayList<>();
        for (Map.Entry<LocalDate, Set<Measurement>> entry : dayMeasurements.entrySet()) {
            DayMeasurementResponse response = new DayMeasurementResponse();

            response.setTimestamp(entry.getKey().format(pattern));
            response.setAvgTemp((float) entry.getValue()
                    .stream()
                    .filter(m -> m.getTemperature() != null)
                    .mapToDouble(Measurement::getTemperature)
                    .average()
                    .orElse(Double.NaN));
            response.setMinTemp(entry.getValue()
                    .stream()
                    .map(Measurement::getTemperature)
                    .filter(Objects::nonNull)
                    .min(Float::compare)
                    .orElse(Float.NaN));
            response.setMaxTemp(entry.getValue()
                    .stream()
                    .map(Measurement::getTemperature)
                    .filter(Objects::nonNull)
                    .max(Float::compare)
                    .orElse(Float.NaN));

            responseList.add(response);
        }

        return responseList;
    }

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
    public static List<Measurement> IncorrectValueFilter(List<Measurement> measurements) {
        int differenceFromAverageDivider = 2;
        int minimumDistanceAllowed = 3;
        float total = 0;
        float max = Integer.MIN_VALUE;
        float min = Integer.MAX_VALUE;

        for(Measurement measurement:measurements) {
            if (measurement.getTemperature()!= null){
                total += measurement.getTemperature();
                if(measurement.getTemperature()>max){
                    max = measurement.getTemperature();
                }
                if (measurement.getTemperature()<min){
                    min = measurement.getTemperature();
                }
            }
        }
        float adjustmentValue = Math.abs(min);
        float adjustedTotal = total + measurements.size()*adjustmentValue;
        float adjustedAverage = adjustedTotal/measurements.size();
        float allowedSpread = adjustedAverage/differenceFromAverageDivider;
        if (allowedSpread < minimumDistanceAllowed){
            allowedSpread = minimumDistanceAllowed;
        }
        float average = total/measurements.size();

        for (Measurement measurement:measurements) {
            if (measurement.getHumidity()!= null && (measurement.getHumidity()<0 || measurement.getHumidity()>100)){
                measurement.setHumidity(null);
            }
            if (measurement.getTemperature() != null){
                float absoluteDifferenceFromAverage = Math.abs(measurement.getTemperature()-average);
                if (absoluteDifferenceFromAverage > allowedSpread){
                    measurement.setTemperature(null);
                }
            }
        }

        return measurements;
    }
}
