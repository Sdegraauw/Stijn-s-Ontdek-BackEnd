package Ontdekstation013.ClimateChecker.features.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<SensorType, Long> {
}