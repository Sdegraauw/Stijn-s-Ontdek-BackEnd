package Ontdekstation013.ClimateChecker.features.station;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Station {
    @Id
    @Column
    private Long id;

    @Column
    private Long meetjestad_id;

    @Column
    private float longitude;

    @Column
    private float latitude;

    @Column
    private boolean is_visible;

    @Column
    private Instant created_at;

    @Column
    private Instant updated_at;
}
