package Ontdekstation013.ClimateChecker.features.measurement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.util.List;

import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeetJeStadService meetJeStadService;

    public List<MeasurementDTO> getLatestMeasurements(int minuteLimit) {
        // define start and end times
        Instant endMoment = Instant.now();
        Instant startMoment = endMoment.minus(Duration.ofMinutes(minuteLimit));
        
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startMoment;
        params.EndDate = endMoment;

        // get measurements from MeetJeStadAPI
        List<Measurement> latestMeasurements = meetJeStadService.getMeasurements(params);
        // filter out older readings of same stationId
        Map<Integer, Measurement> uniqueLatestMeasurements = new HashMap<>();
        for (Measurement measurement : latestMeasurements) {
            int id = measurement.getId();
            // Check if this id is already in the map and if its more recent
            if (!uniqueLatestMeasurements.containsKey(id) ||
                    measurement.getTimestamp().after(uniqueLatestMeasurements.get(id).getTimestamp())) {
                uniqueLatestMeasurements.put(id, measurement);
            }
        }

        // convert to DTOs
        List<MeasurementDTO> uniqueLatestMeasurementDTOs = new ArrayList<>();
        for (Measurement measurement : uniqueLatestMeasurements.values()) {
            MeasurementDTO measurementDto = new MeasurementDTO();
            measurementDto.setId(measurement.getId());
            measurementDto.setLongitude(measurement.getLongitude());
            measurementDto.setLatitude(measurement.getLatitude());
            measurementDto.setTemperature(measurement.getTemperature());
            measurementDto.setHumidity(measurement.getHumidity());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = simpleDateFormat.format(measurement.getTimestamp());
            measurementDto.setTimestamp(formattedDate);

            uniqueLatestMeasurementDTOs.add(measurementDto);
        }
        return uniqueLatestMeasurementDTOs;
    }
}
