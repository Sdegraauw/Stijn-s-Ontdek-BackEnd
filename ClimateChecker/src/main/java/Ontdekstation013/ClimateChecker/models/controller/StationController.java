package Ontdekstation013.ClimateChecker.models.controller;

import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/Station")
public class StationController {

    private final StationService stationService;
    @Autowired
    public StationController(StationService stationService){
        this.stationService = stationService;
    }

    // get station based on id
    @GetMapping("{stationId}")
    public ResponseEntity<stationDto> getStationById(@PathVariable Long stationId) {
        stationDto dto = stationService.findStationById(stationId);
        return ResponseEntity.ok(dto);
    }

    // get stations by user id
    @GetMapping("user/{userId}")
    public ResponseEntity<List<stationTitleDto>> getStationsByUserId(@PathVariable Long userId) {

        List<stationTitleDto> newDtoList = stationService.getAllByUserId(userId);
        return ResponseEntity.ok(newDtoList);
    }

    // get all by page number
    @GetMapping("page/{pageNumber}")
    public ResponseEntity<List<stationTitleDto>> getAllStationsByPage(@PathVariable Long pageId){

        List<stationTitleDto> newDtoList = stationService.getAllByPageId(pageId);
        return ResponseEntity.ok(newDtoList);
    }

    // get all stations
    @GetMapping
    public ResponseEntity<List<stationTitleDto>> getAllStations() {

        List<stationTitleDto> newDtoList = stationService.getAll();
        return ResponseEntity.ok(newDtoList);
    }

    // create new station
    @PostMapping (consumes = {"application/xml","application/json"})
    public ResponseEntity<registerStationDto> createStation(@RequestBody registerStationDto registerstationDto){

        stationService.createStation(registerstationDto);
        //return ResponseEntity.status(HttpStatus.CREATED).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerstationDto);
    }

    // delete station
    @DeleteMapping("{stationId}")
    public ResponseEntity<stationDto> deleteStation(@PathVariable Long stationId){

        stationService.deleteStation(stationId);
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
    }

    // edit station
    @PutMapping
    public ResponseEntity<stationDto> editStation(@RequestBody editStationDto stationDto){

        stationService.editStation(stationDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
