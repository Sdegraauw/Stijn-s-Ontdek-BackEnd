package Ontdekstation013.ClimateChecker.Mocks;

<<<<<<< HEAD
import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.repositories.UserRepository;
=======
import Ontdekstation013.ClimateChecker.features.user.User;
import Ontdekstation013.ClimateChecker.features.user.UserRepository;

>>>>>>> s3nj23
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class MockUserRepo implements UserRepository {

public List<User> userList;


    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
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
    public void delete(User entity) {
        if(userList.contains(entity)){
            userList.remove(entity);
        }

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {

        for (User user: userList
        ) {

            if (user.getUserID() == aLong){
                return Optional.of(user);
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
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        User resultUser = null;
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUserID() == aLong){
                resultUser = userList.get(i);
            }
        }
        return resultUser;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    public MockUserRepo() {
        userList = new ArrayList<>();
    }

    public void FillDatabase(List<User> userList) {
        this.userList = userList;
    }


    @Override
    public boolean existsUserByUserName(String userName) {
        return false;
    }

    @Override
    public boolean existsUserByMailAddress(String mailAddress) {
        return false;
    }

    public User findByMailAddress(String mail) {
        return null;
    }
}