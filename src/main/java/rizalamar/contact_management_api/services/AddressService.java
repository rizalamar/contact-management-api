package rizalamar.contact_management_api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import rizalamar.contact_management_api.entities.Address;
import rizalamar.contact_management_api.entities.Contact;
import rizalamar.contact_management_api.models.address.AddressResponse;
import rizalamar.contact_management_api.models.address.CreateAddressRequest;
import rizalamar.contact_management_api.models.address.UpdateAddressRequest;
import rizalamar.contact_management_api.models.contact.ContactResponse;
import rizalamar.contact_management_api.repositories.AddressRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final ValidationService validationService;
    private final AddressRepository addressRepository;

    public AddressResponse create (Contact contact, CreateAddressRequest request) {
        validationService.validate(request);

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setContact(contact);
        addressRepository.save(address);

        return toAddressResponse(address);
    }

    @Transactional(readOnly = true)
    public AddressResponse get(Contact contact, String addressId) {
        Address address = addressRepository.findFirstByContactAndId(contact, addressId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        return  toAddressResponse(address);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> list(Contact contact) {
        List<Address> addresses = addressRepository.findAllByContact(contact);
        return addresses.stream().map(this::toAddressResponse).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Contact contact, String id) {
        Address address = addressRepository.findFirstByContactAndId(contact, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        addressRepository.delete(address);
    }

    @Transactional
    public AddressResponse update (Contact contact, UpdateAddressRequest request) {
        validationService.validate(request);

        Address address = addressRepository.findFirstByContactAndId(contact, request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        if(Objects.nonNull(request.getStreet())){
            address.setStreet(request.getStreet());
        }

        if(Objects.nonNull(request.getCity())){
            address.setCity(request.getCity());
        }

        if(Objects.nonNull(request.getProvince())){
            address.setProvince(request.getProvince());
        }

        if(Objects.nonNull(request.getCountry())){
            address.setCountry(request.getCountry());
        }

        if(Objects.nonNull(request.getPostalCode())){
            address.setPostalCode(request.getPostalCode());
        }

        addressRepository.save(address);

        return toAddressResponse(address);
    }

    public AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .province(address.getProvince())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }
}
