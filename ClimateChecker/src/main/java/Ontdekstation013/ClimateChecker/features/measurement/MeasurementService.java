package Ontdekstation013.ClimateChecker.features.measurement;

import java.time.*;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.measurement.response.MeasurementOverview;
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

    public MeasurementOverview getMeasurementsAtTime(Instant dateTime) {
        // get measurements within a certain range of the dateTime
        int minuteMargin = meetJeStadService.getMinuteLimit();
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = dateTime.minus(Duration.ofMinutes(minuteMargin));
        params.EndDate = dateTime;
        List<Measurement> allMeasurements = meetJeStadService.getMeasurements(params);

        List<Measurement> closestMeasurements = MeasurementLogic.filterClosestMeasurements(allMeasurements, dateTime);

        return IncorrectValueFilter(closestMeasurements);
    }

    public List<DayMeasurementResponse> getHistoricalMeasurements(int id, Instant startDate, Instant endDate) {
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startDate;
        params.EndDate = endDate;
        params.StationIds.add(id);
        List<Measurement> measurements = meetJeStadService.getMeasurements(params);

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
    private MeasurementOverview IncorrectValueFilter(List<Measurement> measurements) {
        MeasurementOverview measurementOverview = new MeasurementOverview();
        int differenceFromAverageDivider = 2;
        int minimumDistanceAllowed = 3;
        float total = 0;
        measurementOverview.setMaxTemp(Integer.MIN_VALUE);
        measurementOverview.setMinTemp(Integer.MAX_VALUE);

        for(Measurement measurement:measurements) {
            if (measurement.getTemperature()!= null){
                total += measurement.getTemperature();
                if(measurement.getTemperature()>measurementOverview.getMaxTemp()){
                    measurementOverview.setMaxTemp(measurement.getTemperature());
                }
                if (measurement.getTemperature()<measurementOverview.getMinTemp()){
                    measurementOverview.setMinTemp(measurement.getTemperature());
                }
            }
        }
        float adjustmentValue = Math.abs(measurementOverview.getMinTemp());
        float adjustedTotal = total + measurements.size()*adjustmentValue;
        float adjustedAverage = adjustedTotal/measurements.size();
        float allowedSpread = adjustedAverage/differenceFromAverageDivider;
        if (allowedSpread < minimumDistanceAllowed){
            allowedSpread = minimumDistanceAllowed;
        }
        float average = total/measurements.size();

        for (Measurement measurement:measurements) {
            if (measurement.getHumidity()!= null && (measurement.getHumidity()<0 || measurement.getHumidity()>100)){
                measurement.setHumidity(null);
            }
            if (measurement.getTemperature() != null){
                float absoluteDifferenceFromAverage = Math.abs(measurement.getTemperature()-average);
                if (absoluteDifferenceFromAverage > allowedSpread){
                    measurement.setTemperature(null);
                }
            }
        }
        measurementOverview.setMeasurements(new ArrayList<>());
        measurementOverview.setMaxTemp(Integer.MIN_VALUE);
        measurementOverview.setMinTemp(Integer.MAX_VALUE);
        for (Measurement measurement:measurements) {
            if (measurement.getTemperature()!=null){
                if (measurement.getTemperature()<measurementOverview.getMinTemp()){
                    measurementOverview.setMinTemp(measurement.getTemperature());
                }
                if (measurement.getTemperature()>measurementOverview.getMaxTemp()){
                    measurementOverview.setMaxTemp(measurement.getTemperature());
                }
            }
            measurementOverview.measurements.add(convertToDTO(measurement));
        }

        return measurementOverview;
    }
}