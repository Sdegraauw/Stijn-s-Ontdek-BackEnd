package Ontdekstation013.ClimateChecker.Mocks;

import Ontdekstation013.ClimateChecker.models.Location;
import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.repositories.LocationRepository;
import Ontdekstation013.ClimateChecker.repositories.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockLocationRepo implements LocationRepository {
    @Override
    public List<Location> findAll() {
        return null;
    }

    @Override
    public List<Location> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Location> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Location> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Location entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Location> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Location> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Location> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Location> findById(Long aLong) {
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
    public <S extends Location> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Location> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Location> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Location getOne(Long aLong) {
        return null;
    }

    @Override
    public Location getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Location> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Location> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Location> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Location> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Location> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Location> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Location, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}