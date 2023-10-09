package Ontdekstation013.ClimateChecker.features.measurement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/measurement")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;
    private Logger LOG = LoggerFactory.getLogger(MeasurementController.class);

    @GetMapping("/current")
    public ResponseEntity<List<MeasurementDTO>> getLatestMeasurements(@RequestParam(required = false, defaultValue = "35", value = "minuteLimit") int minuteLimit) {
        try {
            List<MeasurementDTO> measurements = measurementService.getLatestMeasurements(minuteLimit);
            return ResponseEntity.ok(measurements);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
