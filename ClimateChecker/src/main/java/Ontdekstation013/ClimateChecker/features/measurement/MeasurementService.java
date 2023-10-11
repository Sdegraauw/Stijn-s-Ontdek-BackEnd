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

    public List<MeasurementDTO> getLatestMeasurements() {
        List<Measurement> uniqueLatestMeasurements = meetJeStadService.getLatestMeasurements();

        // convert to DTOs
        List<MeasurementDTO> uniqueLatestMeasurementDTOs = new ArrayList<>();
        for (Measurement measurement : uniqueLatestMeasurements) {
            MeasurementDTO measurementDto = new MeasurementDTO();
            measurementDto.setId(measurement.getId());
            measurementDto.setLongitude(measurement.getLongitude());
            measurementDto.setLatitude(measurement.getLatitude());
            measurementDto.setTemperature(measurement.getTemperature());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = simpleDateFormat.format(measurement.getTimestamp());
            measurementDto.setTimestamp(formattedDate);

            uniqueLatestMeasurementDTOs.add(measurementDto);
        }
        return uniqueLatestMeasurementDTOs;
    }
}
