package Ontdekstation013.ClimateChecker.features.databasepolling.endpoint;

import Ontdekstation013.ClimateChecker.features.databasepolling.DatabasePollingService;
import Ontdekstation013.ClimateChecker.features.databasepolling.MJSValidationService;
import Ontdekstation013.ClimateChecker.features.location.LocationService;
import Ontdekstation013.ClimateChecker.features.location.endpoint.createLocationDto;
import Ontdekstation013.ClimateChecker.features.sensor.SensorService;
import Ontdekstation013.ClimateChecker.features.station.StationServiceOld;
import Ontdekstation013.ClimateChecker.features.station.endpoint.MeetJeStadDto;
import Ontdekstation013.ClimateChecker.features.station.endpoint.createStationDto;
import Ontdekstation013.ClimateChecker.features.station.endpoint.stationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Data")
public class DatabasePollingController {

    private final SensorService sensorService;
    private final StationServiceOld stationService;
    private final LocationService locationService;
    private final DatabasePollingService databasePollingService;
    private final MJSValidationService MJSvalidationService;

    @Autowired
    public DatabasePollingController(SensorService sensorService, StationServiceOld stationService, LocationService locationService, DatabasePollingService databasePollingService, MJSValidationService MJSvalidationService) {
        this.sensorService = sensorService;
        this.stationService = stationService;
        this.locationService = locationService;
        this.databasePollingService = databasePollingService;
        this.MJSvalidationService = MJSvalidationService;
    }

    @GetMapping()
    public ResponseEntity<String> GetAllSensorData(){
        //Get the registration codes > Station Repos
        List<Long> registrationCodes = stationService.getAllRegistrationCode();

        if(registrationCodes.size() > 0){
            //Use registration codes to build a query for MeetJeStad
            List<MeetJeStadDto> meetJeStadDtos =  databasePollingService.GetAllRecentStations(registrationCodes);

            if(meetJeStadDtos.size() > 0){
                //For all the StationIDs/Registration codes, parse the sensor data
                //Save the sensor data to the sensor repos.
                sensorService.OldSensorDataToInactive();
                for(MeetJeStadDto meetJeStadDto : meetJeStadDtos){

                    sensorService.addSensorData(meetJeStadDto);
                }
                return new ResponseEntity<>("Sensor data has been added to the database", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>("Unable to add sensor data to the database", HttpStatus.NOT_ACCEPTABLE);


    }


    @PostMapping("/createStation")
    public ResponseEntity<String> createStationByRegistrationCode(@RequestParam("databaseTag") String databaseTag, @RequestParam("registrationCode") long registrationCode){

        stationDto station = stationService.findStationByRegistrationCode(registrationCode, databaseTag);

        if (station == null){
            MeetJeStadDto MJSDto = databasePollingService.GetStationToRegister(registrationCode);
            MJSDto = MJSvalidationService.ValidateRegisterDTO(MJSDto);

            createLocationDto createLocationDto = new createLocationDto();
            createLocationDto.setLongitude(MJSDto.longitude);
            createLocationDto.setLatitude(MJSDto.latitude);
            long locationId = locationService.createLocation(createLocationDto);

            createStationDto createStationDto = new createStationDto();
            createStationDto.setLocationId(locationId);
            createStationDto.setRegistrationCode(registrationCode);
            createStationDto.setDatabaseTag(databaseTag);
            stationService.createStation(createStationDto);

            return new ResponseEntity<>("Station has been added to the database", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Unable to add station to the database", HttpStatus.NOT_ACCEPTABLE);
    }
}
