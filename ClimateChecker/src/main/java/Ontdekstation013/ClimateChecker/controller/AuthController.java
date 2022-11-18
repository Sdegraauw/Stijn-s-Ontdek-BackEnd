package Ontdekstation013.ClimateChecker.controller;

import Ontdekstation013.ClimateChecker.models.Mail;
import Ontdekstation013.ClimateChecker.models.dto.sensorDto;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.services.EmailSenderService;
//import Ontdekstation013.ClimateChecker.services.MailService;
import Ontdekstation013.ClimateChecker.services.UserService;
import ch.qos.logback.core.encoder.EchoEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Authentication")

public class AuthController {


    private final UserService userService;
    //private final MailService mailService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public AuthController(UserService userService, EmailSenderService emailSenderService)
    {
        this.userService = userService; this.emailSenderService = emailSenderService;
    }

    // create new user
    @PostMapping("register")
    public ResponseEntity<userDto> createNewUser(@RequestBody registerDto registerDto) throws Exception {
        userService.createNewUser(registerDto);

        emailSenderService.sendSignupMail(registerDto.getEmail(), registerDto.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    // login user
    @PostMapping("login")
    public ResponseEntity<userDto> loginUser(@RequestBody loginDto loginDto){
        userService.loginUser(loginDto);

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
