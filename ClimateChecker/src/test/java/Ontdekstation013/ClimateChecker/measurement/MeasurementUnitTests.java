package Ontdekstation013.ClimateChecker.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementController;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementService;

@ExtendWith(MockitoExtension.class)
public class MeasurementUnitTests {
    
    @InjectMocks
    private MeasurementController measurementController;

    @Mock
    private MeasurementService measurementService;

    private Logger LOG = LogManager.getLogger(MeasurementUnitTests.class);

    @Test
    public void testGetLatestMeasurementsUnitTest() {

        MeasurementDTO measurementDTO = MeasurementDTO.builder()
        .id(1)
        .timestamp("2020-01-01 00:00:00")
        .longitude(35)
        .latitude(70)
        .temperature(20)
        .build();

        List<MeasurementDTO> measurementDTOList = new ArrayList<>();
        measurementDTOList.add(measurementDTO);

        when(measurementService.getLatestMeasurements()).thenReturn(measurementDTOList);

        ResponseEntity<List<MeasurementDTO>> response = measurementController.getLatestMeasurements();
        
        LOG.info(response.getBody().get(0).toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(measurementDTOList, response.getBody());
        assertEquals(measurementDTOList.get(0).getId(), response.getBody().get(0).getId());
        assertEquals(measurementDTOList.get(0).getTimestamp(), response.getBody().get(0).getTimestamp());
        assertEquals(measurementDTOList.get(0).getLongitude(), response.getBody().get(0).getLongitude());
        assertEquals(measurementDTOList.get(0).getLatitude(), response.getBody().get(0).getLatitude());
        assertEquals(measurementDTOList.get(0).getTemperature(), response.getBody().get(0).getTemperature());

        
    }

}
