package Ontdekstation013.ClimateChecker.repositories;

import Ontdekstation013.ClimateChecker.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>, SensorRepositoryCustom {
}