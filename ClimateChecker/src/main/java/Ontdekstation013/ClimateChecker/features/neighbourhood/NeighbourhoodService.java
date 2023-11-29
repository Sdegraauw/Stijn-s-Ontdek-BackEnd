package Ontdekstation013.ClimateChecker.features.neighbourhood;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.features.neighbourhood.endpoint.NeighbourhoodDTO;
import Ontdekstation013.ClimateChecker.utility.GpsTriangulation;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class NeighbourhoodService {
    private final MeetJeStadService meetJeStadService;
    private final NeighbourhoodRepository neighbourhoodRepository;
    private final NeighbourhoodCoordsRepository neighbourhoodCoordsRepository;
    private final Logger LOG = LoggerFactory.getLogger(NeighbourhoodService.class);

    // Latitude = Y
    // Longitude = X
    // Gets neighbourhood data with average temperature
    public List<NeighbourhoodDTO> getNeighbourhoodData() {
        List<Neighbourhood> neighbourhoods = neighbourhoodRepository.findAll();
        List<NeighbourhoodCoords> neighbourhoodCoords = neighbourhoodCoordsRepository.findAll();
        List<NeighbourhoodDTO> neighbourhoodDTOS = new ArrayList<>();

        List<Measurement> measurements = meetJeStadService.getLatestMeasurements();

        for (Neighbourhood neighbourhood : neighbourhoods) {
            NeighbourhoodDTO dto = new NeighbourhoodDTO();

            dto.setId(neighbourhood.getId());
            dto.setName(neighbourhood.getName());

            // Get coordinates that belong to this neighbourhood
            List<Coordinate> tempCoords = new ArrayList<>();
            for (NeighbourhoodCoords coords : neighbourhoodCoords) {
                if (neighbourhood.getId() == coords.getRegionId())
                    tempCoords.add(new Coordinate(coords.getLatitude(), coords.getLongitude()));
            }
            // Skip neighbourhoods that have no coords
            if (tempCoords.isEmpty()) {
                LOG.warn("Neighbourhood " + neighbourhood.getId() + " " + neighbourhood.getName() + " has no coordinates and cannot be processed");
                continue;
            }
            dto.setCoordinates(convertToFloatArray(tempCoords));

            // Get all measurements within this neighbourhood
            List<Measurement> tempMeasurements = new ArrayList<>();
            for (Measurement measurement : measurements) {
                float[] point = {measurement.getLatitude(), measurement.getLongitude()};
                if (GpsTriangulation.pointInPolygon(dto.getCoordinates(), point)) {
                    tempMeasurements.add(measurement);
                }
            }
            // Calculate average temperature of the measurements in this neighbourhood
            float totalTemp = 0.0f;
            int measurementCount = tempMeasurements.size();
            for (Measurement measurement : tempMeasurements) {
                if (measurement.getTemperature() != null) {
                    totalTemp += measurement.getTemperature();
                }
                else {
                    measurementCount--;
                }
            }
            dto.setAvgTemp(totalTemp / measurementCount);

            neighbourhoodDTOS.add(dto);
        }
        return neighbourhoodDTOS;
    }

    // Converting a list of coordinates to a two-dimensional float array
    private float[][] convertToFloatArray(List<Coordinate> coordinates) {
        return coordinates.stream()
                .map(coord -> new float[]{coord.getLongitude(),coord.getLatitude()})
                .toArray(float[][]::new);
    }
}