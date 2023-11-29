package Ontdekstation013.ClimateChecker.features.neighbourhood.endpoint;

import Ontdekstation013.ClimateChecker.features.measurement.endpoint.responses.DayMeasurementResponse;
import Ontdekstation013.ClimateChecker.features.neighbourhood.NeighbourhoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/neighbourhood")
@RequiredArgsConstructor
public class NeighbourhoodController {
    private final NeighbourhoodService neighbourhoodService;

    @GetMapping("/all")
    public List<NeighbourhoodDTO> getLatestNeighbourhoodData() {
        return neighbourhoodService.getLatestNeighbourhoodData();
    }

    @GetMapping("/history/average/{id}")
    public List<DayMeasurementResponse> getNeighbourhoodData(@PathVariable Long id, @RequestParam String startDate, @RequestParam String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime localDateTimeStart = LocalDateTime.parse(startDate, formatter);
        Instant startInstant = localDateTimeStart.atZone(ZoneId.systemDefault()).toInstant();

        LocalDateTime localDateTimeEnd = LocalDateTime.parse(endDate, formatter);
        Instant endInstant = localDateTimeEnd.atZone(ZoneId.systemDefault()).toInstant();

        return neighbourhoodService.getNeighbourhoodDataAverage(id, startInstant, endInstant);
    }
}
