package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.Station;
import Ontdekstation013.ClimateChecker.models.dto.stationDto;
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

    public Station findStationById(Long id) {
        Station station = stationRepository.findById(id).get();
        return station;
    }

    private stationDto StationToStationDTO (Station station){
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
}