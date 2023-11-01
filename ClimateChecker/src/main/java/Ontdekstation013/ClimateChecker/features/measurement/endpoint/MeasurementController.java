package Ontdekstation013.ClimateChecker.features.measurement.endpoint;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import Ontdekstation013.ClimateChecker.features.measurement.MeasurementService;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.responses.MeasurementHistoricalDataResponse;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/latest/{id}")
    public MeasurementDTO getLatestMeasurement(@PathVariable int id) {
        return measurementService.getLatestMeasurement(id);
    }

    @GetMapping("/history/all/{id}")
    public List<MeasurementDTO> getMeasurements(@PathVariable int id, @RequestParam String startDate, @RequestParam String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime localDateTimeStart = LocalDateTime.parse(startDate, formatter);
        Instant startInstant = localDateTimeStart.atZone(ZoneId.systemDefault()).toInstant();

        LocalDateTime localDateTimeEnd = LocalDateTime.parse(endDate, formatter);
        Instant endInstant = localDateTimeEnd.atZone(ZoneId.systemDefault()).toInstant();

        return measurementService.getMeasurements(id, startInstant, endInstant);
    }

    @GetMapping("/history/average/{id}")
    public List<MeasurementHistoricalDataResponse> getMeasurementsAverage(@PathVariable int id, @RequestParam String startDate, @RequestParam String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime localDateTimeStart = LocalDateTime.parse(startDate, formatter);
        Instant startInstant = localDateTimeStart.atZone(ZoneId.systemDefault()).toInstant();

        LocalDateTime localDateTimeEnd = LocalDateTime.parse(endDate, formatter);
        Instant endInstant = localDateTimeEnd.atZone(ZoneId.systemDefault()).toInstant();

        return measurementService.getMeasurementsAverage(id, startInstant, endInstant);
    }
}
