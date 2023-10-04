package Ontdekstation013.ClimateChecker.features.station;

import Ontdekstation013.ClimateChecker.features.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Measurement {
    int stationId;
    LocalDateTime timeStamp;
    Location location;
    float tempreture;
}
