package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class editUserDto extends Dto{

    int id;
    String firstName;
    String namePreposition;
    String lastName;
    String userName;
    String mailAddress;
    String password;
}
