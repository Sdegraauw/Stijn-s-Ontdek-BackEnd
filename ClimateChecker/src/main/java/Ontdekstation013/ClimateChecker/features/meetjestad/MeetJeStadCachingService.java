package Ontdekstation013.ClimateChecker.features.meetjestad;

import Ontdekstation013.ClimateChecker.features.location.Location;
import Ontdekstation013.ClimateChecker.features.location.LocationRepository;
import Ontdekstation013.ClimateChecker.features.measurement.*;
import Ontdekstation013.ClimateChecker.features.station.Station;
import Ontdekstation013.ClimateChecker.features.station.StationRepository;
import Ontdekstation013.ClimateChecker.utility.GpsTriangulation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MeetJeStadCachingService {
    private final MeetJeStadService meetJeStadService;
    private final MeasurementRepository measurementRepository;
    private final MeasurementResultRepository measurementResultRepository;
    private final StationRepository stationRepository;
    private final LocationRepository locationRepository;
    private final EntityManagerFactory entityManagerFactory;

    private final Logger LOG = LoggerFactory.getLogger(MeetJeStadCachingService.class);

    // TODO: Daadwerkelijk schedulen, zie cron hieronder
    //@Scheduled(cron = "0 0-59/1 * * * *")
    @Scheduled(initialDelay = 3000, fixedDelay = 600000000)
    public void UpdateCache() {
        LOG.info("Running UpdateCache");

        // Get the latest measurement to determine the start time
        Measurement measurement = measurementRepository.findTopByOrderByIdDesc();

        // If there are no measurements, get all measurements since the beginning of time
        // Also return latest measurement
        if (measurement == null)
            InstantiateCache(null);
        else
            InstantiateCache(measurement.getMeasurementTime());

        LOG.info(String.valueOf(measurementRepository.count()));
    }

    // This will instantiate the cache the first time, without causing a stack overflow :)
    private void InstantiateCache(Instant startDate) {
        LOG.info("Instantiating cache");

        // Get measurements per day
        Instant currentDate = startDate == null ? Instant.parse("2018-09-26T00:00:00Z") : startDate;
        Instant endDate = Instant.now();

        while (currentDate.isBefore(endDate)) {
            LOG.info("Caching data between: " + currentDate.toString() + " - " + currentDate.plus(1, ChronoUnit.DAYS).toString());

            // Get measurements of current date
            MeetJeStadParameters params = new MeetJeStadParameters();
            params.StartDate = currentDate;
            params.EndDate = currentDate.plus(1, ChronoUnit.DAYS);

            List<MeetJeStadDTO> measurements = meetJeStadService.getMeasurements(params);

            // Save measurements in database
            SaveMeasurements(measurements);

            // Go to next day
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }
    }

    @Async
    public void SaveMeasurements(List<MeetJeStadDTO> measurements) {
        LOG.info("Saving " + measurements.size() + " measurements to the database");

        List<Station> stations = stationRepository.findAll();

        for (MeetJeStadDTO measurement : measurements) {
            int stationId = measurement.getId();

            // Add station to the db if not exists
            boolean stationExists = false;
            for (Station station : stations) {
                if (Objects.equals(station.getMeetjestadId(), (long) stationId)) {
                    stationExists = true;
                    break;
                }
            }

            // If station does not exist: create station and create new location
            Station station = null;
            Location location = null;
            if (!stationExists) {
                station = new Station();
                station.setMeetjestadId((long) stationId);
                station.setVisible(true);
                station = stationRepository.save(station);

                location = new Location();
                location.setStation(station);
                location.setLatitude(measurement.getLatitude());
                location.setLongitude(measurement.getLongitude());
                location = locationRepository.save(location);

                station.setLastLocationId(location.getId());
                stationRepository.save(station);

                stations.add(station);
            }

            if (station == null)
                station = stationRepository.findByMeetjestadId((long) measurement.getId());
            if (location == null)
                location = locationRepository.findTopByStationOrderByCreatedAtDesc(station);

            // If location has changed, update last known location
            if (measurement.getLatitude() != location.getLatitude() || measurement.getLongitude() != location.getLongitude()) {
                Location newLocation = new Location();
                newLocation.setLatitude(measurement.getLatitude());
                newLocation.setLongitude(measurement.getLongitude());
                newLocation.setStation(station);
                location = locationRepository.save(newLocation);

                station.setLastLocationId(location.getId());
                // TODO: Update updated_at
                stationRepository.save(station);
            }

            // Add measurements to db
            Measurement cachedMeasurement = new Measurement();
            cachedMeasurement.setStation(station);
            cachedMeasurement.setLocation(location);
            cachedMeasurement.setMeasurementTime(measurement.getTimestamp());

            cachedMeasurement = measurementRepository.save(cachedMeasurement);

            if (measurement.getTemperature() != null) {
                MeasurementResult result = new MeasurementResult();
                result.setMeasurementType(MeasurementType.TEMPERATURE);
                result.setValue(measurement.getTemperature());
                result.setMeasurementId(cachedMeasurement.getId());

                measurementResultRepository.save(result);
                cachedMeasurement.getMeasurements().add(result);
            }

            if (measurement.getHumidity() != null) {
                MeasurementResult result = new MeasurementResult();
                result.setMeasurementType(MeasurementType.HUMIDITY);
                result.setValue(measurement.getHumidity());
                result.setMeasurementId(cachedMeasurement.getId());

                measurementResultRepository.save(result);
                cachedMeasurement.getMeasurements().add(result);
            }

            measurementRepository.save(cachedMeasurement);
        }

        LOG.info("Saved " + measurements.size() + " measurements to the database");
    }
}
