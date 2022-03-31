package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sensorDto extends Dto{

    Long id;
    Long stationId;
    Long typeId;
    float data;

}
