package Ontdekstation013.ClimateChecker.models.controller;


import Ontdekstation013.ClimateChecker.services.StationService;
import Ontdekstation013.ClimateChecker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import Ontdekstation013.ClimateChecker.models.dto.*;


@RestController
@RequestMapping("/api/User")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // get user by id
    @GetMapping("{userId}")
    public ResponseEntity<userDto> getUserById(@PathVariable Long userId){
        userDto dto = userService.findUserById(userId);
        return ResponseEntity.ok(dto);

    }

    // get all users
    @GetMapping
    public ResponseEntity<List<userDataDto>> getAllUsers(){

        List<userDataDto> newDtoList = userService.getAllUsers();
        return ResponseEntity.ok(newDtoList);
    }

    // get users by page number
    @GetMapping("page/{pageNumber}")
    public ResponseEntity<List<userDataDto>> getAllUsersByPage(@PathVariable Long pageId){

        List<userDataDto> newDtoList = userService.getAllByPageId(pageId);
        return ResponseEntity.ok(newDtoList);
    }

    // delete user
    @DeleteMapping("{userId}")
    public ResponseEntity<userDto> deleteUser(@PathVariable Long userId){

        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
