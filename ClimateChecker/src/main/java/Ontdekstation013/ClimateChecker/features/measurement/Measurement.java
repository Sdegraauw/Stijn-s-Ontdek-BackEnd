package Ontdekstation013.ClimateChecker.features.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
    private int id;
    private Date timestamp;
    private float latitude;
    private float longitude;
    private float temperature;
    private float humidity;
}
