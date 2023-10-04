package Ontdekstation013.ClimateChecker.Mocks;

import Ontdekstation013.ClimateChecker.features.sensor.StationRepositoryCustom;
import Ontdekstation013.ClimateChecker.features.station.StationOld;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockStationRepo implements StationRepositoryCustom {
    public List<StationOld> stations;

    public MockStationRepo() {
        this.stations = new ArrayList<>();
    }

    public void FillDataBase(List<StationOld> stations) {
        this.stations = stations;
    }

    @Override
    public List<StationOld> findAll() {
        return stations;
    }

    @Override
    public List<StationOld> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<StationOld> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<StationOld> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return stations.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(StationOld entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends StationOld> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends StationOld> S save(S entity) {
        stations.add(entity);
        return null;
    }

    @Override
    public <S extends StationOld> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<StationOld> findById(Long aLong) {

        for (StationOld station: stations
        ) {

            if (station.getStationID() == aLong){
                return Optional.of(station);
            }

        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends StationOld> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends StationOld> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<StationOld> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public StationOld getOne(Long aLong) {
        return null;
    }

    @Override
    public StationOld getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends StationOld> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends StationOld> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends StationOld> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends StationOld> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends StationOld> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends StationOld> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends StationOld, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }


    @Override
    public List<StationOld> findAllByOwner_UserID(long userId) {
        List<StationOld> stationList = new ArrayList<>();
        for (StationOld station: stations
        ) {
            if (station.getOwner().getUserID() == userId){
                stationList.add(station);
            }
        }
        return stationList;
    }

    @Override
    public Optional<StationOld> findByRegistrationCodeAndDatabaseTag(long registrationCode, String databaseTag) {
        List<StationOld> stationList = new ArrayList<>();
        for (StationOld station: stations) {
            if (station.getRegistrationCode() == registrationCode && station.getDatabaseTag().equals(databaseTag)){
                return Optional.of(station);
            }
        }
        return Optional.of(new StationOld());
    }
}
