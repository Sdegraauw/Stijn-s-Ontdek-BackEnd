package Ontdekstation013.ClimateChecker.features.measurement;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.List;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.utility.DayMeasurementResponse;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.utility.MeasurementLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeetJeStadService meetJeStadService;

    public List<MeasurementDTO> getMeasurementsAtTime(Instant dateTime) {
        // get measurements within a certain range of the dateTime
        int minuteMargin = meetJeStadService.getMinuteLimit();
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = dateTime.minus(Duration.ofMinutes(minuteMargin));
        params.EndDate = dateTime;

        List<Measurement> allMeasurements = meetJeStadService.getFilteredMeasurementsShortPeriod(params);

        List<Measurement> closestMeasurements = MeasurementLogic.filterClosestMeasurements(allMeasurements, dateTime);

        return closestMeasurements.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<DayMeasurementResponse> getHistoricalMeasurements(int id, Instant startDate, Instant endDate) {
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startDate;
        params.EndDate = endDate;
        params.StationIds.add(id);
        List<Measurement> measurements = meetJeStadService.getUnfilteredMeasurements(params);

        HashMap<LocalDate, Set<Measurement>> dayMeasurements = new LinkedHashMap<>();
        for (Measurement measurement : measurements) {
            if (measurement.getTemperature()!= null){
                LocalDate date = LocalDate.ofInstant(measurement.getTimestamp(), ZoneId.systemDefault());
                if (!dayMeasurements.containsKey(date)) {
                    dayMeasurements.put(date, new HashSet<>());
                }
                dayMeasurements.get(date).add(measurement);
            }
        }

        List<DayMeasurementResponse> responseList = new ArrayList<>();

        for (Map.Entry<LocalDate, Set<Measurement>> entry : dayMeasurements.entrySet()) {
            LocalDate date = entry.getKey();
            float minTemp = entry.getValue()
                    .stream()
                    .map(Measurement::getTemperature)
                    .min(Float::compare)
                    .orElse(Float.NaN);
            float maxTemp = entry.getValue()
                    .stream()
                    .map(Measurement::getTemperature)
                    .max(Float::compare)
                    .orElse(Float.NaN);
            float avgTemp = (float) entry.getValue()
                    .stream()
                    .mapToDouble(Measurement::getTemperature)
                    .average()
                    .orElse(Double.NaN);

            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM");

            DayMeasurementResponse response = new DayMeasurementResponse(
                    date.format(pattern),
                    avgTemp,
                    minTemp,
                    maxTemp
            );

            responseList.add(response);
        }

        return responseList;
    }

    private MeasurementDTO convertToDTO(Measurement entity) {
        MeasurementDTO dto = new MeasurementDTO();
        dto.setId(entity.getId());
        dto.setLongitude(entity.getLongitude());
        dto.setLatitude(entity.getLatitude());
        dto.setTemperature(entity.getTemperature());
        dto.setHumidity(entity.getHumidity());

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.of("Europe/Amsterdam"));
        dto.setTimestamp(formatter.format(entity.getTimestamp()));

        return dto;
    }
}

