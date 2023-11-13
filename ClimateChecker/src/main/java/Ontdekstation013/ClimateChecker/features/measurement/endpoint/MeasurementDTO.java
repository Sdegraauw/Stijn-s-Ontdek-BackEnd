package Ontdekstation013.ClimateChecker.features.measurement.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeasurementDTO {
    @JsonProperty("id")
    private int id;
    // notation is dd-mm-yyyy hh:mm:ss in CET (12-11-2023 06:57:27 voor 7 uur s'ochtends nederlandse tijd)
    // todo: update when switching from Date to Instant in Measurement
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("longitude")
    private float longitude;
    @JsonProperty("latitude")
    private float latitude;
    @JsonProperty("temperature")
    private float temperature;
    @JsonProperty("humidity")
    private float humidity;
}
