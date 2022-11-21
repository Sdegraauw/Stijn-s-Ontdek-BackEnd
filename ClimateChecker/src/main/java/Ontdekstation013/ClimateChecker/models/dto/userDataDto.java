package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userDataDto extends Dto{

    long id;
    String firstName;
    String lastName;
    String Username;
    String mailAddress;
    boolean admin;
}
