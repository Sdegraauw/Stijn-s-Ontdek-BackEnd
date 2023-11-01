package Ontdekstation013.ClimateChecker.features.neighbourhood;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.features.neighbourhood.endpoint.NeighbourhoodDTO;
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
                if (pointInPolygon(dto.getCoordinates(), point)) {
                    tempMeasurements.add(measurement);
                }
            }
            // Calculate average temperature of the measurements in this neighbourhood
            float totalTemp = 0.0f;
            for (Measurement measurement : tempMeasurements)
                totalTemp += measurement.getTemperature();
            dto.setAvgTemp(totalTemp / tempMeasurements.size());

            neighbourhoodDTOS.add(dto);
        }
        return neighbourhoodDTOS;
    }

    // Algorithm for finding a gps point inside an area
    private boolean pointInPolygon(float[][] polygon, float[] point) {
        boolean odd = false;
        for (int i = 0, j = polygon.length - 1; i < polygon.length; i++) {
            if (((polygon[i][0] > point[0]) != (polygon[j][0] > point[0]))
                    && (point[1] < ((polygon[j][1] - polygon[i][1])
                    * (point[0] - polygon[i][0]) / (polygon[j][0] - polygon[i][0]) + polygon[i][1]))) {
                odd = !odd;
            }
            j = i;
        }
        return odd;
    }

    // Converting a list of coordinates to a two-dimensional float array
    private float[][] convertToFloatArray(List<Coordinate> coordinates) {
        return coordinates.stream()
                .map(coord -> new float[]{coord.getLatitude(), coord.getLongitude()})
                .toArray(float[][]::new);
    }
}