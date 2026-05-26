package rizalamar.contact_management_api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import rizalamar.contact_management_api.entities.Contact;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.contact.ContactResponse;
import rizalamar.contact_management_api.models.contact.CreateContactRequest;
import rizalamar.contact_management_api.models.contact.UpdateContactRequest;
import rizalamar.contact_management_api.repositories.ContactRepository;

import java.util.Objects;
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

    @Transactional(readOnly = true)
    public ContactResponse get(User user, String id) {
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        return toContactResponse(contact);
    }

    @Transactional
    public ContactResponse update (User user, UpdateContactRequest request) {
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        if(Objects.nonNull(request.getFirstName())) {
            contact.setFirstName(request.getFirstName());
        }
        if(Objects.nonNull(request.getLastName())){
            contact.setLastName(request.getLastName());
        }
        if(Objects.nonNull(request.getEmail())) {
            contact.setEmail(request.getEmail());
        }
        if(Objects.nonNull(request.getPhone())){
            contact.setPhone(request.getPhone());
        }

        contactRepository.save(contact);

        return toContactResponse(contact);
    }
    @Transactional
    public void delete (User user, String id){
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contactRepository.delete(contact);
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
