package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class flatUserDto extends Dto{

    Long id;
    String username;
    String mailAddress;
    boolean admin;
}
