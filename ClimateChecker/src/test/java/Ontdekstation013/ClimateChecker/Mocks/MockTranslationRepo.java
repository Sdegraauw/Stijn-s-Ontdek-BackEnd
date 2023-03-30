package Ontdekstation013.ClimateChecker.Mocks;

import Ontdekstation013.ClimateChecker.models.Translation;
import Ontdekstation013.ClimateChecker.repositories.TranslationRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockTranslationRepo implements TranslationRepository {

    @Override
    public List<Translation> findAll() {
        return null;
    }

    @Override
    public List<Translation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Translation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Translation> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Translation entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Translation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Translation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Translation> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Translation> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Translation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Translation> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Translation> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Translation getOne(Integer integer) {
        return null;
    }

    @Override
    public Translation getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Translation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Translation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Translation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Translation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Translation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Translation> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Translation, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
