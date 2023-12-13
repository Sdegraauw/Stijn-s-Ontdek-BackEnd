package Ontdekstation013.ClimateChecker.features.station;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meetjestad_id")
    private Long meetjestadId;

    @Column(name = "last_location_id")
    private Long lastLocationId;

    @Column(name = "is_visible")
    private boolean isVisible = true;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;
}
