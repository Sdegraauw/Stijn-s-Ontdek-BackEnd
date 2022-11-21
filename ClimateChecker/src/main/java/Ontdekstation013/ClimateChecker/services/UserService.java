package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.Station;
import Ontdekstation013.ClimateChecker.models.Token;
import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.repositories.TokenRepository;
import Ontdekstation013.ClimateChecker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
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
        newdto.setUsername(user.getUserName());
        return newdto;
    }

    public userDto userToUserDto (User user){
        userDto newdto = new userDto();
        newdto.setId(user.getUserID());
        newdto.setMailAddress(user.getMailAddress());
        newdto.setUsername(user.getUserName());
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

    public void createNewUser(registerDto registerDto) {


    }

    public void loginUser(loginDto loginDto) {


    }


    public void editUser(editUserDto registerDto) {

    }


//    PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Token createToken(User user){
        Token token = new Token();

        token.setUser(user);
        token.setCreationTime(LocalDateTime.now());
//        tokenDto.setLinkHash(encoder.encode(user.getMailAddress() + user.getUserID()));
        token.setLinkHash("abcdefhijk");

        return token;
    }

    public void saveToken(Token token){
        if (tokenRepository.existsByUser(token.getUser())) {
            token.setUser(token.getUser());
            System.out.println("something else");
        }
        token.setId(token.getUser().getUserID());
        tokenRepository.save(token);
    }

    public boolean decryptToken(Token token) {
//        Long userId = userRepository.findById(token.getUser().getUserID());
//        if (encoder.decode()) {
//        return true
//        }
        return false;
    }
}
