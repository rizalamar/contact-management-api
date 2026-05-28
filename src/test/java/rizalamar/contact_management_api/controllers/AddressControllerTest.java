package rizalamar.contact_management_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rizalamar.contact_management_api.entities.Contact;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.address.CreateAddressRequest;
import rizalamar.contact_management_api.repositories.AddressRepository;
import rizalamar.contact_management_api.repositories.ContactRepository;
import rizalamar.contact_management_api.repositories.UserRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setPassword("rahasiaya");
        user.setName("Test User");
        user.setToken("test-token");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("id-kontak-1");
        contact.setFirstName("Kontak");
        contact.setLastName("1");
        contact.setEmail("kontak@mail.com");
        contact.setPhone("081234567823");
        contact.setUser(user);
        contactRepository.save(contact);
    }

    @Test
    void createAddressSuccess() throws Exception {
        User user = userRepository.findById("test").orElseThrow();
        Contact contact = contactRepository.findFirstByUserAndId(user, "id-kontak-1").orElseThrow();
        CreateAddressRequest request = new CreateAddressRequest();
        request.setContactId(contact.getId());
        request.setStreet("jalani aja");
        request.setCity("malang");
        request.setProvince("jatim");
        request.setCountry("indonesia");
        request.setPostalCode("1234");

        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                post("/api/contacts/" + contact.getId() + "/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test-token")
                        .content(requestJson)
        )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.length()").isNotEmpty(),
                        jsonPath("$.data.id").isString(),
                        jsonPath("$.data.street").value("jalani aja"),
                        jsonPath("$.data.city").value("malang"),
                        jsonPath("$.data.province").value("jatim"),
                        jsonPath("$.data.country").value("indonesia"),
                        jsonPath("$.data.postalCode").value("1234")
                );
    }
}