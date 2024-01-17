package Ontdekstation013.ClimateChecker.features.measurement;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.List;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.utility.DayMeasurementResponse;
import Ontdekstation013.ClimateChecker.utility.MeasurementLogic;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
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

    public List<DayMeasurementResponse> getHistoricalMeasurements(int id, Instant startDate, Instant endDate) {
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startDate;
        params.EndDate = endDate;
        params.StationIds.add(id);
        List<Measurement> measurements = meetJeStadService.getUnfilteredMeasurements(params);

        return MeasurementLogic.splitIntoDayMeasurements(measurements);
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

