package Ontdekstation013.ClimateChecker.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class mailDto extends Dto {
    Long id;
    String header;
    String body;
    String footer;
}
