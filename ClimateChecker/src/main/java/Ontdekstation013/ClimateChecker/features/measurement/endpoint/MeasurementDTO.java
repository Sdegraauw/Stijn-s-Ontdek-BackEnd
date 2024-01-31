package Ontdekstation013.ClimateChecker.features.measurement.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeasurementDTO {
    @JsonProperty("id")
    private int id;
    // notation is "dd-mm-yyyy hh:mm:ss" in CET (12-11-2023 06:57:27 voor 7 uur s'ochtends nederlandse tijd)
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("longitude")
    private Float longitude;
    @JsonProperty("latitude")
    private Float latitude;
    @JsonProperty("temperatuur")
    private Float temperature;
    @JsonProperty("luchtvochtigheid")
    private Float humidity;
}