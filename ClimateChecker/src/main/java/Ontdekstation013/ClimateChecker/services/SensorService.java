package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.Sensor;
import Ontdekstation013.ClimateChecker.models.dto.sensorDto;
import Ontdekstation013.ClimateChecker.models.dto.sensorTypeDto;
import Ontdekstation013.ClimateChecker.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public sensorDto findSensorById(Long id) {
        Sensor sensor = sensorRepository.findById(id).get();
        sensorDto newdto = SensorToSensorDTO(sensor);

        return newdto;
    }

    private sensorDto SensorToSensorDTO(Sensor sensor){
        sensorDto newdto = new sensorDto();
        newdto.setId(sensor.getSensorID());
        newdto.setStationId(sensor.getStation().getStationID());
        newdto.setData(sensor.getSensorData());
        newdto.setTypeId(sensor.getSensorType().getTypeID());

        return newdto;
    }

    // not yet functional
    public List<sensorDto> getAll() {
        List<sensorDto> newDtoList = new ArrayList<sensorDto>();


        return newDtoList;
    }

    // not yet functional
    public List<sensorTypeDto> getAllSensorTypes() {
        List<sensorTypeDto> newDtoList = new ArrayList<sensorTypeDto>();

        return newDtoList;
    }

    // not yet functional
    public List<sensorDto> getSensorsByType(Long typeId) {
        List<sensorDto> newDtoList = new ArrayList<sensorDto>();

        return newDtoList;
    }

    // not yet functional
    public List<sensorDto> getSensorsByStation(Long stationId) {
        List<sensorDto> newDtoList = new ArrayList<sensorDto>();

        return newDtoList;
    }

    // not yet functional
    public void createSensor(sensorDto sensorDto) {

    }

    public void createSensorType(sensorTypeDto sensorTypeDto) {
    
    }

    public void deleteSensor(Long sensorId) {
    }
}
