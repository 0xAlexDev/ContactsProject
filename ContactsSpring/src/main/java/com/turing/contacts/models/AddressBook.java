package com.turing.contacts.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class AddressBook {

    public AddressBook(){};

    public AddressBook(Long id){
        this.id = id;
    }

    @Column(name = "id", nullable = false, unique = true)
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    
    //Relations
    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "addressBook", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Contact> contacts = new ArrayList<>(); 

}
