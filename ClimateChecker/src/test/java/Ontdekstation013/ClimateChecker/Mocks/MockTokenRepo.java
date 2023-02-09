package Ontdekstation013.ClimateChecker.Mocks;

import Ontdekstation013.ClimateChecker.models.Token;
import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.repositories.TokenRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockTokenRepo implements TokenRepository {


    @Override
    public boolean existsByUser(User user) {
        return false;
    }

    @Override
    public Token findByUser(User user) {
        return null;
    }

    @Override
    public List<Token> findAll() {
        return null;
    }

    @Override
    public List<Token> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Token> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Token> findAllById(Iterable<Long> longs) {
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
    public void delete(Token entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Token> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Token> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Token> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Token> findById(Long aLong) {
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
    public <S extends Token> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Token> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Token> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Token getOne(Long aLong) {
        return null;
    }

    @Override
    public Token getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Token> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Token> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Token> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Token> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Token> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Token> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Token, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
