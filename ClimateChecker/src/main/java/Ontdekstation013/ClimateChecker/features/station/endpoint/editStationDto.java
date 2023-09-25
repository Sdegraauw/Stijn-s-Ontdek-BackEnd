package Ontdekstation013.ClimateChecker.features.station.endpoint;

import Ontdekstation013.ClimateChecker.features.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class editStationDto extends Dto{

    long Id;
    String name;
    boolean isPublic;
    long registrationCode;
    String databaseTag;
}
