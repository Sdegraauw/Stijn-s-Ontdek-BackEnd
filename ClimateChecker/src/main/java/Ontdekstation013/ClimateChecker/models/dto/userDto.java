package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class userDto extends Dto{

    long id;
    private String firstName;
    private String lastName;
    private String Username;
    private String mailAddress;
    private Byte passwordHash;
    private Byte passwordSalt;
    private boolean Admin;
}
