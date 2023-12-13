package Ontdekstation013.ClimateChecker.features.measurement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MeasurementResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measurement_id")
    private Long measurementId;

    @Enumerated
    @Column(name = "measurement_type")
    private MeasurementType measurementType;

    private float value;
}
