package com.turing.contacts.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.turing.contacts.entity_services.ContactService;
import com.turing.contacts.interfaces.ControllerValidator;
import com.turing.contacts.models.DTOs.ContactDTO;
import com.turing.contacts.models.DTOs.FilterDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contacts")
public class ContactController implements ControllerValidator<ContactDTO> {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@Valid @RequestBody ContactDTO contact) {

        Boolean validation = createValidation(contact);
        
        if(!validation) return ResponseEntity.badRequest().body(null);

        try {
            contact = contactService.saveContact(contact);
            return ResponseEntity.status(HttpStatus.OK).body(contact);
            
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }  
    
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contact) {
        ContactDTO contactDTO = contact;
        contactDTO.setId(id);

        Boolean validation = updateValidation(contactDTO);
        
        if(!validation) return ResponseEntity.badRequest().body(null);
        
        try {
            contact = contactService.updateContact(contact);
            return ResponseEntity.status(HttpStatus.OK).body(contact);
            
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }  

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ContactDTO>> getContact(@PathVariable Long id) {

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(id);

        Boolean validation = getValidation(contactDTO);

        if(!validation) return ResponseEntity.badRequest().body(null);

        try {
            Optional<ContactDTO> ContactDTO = contactService.getContact(id);
            System.out.println(ContactDTO.isPresent());
            return ResponseEntity.status(HttpStatus.OK).body(ContactDTO);
            
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteContact(@PathVariable Long id) {
        
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(id);

        Boolean validation = deleteValidation(contactDTO);

        if(!validation) return ResponseEntity.badRequest().body(null);

        try {
            contactService.deleteContact(id);
            
            return ResponseEntity.status(HttpStatus.OK).body(true);
            
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }  

    @PostMapping("/filter")
    public ResponseEntity<List<ContactDTO>> getAllContactsByFilter(@Valid @RequestBody FilterDTO filter) {

        try {
            List<ContactDTO> contacts =  contactService.getAllContactsByFilter(filter);
            
            return ResponseEntity.status(HttpStatus.OK).body(contacts);
            
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }  

    @PostMapping("/bulk/create")
    @Transactional
    public ResponseEntity<List<ContactDTO>> insertAll(@Valid @RequestBody List<ContactDTO> contacts) {

        for(ContactDTO contactDTO : contacts){
            if(!createValidation(contactDTO)) return ResponseEntity.badRequest().body(null);
        }
        
        try {
            List<ContactDTO> contactDTOs =  contactService.saveAllContact(contacts);
            
            return ResponseEntity.status(HttpStatus.OK).body(contactDTOs);
            
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/bulk/delete")
    @Transactional
    public ResponseEntity<List<ContactDTO>> deleteAll(@Valid @RequestBody List<ContactDTO> contacts) {

        for(ContactDTO contactDTO : contacts){
            if(!deleteValidation(contactDTO)) return ResponseEntity.badRequest().body(null);
        }
        
        
        try {
            List<ContactDTO> contactDTOs =  contactService.deleteAllContact(contacts);
            
            return ResponseEntity.status(HttpStatus.OK).body(contactDTOs);
            
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }


    @Override
    public Boolean updateValidation(ContactDTO dto) {
        if(dto == null) return false;
        if(dto.getId() == null) return false;
        if(dto.getAddressBookId() == null) return false;

        return true;
    }

    @Override
    public Boolean getValidation(ContactDTO dto) {
        if(dto == null) return false;
        if(dto.getId() == null) return false;

        return true;
    }

    @Override
    public Boolean createValidation(ContactDTO dto) {
        if(dto == null) return false;
        if(dto.getId() != null) return false;
        if(dto.getAddressBookId() == null) return false;

        return true;
    }

    @Override
    public Boolean deleteValidation(ContactDTO dto) {
        if(dto == null) return false;
        if(dto.getId() == null) return false;

        return true;
    }  
    
}
