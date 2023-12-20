package Ontdekstation013.ClimateChecker.features.neighbourhood;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementRepository;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementResult;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementType;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.responses.DayMeasurementResponse;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.features.neighbourhood.endpoint.NeighbourhoodDTO;
import Ontdekstation013.ClimateChecker.features.station.Station;
import Ontdekstation013.ClimateChecker.utility.GpsTriangulation;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class NeighbourhoodService {
    private final MeetJeStadService meetJeStadService;
    private final MeasurementRepository measurementRepository;
    private final NeighbourhoodRepository neighbourhoodRepository;
    private final NeighbourhoodCoordsRepository neighbourhoodCoordsRepository;
    private final Logger LOG = LoggerFactory.getLogger(NeighbourhoodService.class);

    // Latitude = Y
    // Longitude = X
    // Process measurements into neighbourhoods
    private List<NeighbourhoodDTO> getNeighbourhoodsAverageTemp(List<Neighbourhood> neighbourhoods, List<Measurement> measurements){
        List<NeighbourhoodDTO> neighbourhoodDTOS = new ArrayList<>();

        for (Neighbourhood neighbourhood : neighbourhoods) {
            NeighbourhoodDTO dto = new NeighbourhoodDTO();

            dto.setId(neighbourhood.getId());
            dto.setName(neighbourhood.getName());
            dto.setCoordinates(convertToFloatArray(neighbourhood.coordinates));

            // Get all measurements within this neighbourhood
            List<Measurement> tempMeasurements = new ArrayList<>();
            for (Measurement measurement : measurements) {
                float[] point = { measurement.getLocation().getLatitude(), measurement.getLocation().getLongitude() };
                if (GpsTriangulation.pointInPolygon(dto.getCoordinates(), point)) {
                    tempMeasurements.add(measurement);
                }
            }

            // Calculate average temperature of the measurements in this neighbourhood

            float totalTemp = 0.0f;
            for (Measurement measurement : tempMeasurements) {
                for (MeasurementResult measurementResult : measurement.getMeasurementResults()) {
                    if (measurementResult.getMeasurementType() == MeasurementType.TEMPERATURE)
                        totalTemp += measurementResult.getValue();
                }
            }

            dto.setAvgTemp(totalTemp / tempMeasurements.size());

            neighbourhoodDTOS.add(dto);
        }

        return neighbourhoodDTOS;
    }

    // Gets latest neighbourhood data with average temperature
    public List<NeighbourhoodDTO> getNeighbourhoodsLatest() {
        List<Neighbourhood> neighbourhoods = neighbourhoodRepository.findAll();

        int minuteMargin = meetJeStadService.getMinuteLimit();

        Instant startDate = Instant.now().minus(Duration.ofMinutes(minuteMargin));
        Instant endDate = Instant.now().plus(Duration.ofMinutes(minuteMargin));

        List<Measurement> measurements = measurementRepository.findDistinctByMeasurementTimeBeforeAndMeasurementTimeAfterOrderByMeasurementTimeDesc(endDate, startDate);

        return getNeighbourhoodsAverageTemp(neighbourhoods, measurements);
    }

    // Gets neighbourhood data at given time with average temperature
    public List<NeighbourhoodDTO> getNeighbourhoodsAtTime(Instant dateTime){
        List<Neighbourhood> neighbourhoods = neighbourhoodRepository.findAll();

        int minuteMargin = meetJeStadService.getMinuteLimit();

        Instant startDate = dateTime.minus(Duration.ofMinutes(minuteMargin));
        Instant endDate = dateTime.plus(Duration.ofMinutes(minuteMargin));

        List<Measurement> allMeasurements = measurementRepository.findDistinctByMeasurementTimeBeforeAndMeasurementTimeAfterOrderByMeasurementTimeDesc(endDate, startDate);

        return getNeighbourhoodsAverageTemp(neighbourhoods, allMeasurements);
    }

    public List<DayMeasurementResponse> getNeighbourhoodData(Long id, Instant startDate, Instant endDate) {
        Optional<Neighbourhood> neighbourhoodOptional = neighbourhoodRepository.findById(id);
        Neighbourhood neighbourhood;
        if (neighbourhoodOptional.isPresent())
              neighbourhood = neighbourhoodOptional.get();
        else
            return new ArrayList<>();

        // Get all measurements from 1 day to filter out stations that are irrelevant
        List<Measurement> measurements = measurementRepository.findAllByMeasurementTimeBeforeAndMeasurementTimeAfterOrderByMeasurementTimeDesc(endDate, endDate.minusSeconds(60 * 60));

        // Get all station id's within this neighbourhood
        float[][] neighbourhoodCoords = convertToFloatArray(neighbourhood.coordinates);
        List<Station> stations = new ArrayList<>();
        for (Measurement measurement : measurements) {
            float[] point = { measurement.getLocation().getLatitude(), measurement.getLocation().getLongitude() };

            if (GpsTriangulation.pointInPolygon(neighbourhoodCoords, point) && !stations.contains(measurement.getStation())) {
                stations.add(measurement.getStation());
            }
        }

        // Get all measurements within timeframe from these stations
        measurements = measurementRepository.findAllByStationInAndMeasurementResults_MeasurementTypeAndMeasurementTimeBeforeAndMeasurementTimeAfter(stations, MeasurementType.TEMPERATURE, endDate, startDate);

        // Get the daily average
        HashMap<LocalDate, List<Float>> dayMeasurements = new LinkedHashMap<>();
        for (Measurement measurement : measurements) {
            if (measurement.getMeasurementResults().isEmpty())
                continue;

            float temperature = Float.MIN_VALUE;
            for (MeasurementResult result : measurement.getMeasurementResults())
                if (result.getMeasurementType() == MeasurementType.TEMPERATURE) {/////////
                    temperature = result.getValue();
                    break;
                }

            if (temperature == Float.MIN_VALUE)
                continue;

            LocalDate date = LocalDate.ofInstant(measurement.getMeasurementTime(), ZoneId.systemDefault());
            if (!dayMeasurements.containsKey(date)) {
                dayMeasurements.put(date, new ArrayList<>());
            }

            dayMeasurements.get(date).add(temperature);
        }

        List<DayMeasurementResponse> responseList = new ArrayList<>();

        // TODO: Fix this after data caching
        for (Map.Entry<LocalDate, List<Float>> entry : dayMeasurements.entrySet()) {
            LocalDate date = entry.getKey();
            float minTemp = entry.getValue()
                    .stream()
                    .min(Float::compare)
                    .orElse(Float.NaN);
            float maxTemp = entry.getValue()
                    .stream()
                    .max(Float::compare)
                    .orElse(Float.NaN);
            float avgTemp = (float) entry.getValue()
                    .stream()
                    .mapToDouble(Float::doubleValue)
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

    // Converting a list of coordinates to a two-dimensional float array
    private float[][] convertToFloatArray(List<NeighbourhoodCoords> coordinates) {
        return coordinates.stream()
                .map(coord -> new float[]{ coord.getLatitude(), coord.getLongitude() })
                .toArray(float[][]::new);
    }
}