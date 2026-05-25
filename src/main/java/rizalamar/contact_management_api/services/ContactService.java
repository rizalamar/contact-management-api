package rizalamar.contact_management_api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rizalamar.contact_management_api.entities.Contact;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.contact.ContactResponse;
import rizalamar.contact_management_api.models.contact.CreateContactRequest;
import rizalamar.contact_management_api.repositories.ContactRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ValidationService validationService;
    private final ContactRepository contactRepository;

    public ContactResponse create (User user, CreateContactRequest request){
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(user);

        contactRepository.save(contact);

        return toContactResponse(contact);

    }

    private ContactResponse toContactResponse(Contact contact){
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }
}
