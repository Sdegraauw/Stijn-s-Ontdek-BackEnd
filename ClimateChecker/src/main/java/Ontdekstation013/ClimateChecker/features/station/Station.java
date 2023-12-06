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
