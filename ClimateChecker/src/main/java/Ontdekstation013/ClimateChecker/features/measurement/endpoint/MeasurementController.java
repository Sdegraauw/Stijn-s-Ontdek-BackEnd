package Ontdekstation013.ClimateChecker.features.measurement.endpoint;

import java.time.Instant;
import java.time.format.*;
import java.util.List;

import Ontdekstation013.ClimateChecker.exception.InvalidArgumentException;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementService;
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

    @GetMapping("/latest")
    public ResponseEntity<List<MeasurementDTO>> getLatestMeasurements() {
        try {
            List<MeasurementDTO> measurements = measurementService.getLatestMeasurements();
            return ResponseEntity.ok(measurements);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/history")
    public List<MeasurementDTO> getAt(
            @RequestParam(value = "datetime") String utcDateTimeISO) {
        try {
            Instant utcDateTime = Instant.parse(utcDateTimeISO);
            List<MeasurementDTO> measurements = measurementService.getMeasurementsAt(utcDateTime);
            return measurements;
        } catch (DateTimeParseException e) {
            throw new InvalidArgumentException("DateTime must be in ISO 8601 format");
        }
    }
}
