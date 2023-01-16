package Ontdekstation013.ClimateChecker.repositories;

import Ontdekstation013.ClimateChecker.models.Region;
import Ontdekstation013.ClimateChecker.models.RegionCords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionCordsRepository extends JpaRepository<RegionCords, Long> {
}
