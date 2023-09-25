package Ontdekstation013.ClimateChecker.features.location.endpoint;

import Ontdekstation013.ClimateChecker.features.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createLocationDto extends Dto {
    public float latitude;
    public float longitude;
}
