package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class editUserDto extends Dto{

    int id;
    String username;
    String mailAddress;
    String password;
}
