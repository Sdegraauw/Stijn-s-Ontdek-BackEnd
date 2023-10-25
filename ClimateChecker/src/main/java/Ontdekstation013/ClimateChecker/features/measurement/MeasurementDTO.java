package Ontdekstation013.ClimateChecker.features.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
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
    @JsonProperty("humidity")
    private float humidity;
}
