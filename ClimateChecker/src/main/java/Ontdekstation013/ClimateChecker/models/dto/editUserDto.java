package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class editUserDto extends Dto{
    Long id;
    String firstName;
    String lastName;
    String userName;
    String mailAddress;
    String password;
}
