package Ontdekstation013.ClimateChecker.repositories;

import Ontdekstation013.ClimateChecker.models.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<SensorType, Long> {
}
