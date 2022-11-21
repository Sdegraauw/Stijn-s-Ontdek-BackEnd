package Ontdekstation013.ClimateChecker.controller;

import Ontdekstation013.ClimateChecker.models.Token;
import Ontdekstation013.ClimateChecker.models.User;
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
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // create new user
    @PostMapping("register")
    public ResponseEntity<userDto> createNewUser(@RequestBody registerDto registerDto) {
        userService.createNewUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    // login user
    @PostMapping("login")
    public ResponseEntity<userDto> loginUser(@RequestBody loginDto loginDto){
        User user = new User(loginDto.getMailAddress(), Long.valueOf(1) );

        Token token = userService.createToken(user);
        userService.saveToken(token);


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
