package com.turing.contacts.models.DTOs;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressBookDTO {

    private Long id;

    List<ContactDTO> contacts;
    
}
