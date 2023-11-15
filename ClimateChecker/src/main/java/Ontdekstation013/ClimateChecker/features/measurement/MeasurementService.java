package Ontdekstation013.ClimateChecker.features.measurement;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.responses.MeasurementHistoricalDataResponse;
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

    public List<MeasurementDTO> getMeasurementsAtTime(Instant dateTime) {
        // get measurements within a certain range of the dateTime
        int minuteMargin = meetJeStadService.getMinuteLimit();
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = dateTime.minus(Duration.ofMinutes(minuteMargin));
        params.EndDate = dateTime.plus(Duration.ofMinutes(minuteMargin));

        // convert dateTime to date format for use in logic
        // this is needed because Measurement uses Date instead of Instant for it's timestamp
        // todo: adjust after changing Measurement timestamp from Date to Instant
        Date dateTimeDate = Date.from(dateTime);

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
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startDate;
        params.EndDate = endDate;
        params.StationIds.add(id);

        List<Measurement> measurements = meetJeStadService.getMeasurements(params);

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
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startDate;
        params.EndDate = endDate;
        params.StationIds.add(id);

        List<Measurement> measurements = meetJeStadService.getMeasurements(params);

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
