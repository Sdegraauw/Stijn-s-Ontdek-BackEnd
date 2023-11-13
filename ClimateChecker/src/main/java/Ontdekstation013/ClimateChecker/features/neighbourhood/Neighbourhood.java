package Ontdekstation013.ClimateChecker.features.neighbourhood;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "region")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Neighbourhood {
    @Id
    private long id;
    private String name;
}
