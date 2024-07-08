package com.turing.contacts.controllers;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.turing.contacts.models.DTOs.AuthResponseDTO;
import com.turing.contacts.models.DTOs.SignInDTO;
import com.turing.contacts.models.DTOs.SignUpDTO;
import com.turing.contacts.services.AuthService;

import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;
    


    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody @Valid SignUpDTO data) {
        try {
            authService.signUp(data);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @PostMapping("/signinWithCredentials")
    public ResponseEntity<Optional<AuthResponseDTO>> signInWithCredentials(@RequestBody @Valid SignInDTO data) {

        try {
            //Generate usernameAndPasswordToken from user's credentials to authenticate
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());

            //Try authenticate user with credentials
            Authentication authUser = authenticationManager.authenticate(usernamePassword);

            ResponseEntity<Optional<AuthResponseDTO>> responseEntity = authService.signInWithCredentials(authUser);

            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.ofNullable(null);
        }
        
    }

    @PostMapping("/signinWithToken")
    public ResponseEntity<Optional<AuthResponseDTO>> signinWithToken() {

        try {
            ResponseEntity<Optional<AuthResponseDTO>> responseEntity = authService.signInWithToken();
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.ofNullable(null);
        }
        
    }
    
}