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

import static Ontdekstation013.ClimateChecker.utility.MeasurementLogic.IncorrectValueFilter;

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

        return IncorrectValueFilterO(closestMeasurements);
    }

    public List<DayMeasurementResponse> getHistoricalMeasurements(int id, Instant startDate, Instant endDate) {
        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startDate;
        params.EndDate = endDate;
        params.locationCorrection = false;
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
    private MeasurementOverview IncorrectValueFilterO(List<Measurement> measurements) {
        IncorrectValueFilter(measurements);

        MeasurementOverview measurementOverview = new MeasurementOverview();
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