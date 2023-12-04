package Ontdekstation013.ClimateChecker.features.measurement;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
    private Long id;
    private Instant timestamp;
    private float latitude;
    private float longitude;
    private float temperature;
    private float humidity;
}
