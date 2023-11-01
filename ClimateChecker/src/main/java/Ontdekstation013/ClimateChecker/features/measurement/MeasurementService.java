package Ontdekstation013.ClimateChecker.features.measurement;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import java.util.List;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.responses.MeasurementHistoricalDataResponse;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeetJeStadService meetJeStadService;

    public List<MeasurementDTO> getLatestMeasurements() {
        List<Measurement> uniqueLatestMeasurements = meetJeStadService.getLatestMeasurements();

        // Convert to DTOs
        List<MeasurementDTO> uniqueLatestMeasurementDTOs = new ArrayList<>();
        for (Measurement measurement : uniqueLatestMeasurements) {
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

    public MeasurementDTO getLatestMeasurement(int id) {
        Measurement latestMeasurement = meetJeStadService.getLatestMeasurement(id);

        // Convert to DTO
        MeasurementDTO measurementDto = new MeasurementDTO();
        measurementDto.setId(latestMeasurement.getId());
        measurementDto.setLongitude(latestMeasurement.getLongitude());
        measurementDto.setLatitude(latestMeasurement.getLatitude());
        measurementDto.setTemperature(latestMeasurement.getTemperature());
        measurementDto.setHumidity(latestMeasurement.getHumidity());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = simpleDateFormat.format(latestMeasurement.getTimestamp());
        measurementDto.setTimestamp(formattedDate);

        return measurementDto;
    }

    public List<MeasurementDTO> getMeasurements(int id, Instant startDate, Instant endDate) {
        List<Measurement> measurements = meetJeStadService.getMeasurements(id, startDate, endDate);

        // Convert to DTOs
        List<MeasurementDTO> measurementDTOs = new ArrayList<>();
        for (Measurement measurement : measurements) {
            MeasurementDTO measurementDto = new MeasurementDTO();
            measurementDto.setId(measurement.getId());
            measurementDto.setLongitude(measurement.getLongitude());
            measurementDto.setLatitude(measurement.getLatitude());
            measurementDto.setTemperature(measurement.getTemperature());
            measurementDto.setHumidity(measurement.getHumidity());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = simpleDateFormat.format(measurement.getTimestamp());
            measurementDto.setTimestamp(formattedDate);

            measurementDTOs.add(measurementDto);
        }

        return measurementDTOs;
    }

    public List<MeasurementHistoricalDataResponse> getMeasurementsAverage(int id, Instant startDate, Instant endDate) {
        List<Measurement> measurements = meetJeStadService.getMeasurements(id, startDate, endDate);

        List<MeasurementHistoricalDataResponse> responseList = new ArrayList<>();
        float minTemp = 0, maxTemp = 0, temps = 0;
        int measurementCount = 0;
        Date currentDate = new Date(1970, Calendar.JANUARY, 1);

        for (Measurement measurement : measurements) {
            if (currentDate == new Date(1970, Calendar.JANUARY, 1))
                currentDate = measurement.getTimestamp();

            if (currentDate.getDay() == measurement.getTimestamp().getDay()) {
                temps += measurement.getTemperature();
                measurementCount++;

                if (minTemp > measurement.getTemperature() || minTemp == 0)
                    minTemp = measurement.getTemperature();
                if (maxTemp < measurement.getTemperature() || maxTemp == 0)
                    maxTemp = measurement.getTemperature();
            } else {
                if (measurementCount > 0) {
                    MeasurementHistoricalDataResponse response = new MeasurementHistoricalDataResponse();
                    response.setMaxTemp(maxTemp);
                    response.setMinTemp(minTemp);
                    response.setAvgTemp(temps / measurementCount);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM");
                    String formattedDate = simpleDateFormat.format(measurement.getTimestamp());
                    response.setTimestamp(formattedDate);

                    responseList.add(response);

                    measurementCount = 0;
                    minTemp = 0;
                    maxTemp = 0;
                    temps = 0;
                }

                currentDate = measurement.getTimestamp();
            }
        }

        return responseList;
    }
}
