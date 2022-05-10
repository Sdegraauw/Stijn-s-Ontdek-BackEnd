package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.Sensor;
import Ontdekstation013.ClimateChecker.models.SensorType;
import Ontdekstation013.ClimateChecker.models.dto.sensorDto;
import Ontdekstation013.ClimateChecker.models.dto.sensorTypeDto;
import Ontdekstation013.ClimateChecker.repositories.SensorRepository;
import Ontdekstation013.ClimateChecker.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private TypeRepository typeRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository, TypeRepository typeRepository) {
        this.sensorRepository = sensorRepository;
        this.typeRepository = typeRepository;
    }

    public sensorDto findSensorById(long id) {
        Sensor sensor = sensorRepository.findById(id).get();
        sensorDto newdto = sensorToSensorDTO(sensor);

        return newdto;
    }

    public sensorDto sensorToSensorDTO(Sensor sensor){
        sensorDto newdto = new sensorDto();
        newdto.setId(sensor.getSensorID());
        newdto.setStationId(sensor.getStation().getStationID());
        newdto.setData(sensor.getSensorData());
        newdto.setTypeId(sensor.getSensorType().getTypeID());

        return newdto;
    }

    // not yet functional
    public List<sensorDto> getAll() {
        Iterable<Sensor> SensorList = sensorRepository.findAll();
        List<sensorDto> newDtoList = new ArrayList<>();
        for (Sensor sensor: SensorList
        ) {

            newDtoList.add(sensorToSensorDTO(sensor));

        }


        return newDtoList;
    }

    // not yet functional
    public List<sensorTypeDto> getAllSensorTypes() {
        List<sensorTypeDto> newDtoList = new ArrayList();
        Iterable<SensorType> sensorTypes = typeRepository.findAll();


        for (SensorType type:sensorTypes
             ) {
            sensorTypeDto newdto = new sensorTypeDto();

            newdto.setId(type.getTypeID());
            newdto.setName(type.getTypeName());

            newDtoList.add(newdto);
        }


        return newDtoList;
    }

    // not yet functional
    public List<sensorDto> getSensorsByType(long typeId) {
        Iterable<Sensor> sensorList = sensorRepository.findAll();

        List<sensorDto> newDtoList = new ArrayList<>();
        for (Sensor sensor: sensorList
        ) {
            if (sensor.getSensorType().getTypeID() == typeId)
            newDtoList.add(sensorToSensorDTO(sensor));
        }

        return newDtoList;
    }

    // not yet functional
    public List<sensorDto> getSensorsByStation(long stationId) {
        Iterable<Sensor> sensorList = sensorRepository.findAllByStation(stationId);

        List<sensorDto> newDtoList = new ArrayList<>();
        for (Sensor sensor: sensorList
        ) {

            if (sensor.getStation().getStationID() == stationId)
            newDtoList.add(sensorToSensorDTO(sensor));

        }

        return newDtoList;
    }

    // not yet functional
    public void createSensor(sensorDto sensorDto) {

    }

    public void createSensorType(sensorTypeDto sensorTypeDto) {
    
    }

    public void deleteSensor(long sensorId) {
    }
}
