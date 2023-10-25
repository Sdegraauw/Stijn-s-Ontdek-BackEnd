package Ontdekstation013.ClimateChecker.features.neighbourhood;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.features.neighbourhood.endpoint.NeighbourhoodDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NeighbourhoodService {
    private final MeetJeStadService meetJeStadService;
    private final NeighbourhoodRepository neighbourhoodRepository;
    private final NeighbourhoodCoordsRepository neighbourhoodCoordsRepository;

    public List<NeighbourhoodDTO> getNeighbourhoodData() {
        List<Neighbourhood> neighbourhoods = neighbourhoodRepository.findAll();
        List<NeighbourhoodCoords> neighbourhoodCoords = neighbourhoodCoordsRepository.findAll();
        List<NeighbourhoodDTO> neighbourhoodDTOS = new ArrayList<>();

        List<Measurement> measurements = meetJeStadService.getLatestMeasurements();

        for (Neighbourhood neighbourhood : neighbourhoods) {
            if (neighbourhood.getId() != 41)
                continue;

            NeighbourhoodDTO dto = new NeighbourhoodDTO();
            dto.setId(neighbourhood.getId());
            dto.setName(neighbourhood.getName());

            List<Coordinate> tempCoords = new ArrayList<>();
            for (NeighbourhoodCoords coords : neighbourhoodCoords) {
                if (neighbourhood.getId() == coords.getRegionId())
                    tempCoords.add(new Coordinate(coords.getLatitude(), coords.getLongitude()));
            }
            float[][] coordsInNeighbourhoud = new float[tempCoords.size()][2];
            for (int i = 0; i < tempCoords.size(); i++) {
                coordsInNeighbourhoud[i][1] = tempCoords.get(i).getLongitude();
                coordsInNeighbourhoud[i][0] = tempCoords.get(i).getLatitude();
            }
            dto.setCoordinates(coordsInNeighbourhoud);

            List<Measurement> tempMeasurements = new ArrayList<>();
            for (Measurement measurement : measurements) {
                float[] point = {measurement.getLongitude(), measurement.getLatitude()};
                if (pointInPolygon(dto.getCoordinates(), point)) {
                    tempMeasurements.add(measurement);
                }
            }

            float totalTemp = 0.0f;
            for (Measurement measurement : tempMeasurements)
                totalTemp += measurement.getTemperature();

            dto.setAvgTemp(totalTemp / tempMeasurements.size());
            neighbourhoodDTOS.add(dto);
        }

        return neighbourhoodDTOS;

//        for (Neighbourhood neighbourhood : neighbourhoods) {
//            NeighbourhoodDTO dto = new NeighbourhoodDTO();
//            dto.setId(neighbourhood.getId());
//            dto.setName(neighbourhood.getName());
//
//            List<Coordinate> tempCoords = new ArrayList<>();
//            for (NeighbourhoodCoords coords : neighbourhoodCoords) {
//                if (neighbourhood.getId() == coords.getRegionId())
//                    tempCoords.add(new Coordinate(coords.getLatitude(), coords.getLongitude()));
//            }
//            dto.setCoordinates(tempCoords);
//
//            List<Measurement> tempMeasurements = new ArrayList<>();
//            for (Measurement measurement : measurements) {
//                List<Coordinate> coords = dto.getCoordinates();
//
//
////                boolean odd = false;
////                for (int i = 0, j = coords.size() - 1; i < coords.size(); i++) {
////                    Coordinate coordinateI = coords.get(i);
////                    Coordinate coordinateJ = coords.get(j);
////
////                    if (((coordinateI.getLatitude() > measurement.getLatitude()) != (coordinateI.getLatitude() > measurement.getLatitude()))
////                            && (measurement.getLongitude() < ((coordinateJ.getLongitude() - coordinateI.getLongitude())
////                            * (measurement.getLatitude() - coordinateI.getLatitude())
////                            / (coordinateJ.getLatitude() - coordinateI.getLatitude()) + coordinateI.getLongitude()))) {
////                        odd = !odd;
////                    }
////                    j = i;
////                }
//
//
//                if (odd) {
//                    tempMeasurements.add(measurement);
//                }
//            }
//
//            float totalTemp = 0.0f;
//            for (Measurement measurement : tempMeasurements)
//                totalTemp += measurement.getTemperature();
//
//            dto.setAvgTemp(totalTemp / tempMeasurements.size());
//
//            neighbourhoodDTOS.add(dto);
//        }
    }

    private boolean pointInPolygon(float[][] polygon, float[] point) {
        boolean odd = false;
        for (int i = 0, j = polygon.length - 1; i < polygon.length; i++) {
            if (((polygon[i][1] > point[1]) != (polygon[j][1] > point[1]))
                    && (point[0] < ((polygon[j][0] - polygon[i][0])
                    * (point[1] - polygon[i][1]) / (polygon[j][1] - polygon[i][1]) + polygon[i][0]))) {
                odd = !odd;
            }
            j = i;
        }
        return odd;
    }
}