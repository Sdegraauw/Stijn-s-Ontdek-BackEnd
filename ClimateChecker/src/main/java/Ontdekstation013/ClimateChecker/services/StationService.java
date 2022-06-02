package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.Location;
import Ontdekstation013.ClimateChecker.models.Sensor;
import Ontdekstation013.ClimateChecker.models.Station;

import java.util.ArrayList;
import java.util.List;

import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    private final StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public stationDto findStationById(long id) {
        Station station = stationRepository.findById(id).get();
        stationDto newdto = stationToStationDTO(station);
        return newdto;
    }

    public stationTitleDto stationToStationTitleDTo (Station station){
        stationTitleDto newdto = new stationTitleDto();
        newdto.setId(station.getStationID());
        newdto.setName(station.getName());


        return newdto;
    }


    public stationDto stationToStationDTO (Station station){
        stationDto newdto = new stationDto();
        newdto.setId(station.getStationID());
        newdto.setName(station.getName());
        newdto.setHeight(station.getHeight());
        newdto.setLocationId(station.getLocation().getLocationID());
        newdto.setLocationName(station.getLocation().getLocationName());
        newdto.setLatitude(station.getLocation().getLatitude());
        newdto.setLongitude(station.getLocation().getLongitude());
        newdto.setIspublic(station.isPublic());
        //newdto.setSensors(station.getSensors());


        return newdto;
    }

    public List<stationTitleDto> getAllByUserId(long userId) {
        Iterable<Station> stationList = stationRepository.findAllByUserId(userId);

        List<stationTitleDto> newDtoList = new ArrayList<>();
        for (Station station: stationList
        ) {

            newDtoList.add(stationToStationTitleDTo(station));

        }

        return newDtoList;
    }

    // not yet functional
    public List<stationTitleDto> getAll() {
        Iterable<Station> StationList = stationRepository.findAll();
        List<stationTitleDto> newDtoList = new ArrayList<>();
        for (Station station: StationList
        ) {

            newDtoList.add(stationToStationTitleDTo(station));

        }


        return newDtoList;
    }

    public List<stationDto> getAllStations() {
        Iterable<Station> StationList = stationRepository.findAll();
        List<stationDto> newDtoList = new ArrayList<>();
        for (Station station: StationList
        ) {
            newDtoList.add(stationToStationDTO(station));
        }
        return newDtoList;
    }

    // not yet functional
    public List<stationTitleDto> getAllByPageId(long pageId) {
        List<stationTitleDto> newDtoList = new ArrayList<stationTitleDto>();


        return newDtoList;
    }


    // Returns false if not all information is filled in
    // Returns true if successful
    public boolean createStation(registerStationDto stationDto) {

        if (stationDto.getUserId() < 1 || stationDto.getStationname().equals("")
                || stationDto.getAddress().equals("")) {
            return false;
        }


        User owner = new User();
        owner.setUserID(stationDto.getUserId());

        Location location = new Location(stationDto.getAddress(), stationDto.getLatitude(), stationDto.getLongitude());

        Station station = new Station(owner, stationDto.getStationname(), stationDto.getHeight(), location, stationDto.isIspublic());

        stationRepository.save(station);

        return true;
    }

    public void deleteStation(long id) {

        stationRepository.deleteById(id);
    }

    public void editStation(editStationDto stationDto) {
        Station currentStation = stationRepository.findById(stationDto.getId()).get();

        currentStation.setName(stationDto.getName());
        currentStation.setHeight(stationDto.getHeight());
        currentStation.setPublic(stationDto.isIspublic());

        Location location = new Location(stationDto.getAddress(), stationDto.getLatitude(), stationDto.getLongitude());
        currentStation.setLocation(location);

        stationRepository.save(currentStation);
    }
}