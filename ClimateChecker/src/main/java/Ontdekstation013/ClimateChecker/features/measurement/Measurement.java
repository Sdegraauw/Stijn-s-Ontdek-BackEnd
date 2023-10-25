package Ontdekstation013.ClimateChecker.features.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Measurement {
    private int id;
    private Date timestamp;
    private float longitude;
    private float latitude;
    private float temperature;
    private float humidity;
}
