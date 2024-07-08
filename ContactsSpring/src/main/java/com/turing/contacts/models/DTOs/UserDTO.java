package com.turing.contacts.models.DTOs;

import com.turing.contacts.enums.UserRole;
import com.turing.contacts.utility.RegEx;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {

    private Long id;

    @Pattern(regexp = RegEx.lettersAndNumbers)
    private String username;

    @Pattern(regexp = RegEx.lettersAndNumbers)
    @Size(max = 8)
    private String password;  

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private AddressBookDTO addressBook;

}
