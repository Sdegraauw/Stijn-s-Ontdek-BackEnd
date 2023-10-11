package Ontdekstation013.ClimateChecker.features.neighbourhood;

import Ontdekstation013.ClimateChecker.features.location.Location;
import Ontdekstation013.ClimateChecker.features.station.Measurement;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
public class Neighbourhood {
    int id;
    String name;

    List<Measurement> Measurements;
    List<Location> Coordinates; // Voor het tekenen van een zone op de kaart

    float averageTemperature;
}
