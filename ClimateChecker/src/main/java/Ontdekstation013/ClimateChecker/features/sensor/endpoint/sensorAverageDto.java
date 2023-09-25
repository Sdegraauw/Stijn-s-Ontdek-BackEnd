package Ontdekstation013.ClimateChecker.features.sensor.endpoint;

import Ontdekstation013.ClimateChecker.features.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sensorAverageDto extends Dto {

    double temperature;
    double nitrogen;
    double carbonDioxide;
    double particulateMatter;
    double humidity;
    double windSpeed;
}