package Ontdekstation013.ClimateChecker.features.measurement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "measurements")
@Getter
@Setter
public class CachedMeasurement {
    @Id
    @Column
    private int id;

    @Column
    private Long station_id;

    @Column
    private Long type_id;

    @Column
    private float value;

    @Column
    private Instant measurement_time;

    @Column
    private Instant created_at;
}
