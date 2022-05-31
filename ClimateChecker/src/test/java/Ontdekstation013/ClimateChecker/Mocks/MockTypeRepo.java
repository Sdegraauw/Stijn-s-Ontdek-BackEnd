package Ontdekstation013.ClimateChecker.Mocks;

import Ontdekstation013.ClimateChecker.models.Location;
import Ontdekstation013.ClimateChecker.models.SensorType;
import Ontdekstation013.ClimateChecker.repositories.LocationRepository;
import Ontdekstation013.ClimateChecker.repositories.TypeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockTypeRepo implements TypeRepository {
    public List<SensorType> typeList;

    public MockTypeRepo(){
        this.typeList = new ArrayList<>();
    }

    public void FillDatabase(List<SensorType> typeList){
        this.typeList = typeList;
    }

    @Override
    public List<SensorType> findAll() {
        return typeList;
    }

    @Override
    public List<SensorType> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<SensorType> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<SensorType> findAllById(Iterable<Long> longs) {
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
    public void delete(SensorType entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends SensorType> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends SensorType> S save(S entity) {
        return null;
    }

    @Override
    public <S extends SensorType> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<SensorType> findById(Long aLong) {
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
    public <S extends SensorType> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends SensorType> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<SensorType> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SensorType getOne(Long aLong) {
        return null;
    }

    @Override
    public SensorType getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends SensorType> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SensorType> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends SensorType> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends SensorType> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SensorType> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SensorType> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SensorType, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}