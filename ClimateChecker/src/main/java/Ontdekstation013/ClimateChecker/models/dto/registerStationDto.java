package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class registerStationDto extends Dto{

    int userId;
    String name;
    float height;
    String address;
    float latitude;
    float longitude;

}
