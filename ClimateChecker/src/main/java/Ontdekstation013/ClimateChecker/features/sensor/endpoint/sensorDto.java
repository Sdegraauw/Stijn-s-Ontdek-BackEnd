package Ontdekstation013.ClimateChecker.features.sensor.endpoint;

import Ontdekstation013.ClimateChecker.features.Dto;
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
