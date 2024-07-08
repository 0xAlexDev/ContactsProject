package com.turing.contacts.models.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthResponseDTO {
    private String token;
    private Long expiresIn;
    private UserDTO user;
}
