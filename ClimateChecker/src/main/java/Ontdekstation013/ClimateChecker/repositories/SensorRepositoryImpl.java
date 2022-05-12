package Ontdekstation013.ClimateChecker.repositories;

import Ontdekstation013.ClimateChecker.models.Sensor;
import Ontdekstation013.ClimateChecker.models.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.management.Query;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SensorRepositoryImpl implements SensorRepositoryCustom {
    @Autowired
    @Lazy
    SensorRepository sensorRepository;

    public List<Sensor> findAllByType(long typeId) {
        List<Sensor> sensors = new ArrayList<>();

        String queryStr = "SELECT * FROM SENSOR WHERE SENSOR_TYPE = "+typeId;


        return sensors;
    }



    public List<Sensor> findAllByStation(long stationId) {
        List<Sensor> sensors = new ArrayList<>();

        return sensors;
    }
}
