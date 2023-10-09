package Ontdekstation013.ClimateChecker.features.station.endpoint;

import Ontdekstation013.ClimateChecker.features.sensor.SensorService;
import Ontdekstation013.ClimateChecker.features.station.Measurement;
import Ontdekstation013.ClimateChecker.features.station.StationService;
import Ontdekstation013.ClimateChecker.features.station.StationServiceOld;
import Ontdekstation013.ClimateChecker.features.station.ValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StationController1 {
    @GetMapping("/test")
    public List<Measurement> test() throws JsonProcessingException {
        return new StationService().GetLastStationsData();
    }
}

