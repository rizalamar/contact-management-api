package rizalamar.contact_management_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rizalamar.contact_management_api.entities.Contact;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.WebResponse;
import rizalamar.contact_management_api.models.contact.ContactResponse;
import rizalamar.contact_management_api.models.contact.CreateContactRequest;
import rizalamar.contact_management_api.services.ContactService;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public WebResponse<ContactResponse> create (User user, @RequestBody CreateContactRequest request){
        ContactResponse contactResponse = contactService.create(user, request);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @GetMapping(path = "/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse> get(User user, @PathVariable("contactId") String contactId) {
        ContactResponse contactResponse = contactService.get(user, contactId);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }
}
