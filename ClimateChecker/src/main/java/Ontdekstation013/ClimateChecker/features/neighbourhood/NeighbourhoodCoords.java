package Ontdekstation013.ClimateChecker.features.neighbourhood;

import Ontdekstation013.ClimateChecker.features.neighbourhood.endpoint.NeighbourhoodDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "region_cords")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NeighbourhoodCoords {
    @Id
    private long id;
    @Column(name = "longitude")
    private float longitude;
    @Column(name = "latitude")
    private float latitude;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Neighbourhood neighbourhood;
}
