package Ontdekstation013.ClimateChecker.features.measurement;

import Ontdekstation013.ClimateChecker.features.station.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    Measurement findTopByOrderByIdDesc();

    List<Measurement> findByStationAndMeasurementTimeIsAfterAndMeasurementTimeIsBefore(Station station, Instant measurementTime, Instant measurementTime2);
    List<Measurement> findByStationAndTypeAndMeasurementTimeIsAfterAndMeasurementTimeIsBefore(Station station, MeasurementType type, Instant measurementTime, Instant measurementTime2);
}
