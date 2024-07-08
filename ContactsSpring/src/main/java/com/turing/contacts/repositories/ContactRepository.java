package com.turing.contacts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.turing.contacts.models.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}

