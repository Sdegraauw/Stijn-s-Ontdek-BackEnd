package Ontdekstation013.ClimateChecker.features.sensor;

import org.springframework.stereotype.Repository;

import Ontdekstation013.ClimateChecker.features.station.StationOld;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepositoryCustom extends StationRepository{

    List<StationOld> findAllByOwner_UserID(long userId);

    Optional<StationOld> findByRegistrationCodeAndDatabaseTag(long registrationCode, String databaseTag);

}
