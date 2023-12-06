package Ontdekstation013.ClimateChecker.features.measurement;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.List;

import Ontdekstation013.ClimateChecker.exception.NotFoundException;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.responses.MeasurementHistoricalDataResponse;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.features.station.Station;
import Ontdekstation013.ClimateChecker.features.station.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeetJeStadService meetJeStadService;
    private final MeasurementRepository measurementRepository;
    private final StationRepository stationRepository;

    public List<MeasurementDTO> getMeasurementsAtTime(Instant dateTime) {
        // get measurements within a certain range of the dateTime
        int minuteMargin = meetJeStadService.getMinuteLimit();
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = dateTime.minus(Duration.ofMinutes(minuteMargin));
        params.EndDate = dateTime.plus(Duration.ofMinutes(minuteMargin));

        //List<Measurement> allMeasurements = meetJeStadService.getMeasurements(params);
        List<Measurement> allMeasurements = new ArrayList<>();

        // select closest measurements to datetime
        Map<Integer, Measurement> measurementHashMap = new HashMap<>();
        for (Measurement measurement : allMeasurements) {
            int id = measurement.getId();
            if (!measurementHashMap.containsKey(id))
                measurementHashMap.put(id, measurement);
            else {
                Duration existingDifference = Duration.between(dateTime, measurementHashMap.get(id).getTimestamp()).abs();
                Duration newDifference = Duration.between(dateTime, measurement.getTimestamp()).abs();
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
        List<Measurement> measurements = measurementRepository.findByStationAndMeasurementTimeIsAfterAndMeasurementTimeIsBefore(station, startDate, endDate);

        return measurements.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<MeasurementHistoricalDataResponse> getMeasurementsAverage(int id, Instant startDate, Instant endDate) {

        Station station = stationRepository.findByMeetjestadId((long) id);
        List<Measurement> measurements = measurementRepository.findByStationAndTypeAndMeasurementTimeIsAfterAndMeasurementTimeIsBefore(station, MeasurementType.TEMPERATURE, startDate, endDate);

        SortedMap<LocalDate, Set<Measurement>> dayMeasurements = new TreeMap<>();
        for (Measurement measurement : measurements) {
            LocalDate date = LocalDate.ofInstant(measurement.getMeasurementTime(), ZoneId.systemDefault());
            if (!dayMeasurements.containsKey(date)) {
                dayMeasurements.put(date, new HashSet<>());
            }
            dayMeasurements.get(date).add(measurement);
        }

        List<MeasurementHistoricalDataResponse> responseList = new ArrayList<>();

        for (Map.Entry<LocalDate, Set<Measurement>> entry : dayMeasurements.entrySet()) {
            LocalDate date = entry.getKey();
            float minTemp = entry.getValue()
                    .stream()
                    .map(Measurement::getValue)
                    .min(Float::compare)
                    .orElse(Float.NaN);
            float maxTemp = entry.getValue()
                    .stream()
                    .map(Measurement::getValue)
                    .max(Float::compare)
                    .orElse(Float.NaN);
            float avgTemp = (float) entry.getValue()
                    .stream()
                    .mapToDouble(Measurement::getValue)
                    .average()
                    .orElse(Double.NaN);

            MeasurementHistoricalDataResponse response = new MeasurementHistoricalDataResponse();
            response.setMinTemp(minTemp);
            response.setMaxTemp(maxTemp);
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
        dto.setTemperature(entity.getTemperature());
        dto.setHumidity(entity.getHumidity());

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.of("Europe/Amsterdam"));
        dto.setTimestamp(formatter.format(entity.getTimestamp()));

        return dto;
    }

    private List<MeasurementDTO> convertToDTO(List<Measurement> measurements) {
        List<MeasurementDTO> measurementDTOS = new ArrayList<>();

        for (Measurement measurement : measurements)
            measurementDTOS.add(convertToDTO(measurement));

        return measurementDTOS;
    }
}
