package Ontdekstation013.ClimateChecker.features.station;

import Ontdekstation013.ClimateChecker.features.location.Location;
import Ontdekstation013.ClimateChecker.features.sensor.SensorService;
import Ontdekstation013.ClimateChecker.features.sensor.StationRepositoryCustom;
import Ontdekstation013.ClimateChecker.features.station.endpoint.createStationDto;
import Ontdekstation013.ClimateChecker.features.station.endpoint.editStationDto;
import Ontdekstation013.ClimateChecker.features.station.endpoint.registerStationDto;
import Ontdekstation013.ClimateChecker.features.station.endpoint.stationDto;
import Ontdekstation013.ClimateChecker.features.station.endpoint.stationTitleDto;
import Ontdekstation013.ClimateChecker.features.user.User;
import Ontdekstation013.ClimateChecker.features.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceOld {

    private final StationRepositoryCustom stationRepository;
    private final SensorService sensorService;
    private final UserRepository userRepository;

    @Autowired
    public StationServiceOld(StationRepositoryCustom stationRepository, SensorService sensorService, UserRepository userRepository) {
        this.stationRepository = stationRepository;
        this.sensorService = sensorService;
        this.userRepository = userRepository;
    }

    public stationDto findStationById(long id) {
        StationOld station = stationRepository.findById(id).get();
        stationDto newdto = stationToStationDTO(station);
        return newdto;
    }

    public stationDto findStationByRegistrationCode(long registrationCode, String databaseTag) {
        StationOld station = stationRepository.findByRegistrationCodeAndDatabaseTag(registrationCode, databaseTag). orElse(null);
        stationDto newdto = null;
        if(station != null){
            newdto = stationToStationDTO(station);
        }
        return newdto;
    }

    public stationTitleDto stationToStationTitleDTo (StationOld station){
        stationTitleDto newdto = new stationTitleDto();
        newdto.setId(station.getStationID());
        newdto.setName(station.getName());


        return newdto;
    }


    public stationDto stationToStationDTO (StationOld station){
        stationDto newdto = new stationDto();
        newdto.setId(station.getStationID());
        newdto.setName(station.getName());
        newdto.setHeight(station.getLocation().getHeight());
        newdto.setDirection(station.getLocation().getDirection());
        newdto.setLocationId(station.getLocation().getLocationID());
        newdto.setLatitude(station.getLocation().getLatitude());
        newdto.setLongitude(station.getLocation().getLongitude());
        newdto.setIspublic(station.isPublic());
        newdto.setIsoutside(station.getLocation().isOutside());
        newdto.setSensors(sensorService.getSensorsByStationId(station.getStationID()));

        return newdto;
    }

    public List<stationTitleDto> getAllByUserId(long userId) {
        Iterable<StationOld> stationList = stationRepository.findAllByOwner_UserID(userId);

        List<stationTitleDto> newDtoList = new ArrayList<>();
        for (StationOld station: stationList
        ) {

            newDtoList.add(stationToStationTitleDTo(station));

        }

        return newDtoList;
    }

    // not yet functional
    public List<stationTitleDto> getAll() {
        Iterable<StationOld> StationList = stationRepository.findAll();
        List<stationTitleDto> newDtoList = new ArrayList<>();
        for (StationOld station: StationList
        ) {

            newDtoList.add(stationToStationTitleDTo(station));

        }


        return newDtoList;
    }

    public List<stationDto> getAllStations() {
        Iterable<StationOld> StationList = stationRepository.findAll();
        List<stationDto> newDtoList = new ArrayList<>();
        for (StationOld station: StationList)
        {
            newDtoList.add(stationToStationDTO(station));
        }
        return newDtoList;
    }


    // not yet functional
    public List<stationTitleDto> getAllByPageId(long pageId) {
        List<stationTitleDto> newDtoList = new ArrayList<stationTitleDto>();


        return newDtoList;
    }


    // Koppel een gebruiker aan bestaand station
    public boolean registerStation(registerStationDto stationDto) {
        StationOld station = stationRepository.findByRegistrationCodeAndDatabaseTag(stationDto.getRegisterCode(), stationDto.getDatabaseTag()).orElse(null);
        User owner = userRepository.findById(stationDto.getUserId()).orElse(null);
        boolean succes = false;

        if (station != null && owner != null){
            station.setOwner(owner);
            station.setPublic(stationDto.isPublicInfo());
            station.setName(stationDto.getStationName());

            station.getLocation().setHeight(stationDto.getHeight());
            station.getLocation().setDirection(stationDto.getDirection());
            station.getLocation().setOutside(stationDto.isOutside());

            stationRepository.save(station);
            succes = true;
        }

        return succes;
    }

    public void deleteStation(long id) {

        stationRepository.deleteById(id);
    }

    public void editStation(editStationDto stationDto) {
        StationOld currentStation = stationRepository.findById(stationDto.getId()).get();
        currentStation.setName(stationDto.getName());
        currentStation.setPublic(stationDto.isPublic());

        stationRepository.save(currentStation);
    }

    public boolean findByRegistrationCode(String databaseTag, long registrationCode){
        boolean available = false;
        StationOld station = stationRepository.findByRegistrationCodeAndDatabaseTag(registrationCode, databaseTag).orElse(null);
        if(station != null) {
            if(station.getName() == null) {
                available = true;
            }
        }
        return available;
    }




    // Zet meetjestad station in de database
    public boolean createStation(createStationDto createStationDto){
        boolean succes = false;

        Location location = new Location();
        location.setLocationID(createStationDto.getLocationId());
        location.setLongitude(createStationDto.longitude);
        location.setLatitude(createStationDto.latitude);
        StationOld station = new StationOld();
        station.setRegistrationCode((createStationDto.registrationCode));
        station.setDatabaseTag(createStationDto.databaseTag);
        station.setLocation(location);
        StationOld check = stationRepository.save(station);
        if (check != null){
            succes = true;
        }

        return succes;
    }


    public List<Long> getAllRegistrationCode(){
        Iterable<StationOld> StationList = stationRepository.findAll();
        List<Long> registrationCodeList = new ArrayList<>();
        for (StationOld station: StationList)
        {
            registrationCodeList.add(station.getRegistrationCode());
        }
        return registrationCodeList;
    }





}