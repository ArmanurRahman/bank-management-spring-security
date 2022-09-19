package net.armanit.springsecuritydemo.repository;

import net.armanit.springsecuritydemo.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}
