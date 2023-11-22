package Ontdekstation013.ClimateChecker.neighbourhood;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.features.neighbourhood.*;
import Ontdekstation013.ClimateChecker.features.neighbourhood.endpoint.NeighbourhoodDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NeighbourhoodUnitTest {
    @InjectMocks
    private NeighbourhoodService neighbourhoodService;

    @Mock
    private MeetJeStadService meetJeStadService;
    private List<Measurement> measurementList;

    @Mock
    private NeighbourhoodRepository neighbourhoodRepository;
    private List<Neighbourhood> neighbourhoodList;

    @Mock
    private NeighbourhoodCoordsRepository neighbourhoodCoordsRepository;
    private List<NeighbourhoodCoords> neighbourhoodCoordsList;

    private Logger LOG = LogManager.getLogger(NeighbourhoodUnitTest.class);

    @BeforeEach
    public void init() {
        neighbourhoodList = new ArrayList<>();
        measurementList = new ArrayList<>();
        neighbourhoodCoordsList = new ArrayList<>();


        neighbourhoodList.add(new Neighbourhood(1, "Lars"));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(1, 4.987390f, 51.581110f, 1));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(2, 4.985801f, 51.583950f, 1));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(3, 4.996515f, 51.582497f, 1));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(4, 4.993423f, 51.573829f, 1));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(5, 4.986881f, 51.574809f, 1));
        measurementList.add(new Measurement(1, Instant.now(), 51.578000f, 4.992980f, 22.0f, 50.0f));
        measurementList.add(new Measurement(2, Instant.now(), 51.578000f, 40.992980f, 23.0f, 50.0f));

        neighbourhoodList.add(new Neighbourhood(2, "Niels"));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(6, 4.967818f, 51.603059f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(7, 4.968806f, 51.601819f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(8, 4.967582f, 51.598061f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(9, 4.975675f, 51.597208f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(10, 4.977636f, 51.596235f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(11, 4.977936f, 51.594022f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(12, 4.948795f, 51.611448f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(13, 4.949632f, 51.612307f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(14, 5.005992f, 51.591109f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(15, 5.005409f, 51.588150f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(16, 5.009339f, 51.585917f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(17, 5.007150f, 51.584410f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(18, 4.997504f, 51.585917f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(19, 4.996380f, 51.582597f, 2));
        neighbourhoodCoordsList.add(new NeighbourhoodCoords(20, 4.985841f, 51.583977f, 2));
        measurementList.add(new Measurement(3, Instant.now(), 51.5869f, 4.999210f, 23.0f, 50.0f));
        measurementList.add(new Measurement(4, Instant.now(), 51.5869f, 4.999210f, 22.0f, 50.0f));
    }

    @Test
    public void getNeighbourhoodData() {
        // Arrange
        when(neighbourhoodRepository.findAll()).thenReturn(neighbourhoodList);
        when(neighbourhoodCoordsRepository.findAll()).thenReturn(neighbourhoodCoordsList);
        when(meetJeStadService.getLatestMeasurements()).thenReturn(measurementList);

        // Act
        List<NeighbourhoodDTO> dtos = neighbourhoodService.getLatestNeighbourhoodData();

        // Assert
        assertEquals(dtos.size(), 2);

        {
            NeighbourhoodDTO dto = dtos.stream().filter(obj -> obj.getId() == 1).toList().get(0);
            assertEquals(dto.getAvgTemp(), 22.0f);
        }

        {
            NeighbourhoodDTO dto = dtos.stream().filter(obj -> obj.getId() == 2).toList().get(0);
            assertEquals(dto.getAvgTemp(), 22.5f);
        }
    }
}
