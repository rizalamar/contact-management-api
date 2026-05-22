package rizalamar.contact_management_api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.RegisterUserRequest;
import rizalamar.contact_management_api.models.WebResponse;
import rizalamar.contact_management_api.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterUser() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Test");
        request.setUsername("test");
        request.setPassword("rahasiaa");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
//
//                    assertEquals("OK", response.getData());
//                });
                .andExpectAll(jsonPath("$.data").value("OK"));
    }

    @Test
    void testRegisterUserBadRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("");
        request.setUsername("");
        request.setPassword("");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/api/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpectAll(status().isBadRequest())
//                .andDo(result -> {
//                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
//
//                    assertEquals(response.getErrors());
//                });
                .andExpectAll(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void testRegisterUserDuplicate() throws Exception {
        User user = new User();
        user.setName("Test");
        user.setUsername("test");
        user.setPassword("rahasiaya");
        userRepository.save(user);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Test");
        request.setUsername("test");
        request.setPassword("rahasiaya");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/api/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpectAll(status().isBadRequest())
//                .andDo(result -> {
//                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
//
//                    assertEquals(response.getErrors());
//                });
                .andExpectAll(jsonPath("$.errors").value("Username already registered"));
    }
}