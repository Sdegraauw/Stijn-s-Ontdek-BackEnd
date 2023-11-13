package Ontdekstation013.ClimateChecker.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Ontdekstation013.ClimateChecker.features.measurement.MeasurementService;

@ExtendWith(MockitoExtension.class)
public class MeasurementUnitTests {
    @InjectMocks
    private MeasurementService measurementService;

    @Mock
    private MeetJeStadService meetJeStadService;
    private List<MeasurementDTO> measurementList;

    @BeforeEach
    public void init() {
        measurementList = new ArrayList<>();

    @BeforeEach
    public void init() {
        measurementList = new ArrayList<>();

        measurementList.add(new Measurement(1, new Date(2000, 0, 1, 12, 0, 0), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(1, new Date(2000, 0, 1, 12, 12, 0), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(1, new Date(2000, 0, 1, 12, 24, 0), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(1, new Date(2000, 0, 1, 12, 48, 0), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(2, new Date(2000, 0, 1, 12, 16, 0), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(2, new Date(2000, 0, 1, 12, 20, 0), 51.55f, 5f, 20.0f, 50.0f));
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

    @Test
    public void getMeasurementsAtTime_ClosestToSpecifiedTime() {
        // Arrange
        int minuteMargin = 36;
        when(meetJeStadService.getMinuteLimit()).thenReturn(minuteMargin);

        Date datetime = new Date(2000, 0, 1, 12, 16, 0);
        Instant dateTimeInstant = datetime.toInstant();
        Instant startInstant = dateTimeInstant.minus(Duration.ofMinutes(minuteMargin / 2));
        Instant endInstant = dateTimeInstant.plus(Duration.ofMinutes(minuteMargin / 2));

        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startInstant;
        params.EndDate = endInstant;

        when(meetJeStadService.getMeasurements(any(MeetJeStadParameters.class))).thenReturn(measurementList);

        // Act
        List<MeasurementDTO> dtos = measurementService.getMeasurementsAtTime(dateTimeInstant);

        // Assert
        assertEquals(dtos.size(), 2);

        //IMPORTANT NOTE:
        // for some reason the constructor of the date class adds 1900 years,
        // the test results correct for this (2000 + 1900 = 3900)
        // in the service logic the constructor is not used, so it's not a problem there.
        {
            MeasurementDTO dto = dtos.stream().filter(obj -> obj.getId() == 1).toList().get(0);
            assertEquals("01-01-3900 12:12:00", dto.getTimestamp());
        }
        {
            MeasurementDTO dto = dtos.stream().filter(obj -> obj.getId() == 2).toList().get(0);
            assertEquals("01-01-3900 12:16:00", dto.getTimestamp());
        }
    }
}
