package Ontdekstation013.ClimateChecker.features.neighbourhood;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name = "region_id")
    private long regionId;
}
