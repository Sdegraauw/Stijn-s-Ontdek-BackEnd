package Ontdekstation013.ClimateChecker.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;

public class MeasurementLogic {

    public static List<DayMeasurementResponse> splitIntoDayMeasurements(Collection<Measurement> measurements){
        LinkedHashMap<LocalDate, Set<Measurement>> dayMeasurements = Measurement.splitIntoDays(measurements);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM");
        List<DayMeasurementResponse> responseList = new ArrayList<>();
        for (Map.Entry<LocalDate, Set<Measurement>> entry : dayMeasurements.entrySet()) {
            DayMeasurementResponse response = new DayMeasurementResponse();
            response.setTimestamp(entry.getKey().format(pattern));
            response.setAvgTemp((float) entry.getValue()
                                .stream()
                                .mapToDouble(Measurement::getTemperature)
                                .average()
                                .orElse(Double.NaN));
            response.setMinTemp(entry.getValue()
                                .stream()
                                .map(Measurement::getTemperature)
                                .min(Float::compare)
                                .orElse(Float.NaN));
            response.setMaxTemp(entry.getValue()
                                .stream()
                                .map(Measurement::getTemperature)
                                .max(Float::compare)
                                .orElse(Float.NaN));
            responseList.add(response);
        }

        return responseList;
    }

}
