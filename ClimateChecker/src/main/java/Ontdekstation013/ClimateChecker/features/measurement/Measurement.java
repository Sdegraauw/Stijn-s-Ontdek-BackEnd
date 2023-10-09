package Ontdekstation013.ClimateChecker.features.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.stream.DoubleStream;
@Getter
public class Measurement {

    @JsonProperty("id")
    private int id;

    @JsonProperty("longitude")
    private float longitude;

    @JsonProperty("latitude")
    private float latitude;

    @JsonProperty("temperature")
    private float temperature;

    @JsonProperty("timestamp")
    private String timestamp;
}
