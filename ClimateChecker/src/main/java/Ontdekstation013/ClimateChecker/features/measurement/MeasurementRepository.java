package Ontdekstation013.ClimateChecker.features.measurement;

import Ontdekstation013.ClimateChecker.features.station.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    Measurement findTopByOrderByIdDesc();

    // Get all measurements between timeframe
    List<Measurement> findAllByMeasurementTimeBeforeAndMeasurementTimeAfterOrderByMeasurementTimeDesc(Instant startTime, Instant endTime);

    // Get all unique measurements between timeframe
    List<Measurement> findDistinctByMeasurementTimeBeforeAndMeasurementTimeAfterOrderByMeasurementTimeDesc(Instant startTime, Instant endTime);

    // Get measurements by station between timeframe
    List<Measurement> findByStationAndMeasurementTimeIsAfterAndMeasurementTimeIsBefore(Station station, Instant measurementTime, Instant measurementTime2);

    // Get measurements by station and measurement type between timeframe
    List<Measurement> findByStationAndMeasurements_MeasurementTypeAndMeasurementTimeIsAfterAndMeasurementTimeIsBefore(Station station, MeasurementType type, Instant measurementTime, Instant measurementTime2);
}
