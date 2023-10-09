package Ontdekstation013.ClimateChecker.features.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MeasurementDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("longitude")
    private float longitude;
    @JsonProperty("latitude")
    private float latitude;
    @JsonProperty("temperature")
    private float temperature;
}
