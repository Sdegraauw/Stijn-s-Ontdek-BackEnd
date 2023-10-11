package Ontdekstation013.ClimateChecker.features.neighbourhood;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "region_cords")
@Getter
public class NeighbourhoodCoords {
    @Id
    private long id;
    @Column(name = "y_cord")
    private float latitude;
    @Column(name = "x_cord")
    private float longitude;
    @Column(name = "region_id")
    private long regionId;
}
