package Ontdekstation013.ClimateChecker.features.meetjestad;

import Ontdekstation013.ClimateChecker.features.location.Location;
import Ontdekstation013.ClimateChecker.features.location.LocationRepository;
import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementRepository;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementType;
import Ontdekstation013.ClimateChecker.features.station.Station;
import Ontdekstation013.ClimateChecker.features.station.StationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MeetJeStadCachingService {
    private final MeetJeStadService meetJeStadService;
    private final MeasurementRepository measurementRepository;
    private final StationRepository stationRepository;
    private final LocationRepository locationRepository;
    private Logger LOG = LoggerFactory.getLogger(MeetJeStadCachingService.class);

    @Scheduled(cron = "0 0-59/1 * * * *")
    private void UpdateCache() {
        LOG.info("Running UpdateCache");

        // Get the latest measurement to determine the start time
        Measurement measurement = measurementRepository.findTopByOrderByIdDesc();

        // Get measurements of the last 5 minutes, where the start time is determined above
        MeetJeStadParameters params = new MeetJeStadParameters();

        // If there are no measurements, get all measurements since the beginning of time
        // Also return latest measurement
        if (measurement == null)
            measurement = InstantiateCache();

        params.StartDate = measurement.getMeasurement_time();
        params.EndDate = Instant.now();

        // Get measurements from MeetJeStad API
        List<MeetJeStadDTO> measurementList = meetJeStadService.getMeasurements(params);

        // Save these measurements into the database along with creating stations and locations if necessary
        SaveMeasurements(measurementList);

        LOG.info(String.valueOf(measurementRepository.count()));
    }

    // This will instantiate the cache the first time, without causing a stack overflow :)
    private Measurement InstantiateCache() {
        // Get measurements per day
        Instant currentDate = Instant.parse("2017-11-28T00:00:00Z"); // First measurement in Tilburg
        Instant endDate = Instant.now();

        while (currentDate.isBefore(endDate)) {
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

        Measurement measurement = measurementRepository.findTopByOrderByIdDesc();

        return measurement;
    }

    // TODO: Refresh stations list after adding new one
    private void SaveMeasurements(List<MeetJeStadDTO> measurements) {
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
            }

            // Add measurement to db and associate with location
            station = stationRepository.findByMeetjestadId((long) measurement.getId());
            location = locationRepository.getById(station.getLastLocationId());

            // If location has changed, update last known location
            if (measurement.getLatitude() != location.getLatitude() || measurement.getLongitude() != location.getLongitude()) {
                Location newLocation = new Location();
                newLocation.setLatitude(measurement.getLatitude());
                newLocation.setLongitude(measurement.getLongitude());
                newLocation.setStation(station);
                location = locationRepository.save(newLocation);

                station.setLastLocationId(location.getId());
                stationRepository.save(station);
            }

            // Add measurements to db
            if (measurement.getTemperature() != null) {
                Measurement cachedMeasurement = new Measurement();
                cachedMeasurement.setStation(station);
                cachedMeasurement.setLocation(location);
                cachedMeasurement.setMeasurementTime(measurement.getTimestamp());
                cachedMeasurement.setType(MeasurementType.TEMPERATURE);
                cachedMeasurement.setValue(measurement.getTemperature());
                measurementRepository.save(cachedMeasurement);
            }

            if (measurement.getHumidity() != null) {
                Measurement cachedMeasurement = new Measurement();
                cachedMeasurement.setStation(station);
                cachedMeasurement.setLocation(location);
                cachedMeasurement.setMeasurementTime(measurement.getTimestamp());
                cachedMeasurement.setType(MeasurementType.HUMIDITY);
                cachedMeasurement.setValue(measurement.getHumidity());
                measurementRepository.save(cachedMeasurement);
            }
        }
    }
}
