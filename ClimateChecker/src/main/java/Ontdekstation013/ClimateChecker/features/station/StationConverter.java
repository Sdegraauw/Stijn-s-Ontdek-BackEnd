package Ontdekstation013.ClimateChecker.features.station;

import Ontdekstation013.ClimateChecker.features.sensor.SensorService;
import Ontdekstation013.ClimateChecker.features.station.endpoint.stationDto;
import Ontdekstation013.ClimateChecker.features.station.endpoint.stationTitleDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationConverter {


    public stationTitleDto stationToStationTitleDTo(Station station){
        stationTitleDto newdto = new stationTitleDto();
        newdto.setId(station.getStationID());
        newdto.setName(station.getName());

        return newdto;
    };

    public stationDto stationToStationDTO(Station station){
        stationDto newdto = new stationDto();
        newdto.setId(station.getStationID());
        newdto.setName(station.getName());
        newdto.setLocationId(station.getLocation().getLocationID());
        newdto.setLatitude(station.getLocation().getLatitude());
        newdto.setLongitude(station.getLocation().getLongitude());
        newdto.setIspublic(station.isPublic());

        return newdto;
    }


}
