package Ontdekstation013.ClimateChecker.services;
import Ontdekstation013.ClimateChecker.models.Token;
import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.repositories.TokenRepository;
import Ontdekstation013.ClimateChecker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailSenderService emailService;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository, EmailSenderService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
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
        newdto.setLastName(user.getLastName());
        newdto.setUserName(user.getUserName());
        return newdto;
    }

    public userDto userToUserDto (User user){
        userDto newdto = new userDto();
        newdto.setId(user.getUserID());
        newdto.setMailAddress(user.getMailAddress());
        newdto.setLastName(user.getLastName());
        newdto.setFirstName(user.getFirstName());
        newdto.setUserName(user.getUserName());
        return newdto;
    }

    // not yet functional
    public List<userDataDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<userDataDto> newDtoList = new ArrayList<>();

        for (User user: userList) {
            newDtoList.add(userToUserDataDto(user));
        }

        return newDtoList;
    }

    // not yet functional
    public List<userDataDto> getAllByPageId(long pageId) {
        List<userDataDto> newDtoList = new ArrayList<userDataDto>();

        return newDtoList;
    }

    public userDto editUser(editUserDto editUserDto) throws Exception {
        List<String> errorList = new ArrayList<>();
        User user = userRepository.findById(editUserDto.getId()).orElseThrow();

        if (editUserDto.getFirstName().length() < 256)
            user.setFirstName(editUserDto.getFirstName());
        else
            errorList.add("First name can't be longer than 256 characters");

        if (editUserDto.getLastName().length() < 256)
            user.setLastName(editUserDto.getLastName());
        else
            errorList.add("Last name can't be longer than 256 characters");

        if (editUserDto.getUserName().length() < 256) {
            if (!editUserDto.getUserName().equals(user.getUserName())) {
                if (!userRepository.existsUserByUserName(editUserDto.getUserName())) {
                    user.setUserName(editUserDto.getUserName());
                } else {
                    errorList.add("Username already in use");
                }
            }
        } else {
            errorList.add("Last name can't be longer than 256 characters");
        }

        editUserDto.setMailAddress(editUserDto.getMailAddress().toLowerCase());
        if (!user.getMailAddress().equals(editUserDto.getMailAddress())) {
            if (editUserDto.getMailAddress().contains("@")) {
                if (!userRepository.existsUserByMailAddress(editUserDto.getMailAddress())) {
                    if (errorList.size() == 0) {
                        String mail = user.getMailAddress();
                        user.setMailAddress(editUserDto.getMailAddress());
                        Token token = createToken(user);
                        user.setMailAddress(mail);
                        token.setUser(user);
                        saveToken(token);
                        emailService.sendEmailEditMail(editUserDto.getMailAddress(), user.getFirstName(), user.getLastName(), createLink(token, editUserDto.getMailAddress()));
                    }
                } else {
                    errorList.add("Email address already in use");
                }
            } else {
                errorList.add("Invalid email address");
            }
        }

        if (errorList.size() != 0){
            StringBuilder errorResponse = new StringBuilder();
            for(String error : errorList){
                errorResponse.append(error).append("\n");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorResponse.toString());
        }

        return userToUserDto(userRepository.save(user));
    }

    public void deleteUser(long id) {
        User user = userRepository.getById(id);
        emailService.deleteUserMail(user.getMailAddress(), user.getFirstName(), user.getLastName());
        userRepository.deleteById(id);
    }

    public User createNewUser(registerDto registerDto) {
        if (registerDto.getFirstName().length() < 256 && registerDto.getLastName().length() < 256 && registerDto.getUserName().length() < 256) {
            registerDto.setMailAddress(registerDto.getMailAddress().toLowerCase());
            if (registerDto.getMailAddress().contains("@")) {
                if (!userRepository.existsUserByUserNameOrMailAddress(registerDto.getUserName(), registerDto.getMailAddress())) {
                    User user = new User(registerDto.getMailAddress(), registerDto.getFirstName(), registerDto.getLastName(), registerDto.getUserName());
                    return userRepository.save(user);
                }
            }
        }
        return null;
    }

    public User verifyMail(loginDto loginDto) {
        return userRepository.findByMailAddress(loginDto.getMailAddress());
    }


    PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Token createToken(User user) {
        Token token = new Token();

        token.setUser(user);
        token.setCreationTime(LocalDateTime.now());
        token.setLinkHash(encoder.encode(user.getMailAddress() + user.getUserID()));


        return token;
    }

    public void saveToken(Token token) {
            if (tokenRepository.existsByUser(token.getUser())) {
                token.setUser(token.getUser());
            }
            token.setId(token.getUser().getUserID());
            tokenRepository.save(token);
    }

    public boolean verifyToken(String linkHash, String email) {
        User user = userRepository.findByMailAddress(email);
        Token officialToken = tokenRepository.findByUser(user);
        if (officialToken != null){
            if (officialToken.getLinkHash().equals(linkHash) && encoder.matches(user.getMailAddress() + user.getUserID(), officialToken.getLinkHash())) {
                if (officialToken.getCreationTime().isBefore(LocalDateTime.now().plusMinutes(5))) {
                    tokenRepository.delete(officialToken);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verifyToken(String linkHash, String oldEmail, String newEmail) {
        User user = userRepository.findByMailAddress(oldEmail);
        Token officialToken = tokenRepository.findByUser(user);
        if (officialToken != null){
            if (officialToken.getLinkHash().equals(linkHash) && encoder.matches(newEmail + user.getUserID(), officialToken.getLinkHash())) {
                if (officialToken.getCreationTime().isBefore(LocalDateTime.now().plusMinutes(5))) {
                    user.setMailAddress(newEmail);
                    userRepository.save(user);
                    tokenRepository.delete(officialToken);
                    return true;
                }
            }
        }
        return false;
    }

    public String createLink(Token token){
        String domain = "http://localhost:8082/";
        return (domain + "api/Authentication/verify" + "?linkHash=" + token.getLinkHash() + "&email=" + token.getUser().getMailAddress());
    }

    public String createLink(Token token, String newEmail){
        String domain = "http://localhost:8082/";
        return (domain + "api/User/verify" + "?linkHash=" + token.getLinkHash() + "&oldEmail=" + token.getUser().getMailAddress() + "&newEmail=" + newEmail);
    }
}
