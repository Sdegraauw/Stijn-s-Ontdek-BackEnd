package Ontdekstation013.ClimateChecker.features.station;

import Ontdekstation013.ClimateChecker.features.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

@Getter
@Setter
@NoArgsConstructor
public class Station {
    int Id;
    float lastLatitude;
    float lastLongitude;
}
