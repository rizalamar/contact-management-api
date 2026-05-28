package rizalamar.contact_management_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rizalamar.contact_management_api.entities.Address;
import rizalamar.contact_management_api.entities.Contact;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {
    // untuk nyari satu alamat spesifik milik kontak tertentu
    Optional<Address> findFirstByContactAndId(Contact contact, String id);

    // untuk ngambil semua alamat milik kontak tertentu
    List<Address> findAllByContact(Contact contact);
}
