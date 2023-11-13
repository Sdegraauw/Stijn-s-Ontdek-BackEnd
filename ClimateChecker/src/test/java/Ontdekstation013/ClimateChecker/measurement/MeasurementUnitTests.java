package Ontdekstation013.ClimateChecker.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementService;

@ExtendWith(MockitoExtension.class)
public class MeasurementUnitTests {
    @InjectMocks
    private MeasurementService measurementService;

    @Mock
    private MeetJeStadService meetJeStadService;
    private List<MeasurementDTO> measurementList;

    private Logger LOG = LogManager.getLogger(MeasurementUnitTests.class);

    @BeforeEach
    public void init() {
        measurementList = new ArrayList<>();

        measurementList.add(new MeasurementDTO(1, "07-10-2023 15:00", 51.578000f, 4.992980f, 22.0f, 50.0f));
        measurementList.add(new MeasurementDTO(2, "07-10-2023 14:00", 51.578000f, 4.992980f, 21.0f, 50.0f));
        measurementList.add(new MeasurementDTO(3, "07-10-2023 16:00", 51.578000f, 4.992980f, 19.0f, 50.0f));
        measurementList.add(new MeasurementDTO(3, "07-10-2023 15:00", 51.578000f, 4.992980f, 19.0f, 50.0f));
        measurementList.add(new MeasurementDTO(4, "07-10-2023 16:00", 51.578000f, 4.992980f, 19.5f, 50.0f));
        measurementList.add(new MeasurementDTO(5, "07-10-2023 16:00", 51.578000f, 4.992980f, 19.0f, 50.0f));
        measurementList.add(new MeasurementDTO(5, "07-10-2023 17:00", 51.578000f, 4.992980f, 19.5f, 50.0f));
    }

    @Test
    public void getLatestMeasurements() {
        // Arrange
        //when(measurementService.getLatestMeasurements()).thenReturn(measurementList);

        // Act
        //List<MeasurementDTO> dtos = measurementService.getLatestMeasurements();

        // Assert
        //assertEquals(dtos.size(), 5);
    }
}
