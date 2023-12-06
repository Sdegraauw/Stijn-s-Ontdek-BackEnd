package Ontdekstation013.ClimateChecker.features.location;

import Ontdekstation013.ClimateChecker.features.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}