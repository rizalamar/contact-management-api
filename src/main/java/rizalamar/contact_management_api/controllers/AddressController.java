package rizalamar.contact_management_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rizalamar.contact_management_api.entities.Contact;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.WebResponse;
import rizalamar.contact_management_api.models.address.AddressResponse;
import rizalamar.contact_management_api.models.address.CreateAddressRequest;
import rizalamar.contact_management_api.models.address.UpdateAddressRequest;
import rizalamar.contact_management_api.services.AddressService;
import rizalamar.contact_management_api.services.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/contacts/{contactId}/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final ContactService contactService;

    @PostMapping
    public WebResponse<AddressResponse> create (User user, @PathVariable("contactId") String contactId, @RequestBody CreateAddressRequest request) {
        Contact contact = contactService.findContactEntityByUserAndId(user, contactId);
        AddressResponse addressResponse = addressService.create(contact, request);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @GetMapping("/{addressId}")
    public WebResponse<AddressResponse> get(
            User user,
            @PathVariable("contactId") String contactId,
            @PathVariable("addressId") String addressId
    ) {
        Contact contact = contactService.findContactEntityByUserAndId(user, contactId);
        AddressResponse addressResponse = addressService.get(contact, addressId);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @GetMapping
    public WebResponse<List<AddressResponse>> list (
            User user,
            @PathVariable("contactId") String contactId
    ) {
        Contact contact = contactService.findContactEntityByUserAndId(user, contactId);
        List<AddressResponse> addressResponses = addressService.list(contact);
        return WebResponse.<List<AddressResponse>>builder().data(addressResponses).build();
    }

    @PutMapping("/{addressId}")
    public WebResponse<AddressResponse> update(
            User user,
            @PathVariable("contactId") String contactId,
            @PathVariable("addressId") String addressId,
            @RequestBody UpdateAddressRequest request
            ) {
        request.setId(addressId);
        Contact contact = contactService.findContactEntityByUserAndId(user, contactId);
        AddressResponse addressResponse = addressService.update(contact, request);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @DeleteMapping("/{addressId}")
    public WebResponse<String> delete(
            User user,
            @PathVariable("contactId") String contactId,
            @PathVariable("addressId") String addressId
    ) {
        Contact contact = contactService.findContactEntityByUserAndId(user, contactId);
        addressService.delete(contact, addressId);
        return WebResponse.<String>builder().data("OK").build();
    }
}
