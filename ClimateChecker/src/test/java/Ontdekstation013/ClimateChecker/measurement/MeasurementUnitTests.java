package Ontdekstation013.ClimateChecker.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private List<Measurement> measurementList;

    @BeforeEach
    public void init() {
        measurementList = new ArrayList<>();
        measurementList.add(new Measurement(1, Instant.parse("2000-01-01T12:00:00.00Z"), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(1, Instant.parse("2000-01-01T12:12:00.00Z"), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(1, Instant.parse("2000-01-01T12:24:00.00Z"), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(1, Instant.parse("2000-01-01T12:48:00.00Z"), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(2, Instant.parse("2000-01-01T12:16:00.00Z"), 51.55f, 5f, 20.0f, 50.0f));
        measurementList.add(new Measurement(2, Instant.parse("2000-01-01T12:20:00.00Z"), 51.55f, 5f, 20.0f, 50.0f));
    }

    @Test
    public void getMeasurementsAtTime_ClosestToSpecifiedTime() {
        // Arrange
        int minuteMargin = 36;
        when(meetJeStadService.getMinuteLimit()).thenReturn(minuteMargin);
        when(meetJeStadService.getMeasurements(any(MeetJeStadParameters.class))).thenReturn(measurementList);

        Instant datetime = Instant.parse("2000-01-01T12:16:00.00Z");

        // Act
        List<MeasurementDTO> dtos = measurementService.getMeasurementsAtTime(datetime);

        // Assert
        assertEquals(dtos.size(), 2);

        //IMPORTANT NOTE:
        // for some reason the constructor of the date class adds 1900 years,
        // the test results correct for this (2000 + 1900 = 3900)
        // in the service logic the constructor is not used, so it's not a problem there.

        // I think this formatter takes summertime offset into account,
        // meaning the unit tests will only work in wintertime if you expect dd-MM-yyyy HH:mm:ss format,
        // therefore the formatter must be used in the unit tests as well.
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.of("Europe/Amsterdam"));
        {
            MeasurementDTO dto = dtos.stream().filter(obj -> obj.getId() == 1).toList().get(0);
            assertEquals(formatter.format(Instant.parse("2000-01-01T12:12:00.00Z")) , dto.getTimestamp());
        }
        {
            MeasurementDTO dto = dtos.stream().filter(obj -> obj.getId() == 2).toList().get(0);
            assertEquals(formatter.format(Instant.parse("2000-01-01T12:16:00.00Z")), dto.getTimestamp());
        }
    }
}
