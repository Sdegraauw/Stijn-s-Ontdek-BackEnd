package Ontdekstation013.ClimateChecker.features.station.endpoint;

import Ontdekstation013.ClimateChecker.features.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createStationDto extends Dto{

    public long registrationCode;
    public String databaseTag;
    public long locationId;
    public float latitude;
    public float longitude;
}
