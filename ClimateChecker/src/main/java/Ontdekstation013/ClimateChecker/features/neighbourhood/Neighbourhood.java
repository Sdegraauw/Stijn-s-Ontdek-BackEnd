package Ontdekstation013.ClimateChecker.features.neighbourhood;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "region")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Neighbourhood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "neighbourhood")
    List<NeighbourhoodCoords> coordinates;

    public Neighbourhood(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
