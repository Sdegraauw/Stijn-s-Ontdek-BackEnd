package Ontdekstation013.ClimateChecker.features.measurement;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.util.List;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeetJeStadService meetJeStadService;

    public List<MeasurementDTO> getLatestMeasurements() {
        List<Measurement> uniqueLatestMeasurements = meetJeStadService.getLatestMeasurements();
        return uniqueLatestMeasurements.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<MeasurementDTO> getMeasurementsAt(Instant dateTime) {
        // get dateTime in date format for use in logic
        Date dateTimeDate = Date.from(dateTime);

        // get measurements within a certain range of the dateTime
        int minuteMargin = meetJeStadService.getMinuteLimit();
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = dateTime.minus(Duration.ofMinutes(minuteMargin/2));
        params.EndDate = dateTime.plus(Duration.ofMinutes(minuteMargin/2));

        // get measurements from MeetJeStadAPI
        List<Measurement> allMeasurements = meetJeStadService.getMeasurements(params);

        // get closest measurements to datetime
        Map<Integer, Measurement> measurementHashMap = new HashMap<>();
        for (Measurement measurement : allMeasurements) {
            int id = measurement.getId();
            // check if this station is already in the map
            if (!measurementHashMap.containsKey(id))
                measurementHashMap.put(id, measurement);
                // check if the measurement is closer to the datetime
            else {
                long existingDifference = Math.abs(measurementHashMap.get(id).getTimestamp().getTime() - dateTimeDate.getTime());
                long newDifference = Math.abs(measurement.getTimestamp().getTime() - dateTimeDate.getTime());
                if (existingDifference > newDifference)
                    measurementHashMap.put(id, measurement);
            }
        }

        List<Measurement> closestMeasurements = new ArrayList<>(measurementHashMap.values());
        return closestMeasurements.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private MeasurementDTO convertToDTO(Measurement entity) {
        MeasurementDTO dto = new MeasurementDTO();
        dto.setId(entity.getId());
        dto.setLongitude(entity.getLongitude());
        dto.setLatitude(entity.getLatitude());
        dto.setTemperature(entity.getTemperature());
        dto.setHumidity(entity.getHumidity());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = simpleDateFormat.format(entity.getTimestamp());
        dto.setTimestamp(formattedDate);

        return dto;
    }
}
