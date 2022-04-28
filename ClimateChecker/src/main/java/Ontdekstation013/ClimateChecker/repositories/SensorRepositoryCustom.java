package Ontdekstation013.ClimateChecker.repositories;

import Ontdekstation013.ClimateChecker.models.Sensor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorRepositoryCustom {

    @Query(value = "SELECT * FROM SENSOR WHERE SENSOR_TYPE = ?1", nativeQuery = true)
    List<Sensor> findAllByType(long typeId);

    @Query(value = "SELECT * FROM SENSOR WHERE STATIONID = ?1", nativeQuery = true)
    List<Sensor> findAllByStation(long stationId);
}
