package Ontdekstation013.ClimateChecker.features.location.endpoint;

import Ontdekstation013.ClimateChecker.features.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class editLocationDto extends Dto {
    public long id;
    public String direction;
    public float height;
    public boolean isOutside;
}
