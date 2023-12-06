package Ontdekstation013.ClimateChecker.features.location;

import Ontdekstation013.ClimateChecker.features.station.Station;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table()
public class Location {
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;

    private float latitude;

    private float longitude;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
