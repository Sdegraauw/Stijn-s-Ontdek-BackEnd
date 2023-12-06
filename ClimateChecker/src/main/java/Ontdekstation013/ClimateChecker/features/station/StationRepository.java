package Ontdekstation013.ClimateChecker.features.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByMeetjestadId(Long meetjestadId);
}
