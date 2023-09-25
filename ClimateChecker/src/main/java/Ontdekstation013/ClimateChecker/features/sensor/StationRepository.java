package Ontdekstation013.ClimateChecker.features.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Ontdekstation013.ClimateChecker.features.station.Station;

@Repository

public interface StationRepository extends JpaRepository<Station, Long> {
}
