package com.turing.contacts.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Contact {
    @GeneratedValue
    @Id
    @Column(name = "id", unique = true)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "age", length = 50)
    private String age;

    //Relations 

    @ManyToOne()
    @JoinColumn(name = "id_address_book", nullable = false)
    private AddressBook addressBook;
}
