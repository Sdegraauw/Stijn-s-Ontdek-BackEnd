package Ontdekstation013.ClimateChecker.repositories;

import Ontdekstation013.ClimateChecker.models.Sensor;

import java.util.List;

public interface SensorRepositoryCustom {

    List<Sensor> findAllByType(long typeId);

    List<Sensor> findAllByStation(long stationId);
}
