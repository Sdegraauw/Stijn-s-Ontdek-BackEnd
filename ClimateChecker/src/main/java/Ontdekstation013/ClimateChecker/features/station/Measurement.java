package Ontdekstation013.ClimateChecker.features.station;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Measurement {
    int stationId;
    LocalDateTime timeStamp;
    float lastLatitude;
    float lastLongitude;
    float temperature;
}
