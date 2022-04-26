package Ontdekstation013.ClimateChecker.repositories;

import Ontdekstation013.ClimateChecker.models.Station;

import java.util.List;

public interface StationRepositoryCustom {

    List<Station> findAllByUserId(long userId);

}
