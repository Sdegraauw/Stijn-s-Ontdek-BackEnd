package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.Sensor;
import Ontdekstation013.ClimateChecker.models.Station;

import java.util.ArrayList;
import java.util.List;

import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        //newdto.setSensors(station.getSensors());


        return newdto;
    }


    // not yet functional
    public List<stationTitleDto> getAllByUserId(long userId) {
        Iterable<Station> stationList = new ArrayList<>();// stationRepository.findAllByUserId(userId);

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

    // not yet functional
    public List<stationTitleDto> getAllByPageId(long pageId) {
        List<stationTitleDto> newDtoList = new ArrayList<stationTitleDto>();


        return newDtoList;
    }

    // not yet functional
    public void createStation(registerStationDto stationDto) {



    }

    // not yet functional
    public void deleteStation(long id) {


    }

    // not yet functional
    public void editStation(editStationDto stationDto) {


    }
}