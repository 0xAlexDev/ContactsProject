package com.turing.contacts.entity_services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.turing.contacts.mappers.UserMapper;
import com.turing.contacts.models.AddressBook;
import com.turing.contacts.models.User;
import com.turing.contacts.models.DTOs.UserDTO;
import com.turing.contacts.repositories.AddressBookRepository;
import com.turing.contacts.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressBookRepository addressBookRepository;


    public Optional<UserDTO> getUser(Long id) {
        
        try {
            User user = userRepository.getReferenceById(id);
        
            return Optional.of(userMapper.toDTO(user));

        } catch (Exception e) {
            
            return Optional.empty();
        }
    }

    public User getUserFromCredentials(String username) {
        
        User user = userRepository.findByUsername(username);
        return user;

    }

    @Transactional
    public UserDTO signUpUser(User user){

        //Clear all relations (handle manually cascade persistence)
        fixUserToSave(user);

        //if is create, we have to create first user's AddressBook 
        AddressBook addressBook = null;
        if(user.getId() == null) addressBook = createNewAddressbookForUser(user);

        //Save all entities
        User newUser = userRepository.save(user);
        if(addressBook != null) addressBookRepository.save(addressBook);
        
        
        return userMapper.toDTO(newUser);
    }

    private void fixUserToSave(User user){
        user.setAddressBook(null);
    }

    private AddressBook createNewAddressbookForUser(User user){
        AddressBook addressBook = new AddressBook();
        addressBook.setContacts(new ArrayList<>());
        addressBook.setUser(user);
        return addressBook;
    }
}
