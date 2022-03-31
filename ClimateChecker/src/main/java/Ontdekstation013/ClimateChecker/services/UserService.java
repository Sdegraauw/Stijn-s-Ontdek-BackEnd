package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.models.dto.userDataDto;
import Ontdekstation013.ClimateChecker.models.dto.userDto;
import Ontdekstation013.ClimateChecker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public userDto findUserById(Long id) {
        User user = userRepository.findById(id).get();
        userDto newdto = UserToUserDTO(user);
        return newdto;
    }

    // not yet done
    // convert all data
    private userDto UserToUserDTO (User user){
        userDto newdto = new userDto();
        newdto.setId(user.getUserID());
        return newdto;
    }

    // not yet functional
    public List<userDataDto> getAllUsers() {
        List<userDataDto> newDtoList = new ArrayList<userDataDto>();

        return newDtoList;
    }

    // not yet functional
    public List<userDataDto> getAllByPageId(Long pageId) {
        List<userDataDto> newDtoList = new ArrayList<userDataDto>();

        return newDtoList;
    }


    public void deleteUser(Long id) {


    }
}
