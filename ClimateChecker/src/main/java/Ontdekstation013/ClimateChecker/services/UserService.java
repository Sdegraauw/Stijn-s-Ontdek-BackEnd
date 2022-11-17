package Ontdekstation013.ClimateChecker.services;
import Ontdekstation013.ClimateChecker.models.Location;
import Ontdekstation013.ClimateChecker.models.Station;
import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public userDto findUserById(long id) {
        User user = userRepository.findById(id).get();
        userDto newdto = userToUserDto(user);
        return newdto;
    }

    public userDataDto userToUserDataDto (User user){
        userDataDto newdto = new userDataDto();
        newdto.setId(user.getUserID());
        newdto.setMailAddress(user.getMailAddress());
        newdto.setFirstName(user.getFirstName());
        newdto.setNamePreposition(user.getNamePreposition());
        newdto.setLastName(user.getLastName());
        return newdto;
    }

    public userDto userToUserDto (User user){
        userDto newdto = new userDto();
        newdto.setId(user.getUserID());
        newdto.setMailAddress(user.getMailAddress());
        newdto.setFirstName(user.getFirstName());
        newdto.setNamePreposition(user.getNamePreposition());
        newdto.setLastName(user.getLastName());
        newdto.setPasswordHash(user.getPasswordHash());
        newdto.setPasswordSalt(user.getPasswordSalt());
        return newdto;
    }

    // not yet functional
    public List<userDataDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<userDataDto> newDtoList = new ArrayList<>();

        for (User user: userList
        ) {

            newDtoList.add(userToUserDataDto(user));
        }


        return newDtoList;
    }

    // not yet functional
    public List<userDataDto> getAllByPageId(long pageId) {
        List<userDataDto> newDtoList = new ArrayList<userDataDto>();

        return newDtoList;
    }



    public void deleteUser(long Id) {

    }

    public boolean createNewUser(registerDto registerDto) {
        if (registerDto.getFirstName().length() < 256 && registerDto.getNamePreposition().length() < 256 && registerDto.getLastName().length() < 256) {
            if (registerDto.getMailAddress().contains("@")) {
                User user = new User(registerDto.getFirstName(), registerDto.getNamePreposition(), registerDto.getLastName(), registerDto.getMailAddress());
                userRepository.save(user);
                System.out.println("Gelukt");
                return true;
            }
        }
        System.out.println("Niet gelukt");
        return false;
    }

    public void loginUser(loginDto loginDto) {
        System.out.println(loginDto.getPassword());
    }


    public void editUser(editUserDto registerDto) {

    }
}
