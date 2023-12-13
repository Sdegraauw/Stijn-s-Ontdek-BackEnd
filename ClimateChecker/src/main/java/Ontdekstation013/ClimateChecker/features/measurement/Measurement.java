package Ontdekstation013.ClimateChecker.features.measurement;

import Ontdekstation013.ClimateChecker.features.location.Location;
import Ontdekstation013.ClimateChecker.features.station.Station;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne()
    @JoinColumn(name = "station_id")
    private Station station;

    @OneToMany()
    @JoinColumn(name = "measurement_id")
    private List<MeasurementResult> measurements = new ArrayList<>();

    @Column(name = "measurement_time")
    private Instant measurementTime;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
