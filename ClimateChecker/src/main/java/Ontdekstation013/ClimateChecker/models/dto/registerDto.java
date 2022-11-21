package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class registerDto extends Dto{

    String firstName;
    String lastName;
    String userName;
    String mailAddress;
    String password;
}
