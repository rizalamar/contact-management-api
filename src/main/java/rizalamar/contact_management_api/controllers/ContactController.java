package rizalamar.contact_management_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
