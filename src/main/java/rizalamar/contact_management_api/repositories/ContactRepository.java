package rizalamar.contact_management_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rizalamar.contact_management_api.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
}
