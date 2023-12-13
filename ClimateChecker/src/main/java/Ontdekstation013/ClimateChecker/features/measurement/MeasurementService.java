package Ontdekstation013.ClimateChecker.features.measurement;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.List;

import Ontdekstation013.ClimateChecker.exception.NotFoundException;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementController;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.responses.MeasurementHistoricalDataResponse;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadDTO;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.features.station.Station;
import Ontdekstation013.ClimateChecker.features.station.StationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeetJeStadService meetJeStadService;
    private final MeasurementRepository measurementRepository;
    private final StationRepository stationRepository;

    private Logger LOG = LoggerFactory.getLogger(MeasurementService.class);

    public List<MeasurementDTO> getMeasurementsAtTime(Instant dateTime) {
        int minuteMargin = meetJeStadService.getMinuteLimit();

        Instant startDate = dateTime.minus(Duration.ofMinutes(minuteMargin));
        Instant endDate = dateTime.plus(Duration.ofMinutes(minuteMargin));

        List<Measurement> allMeasurements = measurementRepository.findAllByMeasurementTimeBeforeAndMeasurementTimeAfterOrderByMeasurementTimeDesc(startDate, endDate);

        // select closest measurements to datetime
        Map<Long, Measurement> measurementHashMap = new HashMap<>();
        for (Measurement measurement : allMeasurements) {
            Long id = measurement.getId();
            if (!measurementHashMap.containsKey(id))
                measurementHashMap.put(id, measurement);
            else {
                Duration existingDifference = Duration.between(dateTime, measurementHashMap.get(id).getMeasurementTime()).abs();
                Duration newDifference = Duration.between(dateTime, measurement.getMeasurementTime()).abs();
                if (existingDifference.toSeconds() > newDifference.toSeconds())
                    measurementHashMap.put(id, measurement);
            }
        }

        List<Measurement> closestMeasurements = new ArrayList<>(measurementHashMap.values());
        return closestMeasurements.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<MeasurementDTO> getMeasurements(int id, Instant startDate, Instant endDate) {
        Station station = stationRepository.findByMeetjestadId((long) id);
        List<Measurement> measurements = measurementRepository
                .findByStationAndMeasurementTimeIsAfterAndMeasurementTimeIsBefore(station, startDate, endDate);

        return measurements.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<MeasurementHistoricalDataResponse> getMeasurementsAverage(int id, Instant startDate, Instant endDate) {
        Station station = stationRepository.findByMeetjestadId((long) id);
        List<Measurement> measurements = measurementRepository.findByStationAndMeasurements_MeasurementTypeAndMeasurementTimeIsAfterAndMeasurementTimeIsBefore(station, MeasurementType.TEMPERATURE, startDate, endDate);

        // Sort measurements into key value map where the key is the date
        SortedMap<LocalDate, Set<Measurement>> dayMeasurements = new TreeMap<>();
        for (Measurement measurement : measurements) {
            LocalDate date = LocalDate.ofInstant(measurement.getMeasurementTime(), ZoneId.systemDefault());
            if (!dayMeasurements.containsKey(date)) {
                dayMeasurements.put(date, new HashSet<>());
            }
            dayMeasurements.get(date).add(measurement);
        }

        // Get the min, max and avg temperature from the key value map, looping through it by date
        List<MeasurementHistoricalDataResponse> responseList = new ArrayList<>();
        for (Map.Entry<LocalDate, Set<Measurement>> measurement : dayMeasurements.entrySet()) {
            LocalDate date = measurement.getKey();

            float minTemp = Float.MIN_VALUE;
            float maxTemp = Float.MIN_VALUE;
            float avgTemp = 0.0f;
            int count = 0;
            for (Measurement measurement1 : measurement.getValue()) {
                if (measurement1.getMeasurements().isEmpty())
                    continue;

                if (measurement1.getMeasurements().size() > 1)
                    LOG.error("THIS SHOULD NOT BE POSSIBLE!");

                MeasurementResult measurementResult = measurement1.getMeasurements().get(0);
                float value = measurementResult.getValue();

                if (value > maxTemp)
                    maxTemp = value;
                if (value < minTemp)
                    minTemp = value;

                avgTemp += value;
                count++;
            }

            avgTemp /= count;

            MeasurementHistoricalDataResponse response = new MeasurementHistoricalDataResponse();
            response.setMaxTemp(maxTemp);
            response.setMinTemp(minTemp);
            response.setAvgTemp(avgTemp);

            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM");
            response.setTimestamp(date.format(pattern));

            responseList.add(response);
        }

        return responseList;
    }

    public List<MeasurementDTO> getCachedMeasurements() {
        List<Measurement> measurements = measurementRepository.findAll();

        return convertToDTO(measurements);
    }

    private MeasurementDTO convertToDTO(Measurement entity) {
        MeasurementDTO dto = new MeasurementDTO();
        dto.setId(entity.getStation().getMeetjestadId());
        dto.setLongitude(entity.getLocation().getLongitude());
        dto.setLatitude(entity.getLocation().getLatitude());

        Optional<MeasurementResult> optTemp = entity.getMeasurements().stream().filter(measurement -> MeasurementType.TEMPERATURE.equals(measurement.getMeasurementType())).findAny();
        optTemp.ifPresent(measurementResult -> dto.setTemperature(measurementResult.getValue()));

        Optional<MeasurementResult> optHum = entity.getMeasurements().stream().filter(measurement -> MeasurementType.HUMIDITY.equals(measurement.getMeasurementType())).findAny();
        optHum.ifPresent(measurementResult -> dto.setHumidity(measurementResult.getValue()));

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.of("Europe/Amsterdam"));
        dto.setTimestamp(formatter.format(entity.getMeasurementTime()));

        return dto;
    }

    private List<MeasurementDTO> convertToDTO(List<Measurement> measurements) {
        List<MeasurementDTO> measurementDTOS = new ArrayList<>();

        for (Measurement measurement : measurements)
            measurementDTOS.add(convertToDTO(measurement));

        return measurementDTOS;
    }
}
