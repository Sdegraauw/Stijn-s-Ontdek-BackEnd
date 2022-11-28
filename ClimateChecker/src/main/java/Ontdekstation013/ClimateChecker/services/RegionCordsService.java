package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.Location;
import Ontdekstation013.ClimateChecker.models.RegionCords;
import Ontdekstation013.ClimateChecker.models.Sensor;
import Ontdekstation013.ClimateChecker.models.dto.RegionAverageDto;
import Ontdekstation013.ClimateChecker.models.dto.RegionInfoDto;
import Ontdekstation013.ClimateChecker.models.dto.sensorDto;
import Ontdekstation013.ClimateChecker.models.dto.stationDto;
import Ontdekstation013.ClimateChecker.models.logic.RegionSurface;
import Ontdekstation013.ClimateChecker.repositories.RegionCordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionCordsService {

    private final RegionCordsRepository regionCordsRepository;
    private final RegionService regionService;
    private final LocationService locationService;
    private final StationService stationService;

    public List<RegionInfoDto> getAll()
    {
        List<RegionCords> cords = regionCordsRepository.findAll();
        List<RegionInfoDto> dtos = new ArrayList<>();

        long currentID = 0;

        for(RegionCords cord : cords)
        {
            if(currentID != cord.getRegion().getId())
            {
                RegionInfoDto regionInfoDto = new RegionInfoDto(cord.getRegion(), cords);
                regionInfoDto.averageData = getAverageRegionData(regionInfoDto);

                dtos.add(regionInfoDto);
                currentID = cord.getRegion().getId();
            }
        }

        return dtos;
    }

    public RegionInfoDto getAllByRegionId(long regionId)
    {
        List<RegionCords> all = regionCordsRepository.findAll();
        List<RegionCords> retval = new ArrayList<RegionCords>();

        for(RegionCords cords : all)
        {
            if(cords.getRegion().getId() == regionId) retval.add(cords);
        }

        return new RegionInfoDto(regionService.getById(regionId), retval);
    }

    public RegionAverageDto getAverageRegionData(RegionInfoDto info)
    {
        RegionAverageDto regionAverage = new RegionAverageDto();
        List<stationDto> stations = stationService.getAllStations();
        List<stationDto> stationInRegion = new ArrayList<stationDto>();

        RegionSurface surface = new RegionSurface(info.cordsList);

        for(stationDto station : stations)
        {
            Location location = locationService.findLocationById(station.getLocationId());

            if(surface.contains(location.getLatitude(), location.getLongitude()))
            {
                stationInRegion.add(station);
            }
        }

        double avgTemp = 0;
        int tempCount = 0;

        //Found all stations in region, now determine averages
        //TODO Implement all data types

        for(stationDto station : stationInRegion)
        {
            for(sensorDto sensor : station.getSensors())
            {
                if(sensor.getTypeId() == 1)             //temperature
                {
                    avgTemp+= sensor.getData();
                    tempCount++;
                }
            }
        }

        avgTemp = avgTemp/tempCount;

        regionAverage.setTemperature(avgTemp);

        return regionAverage;
    }

    @Autowired
    public RegionCordsService(RegionCordsRepository regionCordsRepository, RegionService regionService, StationService stationService
    , LocationService locationService)
    {
        this.regionCordsRepository = regionCordsRepository;
        this.regionService = regionService;
        this.stationService = stationService;
        this.locationService = locationService;
    }
}
