package Ontdekstation013.ClimateChecker.features.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Ontdekstation013.ClimateChecker.features.station.StationOld;

@Repository

public interface StationRepository extends JpaRepository<StationOld, Long> {
}
