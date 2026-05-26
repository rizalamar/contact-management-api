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
import rizalamar.contact_management_api.models.contact.CreateContactRequest;
import rizalamar.contact_management_api.repositories.ContactRepository;
import rizalamar.contact_management_api.repositories.UserRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setPassword("rahasiaya");
        user.setName("Test User");
        user.setToken("test-token");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
        userRepository.save(user);
    }

    @Test
    void createContactSuccess() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Rizal");
        request.setLastName("Amarulloh");
        request.setEmail("rizal@mail.com");
        request.setPhone("08123456789");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test-token")
                        .content(requestJson)
        )
                .andExpectAll(status().isOk())
                .andExpectAll(jsonPath("$.data.id").isString())
                .andExpectAll(jsonPath("$.data.firstName").value("Rizal"))
                .andExpectAll(jsonPath("$.data.lastName").value("Amarulloh"))
                .andExpectAll(jsonPath("$.data.email").value("rizal@mail.com"))
                .andExpectAll(jsonPath("$.data.phone").value("08123456789"));
    }

    @Test
    void createContactBadRequest() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah-email");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test-token")
                        .content(requestJson)
        )
                .andExpectAll(status().isBadRequest())
                .andExpectAll(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void getContactSuccess() throws Exception {
        User user = userRepository.findById("test").orElseThrow();
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName("Rizal");
        contact.setLastName("Amarulloh");
        contact.setEmail("rizal@mail.com");
        contact.setPhone("08123456789");
        contact.setUser(user);
        contactRepository.save(contact);

        mockMvc.perform(
                get("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test-token")
        )
                .andExpectAll(status().isOk())
                .andExpectAll(jsonPath("$.data.id").value(contact.getId()))
                .andExpectAll(jsonPath("$.data.firstName").value(contact.getFirstName()))
                .andExpectAll(jsonPath("$.data.lastName").value(contact.getLastName()))
                .andExpectAll(jsonPath("$.data.email").value(contact.getEmail()))
                .andExpectAll(jsonPath("$.data.phone").value(contact.getPhone()));
    }

    @Test
    void getContactNotFound() throws Exception {
        mockMvc.perform(
                get("/api/contacts/salah")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test-token")
        )
                .andExpectAll(status().isNotFound())
                .andExpectAll(jsonPath("$.errors").exists());
    }
}