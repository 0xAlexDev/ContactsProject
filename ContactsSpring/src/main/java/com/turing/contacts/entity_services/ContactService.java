package com.turing.contacts.entity_services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.turing.contacts.mappers.ContactMapper;
import com.turing.contacts.models.AddressBook;
import com.turing.contacts.models.Contact;
import com.turing.contacts.models.User;
import com.turing.contacts.models.DTOs.ContactDTO;
import com.turing.contacts.models.DTOs.FilterDTO;
import com.turing.contacts.repositories.AddressBookRepository;
import com.turing.contacts.repositories.ContactRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class ContactService {
    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private ContactRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ContactDTO saveContact(ContactDTO contactDTO) throws Exception {
        
        Contact contact = contactMapper.toEntity(contactDTO);

        contact.setAddressBook(addressBookRepository.getReferenceById(contactDTO.getAddressBookId()));

        if(!isUserBookaddress(contactDTO)) throw new Exception();


        return contactMapper.toDTO(repository.save(contact));
    }

    public ContactDTO updateContact(ContactDTO contactDTO) throws Exception {

        if(!isUserBookaddress(contactDTO)) throw new Exception();
        
        Contact newContact = contactMapper.toEntity(contactDTO);

        newContact.setAddressBook(addressBookRepository.getReferenceById(contactDTO.getAddressBookId()));
        
        return contactMapper.toDTO(repository.save(newContact));
    }

    public Optional<ContactDTO> getContact(Long id) throws Exception {

        if(!isUserContact(id)) throw new Exception();

        try {
           Contact contact = repository.getReferenceById(id);
       
           return Optional.of(contactMapper.toDTO(contact));

       } catch (Exception e) {
           
           return Optional.empty();
       }
   }

    public void deleteContact(Long id) throws Exception {
        if(!isUserContact(id)) throw new Exception();
        repository.deleteById(id);
    }

    public List<ContactDTO> saveAllContact(List<ContactDTO> contactDTOs) throws Exception {

        for(ContactDTO contactDTO : contactDTOs){
            if(!isUserBookaddress(contactDTO)) throw new Exception();
        }

        List<Contact> contacts = contactDTOs.stream().map(contactDTO -> contactMapper.toEntity(contactDTO)).collect(Collectors.toList());

        contacts.forEach(contact ->{
            contact.setAddress(null);
        });

        contacts = repository.saveAll(contacts);

        return contacts.stream().map(contact -> contactMapper.toDTO(contact)).collect(Collectors.toList());
        
    }

    public List<ContactDTO> deleteAllContact(List<ContactDTO> contactDTOs) throws Exception {

        for(ContactDTO contactDTO : contactDTOs){
            if(!isUserContact(contactDTO.getId())) throw new Exception();
        }
        
        List<Contact> contacts = contactDTOs.stream().map(contactDTO -> contactMapper.toEntity(contactDTO)).collect(Collectors.toList());

        contacts.forEach(contact ->{
            contact.setAddress(null);
        });

        repository.deleteAll(contacts);

        return contacts.stream().map(contact -> contactMapper.toDTO(contact)).collect(Collectors.toList());
        
    }
    
    public List<ContactDTO> getAllContactsByFilter(FilterDTO filter) throws Exception{

        if(!isUserResource(filter.getUserId())) throw new Exception();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> personRoot = criteriaQuery.from(Contact.class);

        criteriaQuery.select(personRoot)
                     .where(criteriaBuilder
                     .equal(personRoot.get("addressBook").get("user").get("id"), filter.getUserId()));

        List<Contact> queryContacts = entityManager.createQuery(criteriaQuery).getResultList();
        
        return queryContacts.stream().map(addressBook -> contactMapper.toDTO(addressBook)).collect(Collectors.toList());
    }
   
    private User getUserFromSecurityContext(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Boolean isUserResource(Long resourceUserId){
        User user = getUserFromSecurityContext();
        return user.getId().equals(resourceUserId);
    }

    //checks if the user uses their own resources
    private Boolean isUserBookaddress(ContactDTO contactDTO){
        AddressBook addressBook = addressBookRepository.getReferenceById(contactDTO.getAddressBookId());
        return isUserResource(addressBook.getUser().getId());
    }

    //checks if the user uses their own contact
    private Boolean isUserContact(Long id){
        User user = getUserFromSecurityContext();

        return user.getAddressBook().getContacts().stream().map(contact -> contact.getId()).toList().contains(id);
    }
}
