package com.turing.contacts.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.turing.contacts.interfaces.EntityMapperInterface;
import com.turing.contacts.models.AddressBook;
import com.turing.contacts.models.Contact;
import com.turing.contacts.models.DTOs.ContactDTO;

@Service
public class ContactMapper implements EntityMapperInterface <Contact,ContactDTO> {

    @Override
    public ContactDTO toDTO(Contact entity) {
        ModelMapper modelMapper = new ModelMapper();

        ContactDTO contactDTO = modelMapper.map(entity, ContactDTO.class);
        
        contactDTO.setAddressBookId(entity.getAddressBook().getId());

        return contactDTO;
    }

    @Override
    public Contact toEntity(ContactDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Contact contact = modelMapper.map(dto, Contact.class);

        contact.setAddressBook(new AddressBook(dto.getAddressBookId()));

        return contact;
    }
    
}
