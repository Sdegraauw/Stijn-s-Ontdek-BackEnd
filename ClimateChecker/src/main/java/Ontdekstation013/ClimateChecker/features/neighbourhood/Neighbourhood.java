package Ontdekstation013.ClimateChecker.features.neighbourhood;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "region")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Neighbourhood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
}
