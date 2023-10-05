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

    @GetMapping("/all")
    public ResponseEntity<List<Measurement>> getFilterMeasurements(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        try {
            List<Measurement> measurements = measurementService.getFilterMeasurements(startDate, endDate);
            return ResponseEntity.ok(measurements);

        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
