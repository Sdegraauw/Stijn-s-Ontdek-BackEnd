package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionAverageDto {

    double temperature;
    double nitrogen;
    double carbonDioxide;
    double particulateMatter;
    double humidity;
    double windSpeed;

}
