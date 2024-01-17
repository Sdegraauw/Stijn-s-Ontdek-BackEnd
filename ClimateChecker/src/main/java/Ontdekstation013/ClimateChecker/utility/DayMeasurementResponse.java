package Ontdekstation013.ClimateChecker.utility;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Used in compiling data into single days
 */
@NoArgsConstructor
@Getter
public class DayMeasurementResponse {
    @Setter
    @JsonProperty("timestamp")
    private String timestamp; // Format "dd-MM"
    @JsonProperty("avgTemp")
    private float avgTemp;
    @JsonProperty("minTemp")
    private float minTemp;
    @JsonProperty("maxTemp")
    private float maxTemp;

    public void setAvgTemp(Collection<Measurement> measurements){
        avgTemp = (float) measurements
                .stream()
                .mapToDouble(Measurement::getTemperature)
                .average()
                .orElse(Double.NaN);
    }

    public void setMinTemp(Collection<Measurement> measurements){
        minTemp = measurements
                .stream()
                .map(Measurement::getTemperature)
                .min(Float::compare)
                .orElse(Float.NaN);
    }

    public void setMaxTemp(Collection<Measurement> measurements) {
        maxTemp = measurements
                .stream()
                .map(Measurement::getTemperature)
                .max(Float::compare)
                .orElse(Float.NaN);
    }

    public static List<DayMeasurementResponse> splitIntoDayMeasurements(Collection<Measurement> measurements){
        LinkedHashMap<LocalDate, Set<Measurement>> dayMeasurements = Measurement.splitIntoDays(measurements);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM");
        List<DayMeasurementResponse> responseList = new ArrayList<>();
        for (Map.Entry<LocalDate, Set<Measurement>> entry : dayMeasurements.entrySet()) {
            DayMeasurementResponse response = new DayMeasurementResponse();
            response.setTimestamp(entry.getKey().format(pattern));
            response.setAvgTemp(entry.getValue());
            response.setMinTemp(entry.getValue());
            response.setMaxTemp(entry.getValue());

            responseList.add(response);
        }

        return responseList;
    }
}
