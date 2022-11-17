package Ontdekstation013.ClimateChecker.controller;

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
    public ResponseEntity<userDto> createNewUser(@RequestBody registerDto registerDto) {
        userService.createNewUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    // login user
    @PostMapping("login")
    public ResponseEntity<userDto> loginUser(@RequestBody loginDto loginDto){

        if(userService.verifyMail(loginDto)) {
            // TODO Link Code
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // edit user
    @PutMapping
    public ResponseEntity<userDto> editUser(@RequestBody editUserDto registerDto){

        userService.editUser(registerDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }


}
