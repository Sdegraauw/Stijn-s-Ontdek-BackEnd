package Ontdekstation013.ClimateChecker.features.station.endpoint;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import Ontdekstation013.ClimateChecker.features.Dto;
import Ontdekstation013.ClimateChecker.features.sensor.endpoint.sensorDto;

@Getter
@Setter
public class stationDto extends Dto{

    long id;
    long registrationCode;
    String databaseTag;
    String name;
    float height;
    String direction;
    long locationId;
    String locationName;
    float latitude;
    float longitude;
    boolean ispublic;
    boolean isoutside;
    List<sensorDto> sensors;
}

