package Ontdekstation013.ClimateChecker.features.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementResultRepository extends JpaRepository<MeasurementResult, Long> {

}
