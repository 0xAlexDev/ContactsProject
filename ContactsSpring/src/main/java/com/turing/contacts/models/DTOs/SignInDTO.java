package com.turing.contacts.models.DTOs;

import com.turing.contacts.utility.RegEx;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignInDTO {
    
    @Pattern(regexp = RegEx.onlyLettes, message = "[Username wrong format]")
    @Size(min = 4, max = 20,message = "[Username must be between 8 - 12]")
    private String username;
    
    @Pattern(regexp = RegEx.lettersAndNumbers, message = "[Password wrong format]")
    @Size(min = 8, max = 12,message = "[Password must be between 8 - 12]")
    private String password;
    
}
