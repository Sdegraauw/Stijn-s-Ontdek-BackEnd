package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class userDto extends Dto{

    long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String mailAddress;
    private String jwsString;
    private boolean admin;
}
