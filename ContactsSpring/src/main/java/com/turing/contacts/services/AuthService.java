package com.turing.contacts.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.turing.contacts.entity_services.UserService;
import com.turing.contacts.enums.UserRole;
import com.turing.contacts.mappers.UserMapper;
import com.turing.contacts.models.User;
import com.turing.contacts.models.DTOs.AuthResponseDTO;
import com.turing.contacts.models.DTOs.SignUpDTO;
import com.turing.contacts.models.DTOs.UserDTO;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  TokenProviderService tokenProviderService;

  @Autowired
  UserService userService;

  @Autowired
  UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) {
    //In this case we want that user credentials are the couple (username - password)
    User user = userService.getUserFromCredentials(username);
    return user;
  }

  public UserDTO fromDetailsToDTO(User user) {
    UserDTO userDTO = userMapper.toDTO(user);
    return userDTO;
  }

  public UserDTO signUp(SignUpDTO data) throws Exception {
    if (userService.getUserFromCredentials(data.getUsername()) != null) {
      throw new Exception("Username already exists");
    }

    //create encriptedPSW
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

    //Set user values
    User newUser = new User();
    newUser.setUsername(data.getUsername());
    newUser.setPassword(encryptedPassword);
    newUser.setRole(UserRole.USER);

    return userService.signUpUser(newUser);
  }

  public ResponseEntity<Optional<AuthResponseDTO>> signInWithCredentials(Authentication authUser){
    //If is authenticated create JWT and return for future requests
    if(authUser.isAuthenticated()){
        String accessToken = tokenProviderService.generateAccessToken((User) authUser.getPrincipal());
        AuthResponseDTO AuthResponseDTO = new AuthResponseDTO();
        AuthResponseDTO.setToken(accessToken);
        AuthResponseDTO.setUser(fromDetailsToDTO((User) authUser.getPrincipal()));
        return ResponseEntity.ok(Optional.of(AuthResponseDTO));
    }

    return ResponseEntity.ofNullable(null);
  }

  public ResponseEntity<Optional<AuthResponseDTO>> signInWithToken(){
    Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

    //If is authenticated create JWT and return for future requests
    if(authUser.isAuthenticated()){
        String accessToken = tokenProviderService.generateAccessToken((User) authUser.getPrincipal());
        AuthResponseDTO AuthResponseDTO = new AuthResponseDTO();
        AuthResponseDTO.setToken(accessToken);
        AuthResponseDTO.setUser(fromDetailsToDTO((User) authUser.getPrincipal()));
        return ResponseEntity.ok(Optional.of(AuthResponseDTO));
    }

    return ResponseEntity.ofNullable(null);
  }

  


}