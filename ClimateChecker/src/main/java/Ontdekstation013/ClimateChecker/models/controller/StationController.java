package Ontdekstation013.ClimateChecker.models.controller;

import Ontdekstation013.ClimateChecker.models.Station;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.models.exception.ResourceNotFoundException;
import Ontdekstation013.ClimateChecker.repositories.StationRepository;
import Ontdekstation013.ClimateChecker.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/Station")
public class StationController {

    @Autowired
    private final StationService stationService;

    // get station based on ID
    @GetMapping("{stationId}")
    public ResponseEntity<stationDto> getStationById(@PathVariable long id) {
        stationDto dto = stationService.findStationById(id);
        return ResponseEntity.ok(dto);
    }
}
