package Ontdekstation013.ClimateChecker.features.meetjestad;

import Ontdekstation013.ClimateChecker.features.measurement.CachedMeasurement;
import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementRepository;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementController;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.station.Station;
import Ontdekstation013.ClimateChecker.features.station.StationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MeetJeStadCachingService {
    private final MeetJeStadService meetJeStadService;
    private final MeasurementRepository measurementRepository;
    private final StationRepository stationRepository;
    private Logger LOG = LoggerFactory.getLogger(MeetJeStadCachingService.class);

    @Scheduled(cron = "0 0-59/1 * * * *")
    private void UpdateCache() {
        LOG.info("Running UpdateCache");

        // Get the latest measurement to determine the start time
        CachedMeasurement measurement = measurementRepository.findTopByOrderByIdDesc();

        // Get measurements of the last 5 minutes, where the start time is determined above
        MeetJeStadParameters params = new MeetJeStadParameters();

        // If there are no measurements, get all measurements since the beginning of time
        if (measurement == null)
            measurement = InstantiateCache();

        params.StartDate = measurement.getMeasurement_time();
        params.EndDate = Instant.now();

        List<Measurement> measurementList = meetJeStadService.getMeasurements(params);

        // Insert these measurements into the database along with creating stations if necessary
        SaveMeasurements(measurementList);

        LOG.info(String.valueOf(measurementRepository.count()));
    }

    // This will instantiate the cache the first time, without causing a stack overflow :)
    private CachedMeasurement InstantiateCache() {
        // Get measurements per day
        Instant currentDate = Instant.parse("2017-11-28T00:00:00Z"); // First measurement in Tilburg
        Instant endDate = Instant.now();

        while (currentDate.isBefore(endDate)) {
            // Get measurements of current date
            MeetJeStadParameters params = new MeetJeStadParameters();
            params.StartDate = currentDate;
            params.EndDate = currentDate.plus(1, ChronoUnit.DAYS);

            List<Measurement> measurements = meetJeStadService.getMeasurements(params);

            // Save measurements in database
            SaveMeasurements(measurements);

            // Go to next day
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        CachedMeasurement measurement = measurementRepository.findTopByOrderByIdDesc();

        return measurement;
    }

    private void SaveMeasurements(List<Measurement> measurements) {
        for (Measurement measurement : measurements) {
            Long stationId = measurement.getId();

            // Add station to the db if not exists
            if (!stationRepository.existsById(stationId)) {
                Station station = new Station();
                station.setMeetjestad_id(stationId);
                station.set_visible(true);

                stationRepository.save(station);
            }

            // If station exists, update location to latest location
            Station station = stationRepository.getById(stationId);
            // if not null
            station.setLatitude(measurement.getLatitude());
            station.setLongitude(measurement.getLongitude());

            stationRepository.save(station);

            // Measurement in database opslaan
            CachedMeasurement cachedMeasurement = new CachedMeasurement();
            cachedMeasurement.setStation_id(station.getMeetjestad_id());
            cachedMeasurement.setType_id(1L);
            cachedMeasurement.setValue(measurement.getTemperature());
            cachedMeasurement.setMeasurement_time(measurement.getTimestamp());

            CachedMeasurement cachedMeasurement = new CachedMeasurement();
            cachedMeasurement.setStation_id(station.getMeetjestad_id());
            cachedMeasurement.setType_id(2L);
            cachedMeasurement.setValue(measurement.getTemperature());
            cachedMeasurement.setMeasurement_time(measurement.getTimestamp());
        }
    }
}
