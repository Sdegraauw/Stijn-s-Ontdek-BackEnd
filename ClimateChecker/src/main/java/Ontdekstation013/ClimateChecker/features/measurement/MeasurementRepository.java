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
    List<Measurement> findAllByMeasurementTimeBeforeAndMeasurementTimeAfterOrderByMeasurementTimeDesc(Instant endDate, Instant startDate);

    // Get all unique measurements between timeframe
    List<Measurement> findDistinctByMeasurementTimeBeforeAndMeasurementTimeAfterOrderByMeasurementTimeDesc(Instant endDate, Instant startDate);

    // Get measurements by station between timeframe
    List<Measurement> findByStationAndMeasurementTimeBeforeAndMeasurementTimeAfter(Station station, Instant endDate, Instant startDate);

    List<Measurement> findAllByStationInAndMeasurementResults_MeasurementTypeAndMeasurementTimeBeforeAndMeasurementTimeAfter(List<Station> stations, MeasurementType type, Instant endDate, Instant startDate);

    // Get measurements by station and measurement type between timeframe
    List<Measurement> findByStationAndMeasurementResults_MeasurementTypeAndMeasurementTimeBeforeAndMeasurementTimeAfter(Station station, MeasurementType type, Instant endDate, Instant startDate);
}
