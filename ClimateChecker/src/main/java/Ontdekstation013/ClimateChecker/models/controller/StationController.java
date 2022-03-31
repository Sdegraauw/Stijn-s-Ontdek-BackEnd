package Ontdekstation013.ClimateChecker.models.controller;

import Ontdekstation013.ClimateChecker.models.Station;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.repositories.StationRepository;
import Ontdekstation013.ClimateChecker.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/Station")
public class StationController {

    private final StationService stationService;
    @Autowired
    public StationController(StationService stationService){
        this.stationService = stationService;
    }
    // get station based on ID
    @GetMapping("{stationId}")
    public ResponseEntity<stationDto> getStationById(@PathVariable Long stationId) {
        stationDto dto = stationService.findStationById(stationId);
        return ResponseEntity.ok(dto);
    }

    // get stations by user ID
    @GetMapping("user/{userId}")
    public ResponseEntity<List<stationTitleDto>> getStationsByUserId(@PathVariable Long userId) {

        List<stationTitleDto> newDtoList = stationService.getAllByUserId(userId);
        return ResponseEntity.ok(newDtoList);
    }

    // get all by page number
    @GetMapping("page/{pageNumber}")
    public ResponseEntity<List<stationDto>> getAllStations(Long pageId){

        List<stationDto> newDtoList = stationService.getAllByPageId(pageId);
        return ResponseEntity.ok(newDtoList);
    }

    // get all stations
    @GetMapping
    public ResponseEntity<List<stationDto>> getAllStations() {

        List<stationDto> newDtoList = stationService.getAll();
        return ResponseEntity.ok(newDtoList);
    }

    // create new station


    // delete station


    // edit station

}
