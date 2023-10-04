package Ontdekstation013.ClimateChecker.features.station;

import Ontdekstation013.ClimateChecker.features.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class Station {
    int Id;
    Location lastLocation;
}
