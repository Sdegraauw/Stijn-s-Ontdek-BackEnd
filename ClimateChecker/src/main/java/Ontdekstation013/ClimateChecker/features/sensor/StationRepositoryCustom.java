package Ontdekstation013.ClimateChecker.features.sensor;

import org.springframework.stereotype.Repository;

import Ontdekstation013.ClimateChecker.features.station.Station;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepositoryCustom extends StationRepository{

    List<Station> findAllByOwner_UserID(long userId);

    Optional<Station> findByRegistrationCodeAndDatabaseTag(long registrationCode, String databaseTag);

}
