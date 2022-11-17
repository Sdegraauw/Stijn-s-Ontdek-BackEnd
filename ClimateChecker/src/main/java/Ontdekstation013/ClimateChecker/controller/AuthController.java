package Ontdekstation013.ClimateChecker.controller;

import Ontdekstation013.ClimateChecker.models.dto.sensorDto;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Authentication")

public class AuthController {


    private final UserService userService;
    @Autowired
    public AuthController(UserService userService) {this.userService = userService;}

    // create new user
    @PostMapping("register")
    @CrossOrigin
    public ResponseEntity<userDto> createNewUser(@RequestBody registerDto registerDto) {
        boolean created = userService.createNewUser(registerDto);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // login user
    @PostMapping("login")
    public ResponseEntity<userDto> loginUser(@RequestBody loginDto loginDto){

        userService.loginUser(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // edit user
    @PutMapping
    public ResponseEntity<userDto> editUser(@RequestBody editUserDto registerDto){

        userService.editUser(registerDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }


}
