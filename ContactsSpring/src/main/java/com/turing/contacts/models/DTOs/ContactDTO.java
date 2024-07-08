package com.turing.contacts.models.DTOs;



import com.turing.contacts.utility.RegEx;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContactDTO {
    
    private Long id;

    @Pattern(regexp = RegEx.onlyLettes, message = "[Name wrong format]")
    @Size(min = 5, max = 30, message = "[Name length needed between 5-30]")
    private String name;

    @Pattern(regexp = RegEx.lettersAndNumbersWithSpaces, message = "[Surname wrong format]")
    @Size(min = 5, max = 30, message = "[Surname length needed between 5-30]")
    private String surname;

    @Pattern(regexp = RegEx.lettersAndNumbersWithSpaces, message = "[Address wrong format]")
    @Size(min = 5, max = 30, message = "[Address length needed between 5-30]")
    private String address;

    @Pattern(regexp = RegEx.onlyNumbers, message = "[Phonenumber wrong format]")
    @Size(max = 15, min = 9, message = "[Phonenumber length needed between 9-15]")
    private String phoneNumber;

    @Pattern(regexp = RegEx.onlyNumbers,message = "[Age wrong format]")
    @Size(max = 2,min = 1, message = "[Age length needed between 1-2]")
    private String age;

    private Long addressBookId;

}
