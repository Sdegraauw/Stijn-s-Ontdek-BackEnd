package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sensorDto extends Dto{

    long id;
    long stationId;
    long typeId;
    float data;

}
