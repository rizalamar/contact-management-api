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
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
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



    @Test
    void testGetUserSuccess() throws Exception {
        // - mensimulasikan ada data user di database yang SUDAH LOGIN (punya token)
        User user = new User();
        user.setName("Test");
        user.setUsername("test");
        user.setPassword("rahasia");
        user.setToken("test-token");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000000L);
        userRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test-token") // <-- mengirim token
        )
                .andExpectAll(status().isOk())
                .andExpectAll(jsonPath("$.data.username").isNotEmpty())
                .andExpectAll(jsonPath("$.data.name").isNotEmpty())
                .andExpectAll(jsonPath("$.errors").isEmpty());


    }

    @Test
    void testGetUserTokenNotFound() throws  Exception{
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "token-salah")
        )
                .andExpectAll(status().isUnauthorized())
                .andExpectAll(jsonPath("$.errors").value("Unauthorized"));
    }

    @Test
    void testGetUserTokenExpired() throws Exception{
        User user = new User();
        user.setName("Test");
        user.setUsername("test");
        user.setPassword("rahasia");
        user.setToken("test-basi");
        user.setTokenExpiredAt(System.currentTimeMillis() - 1000); // <-- lewat 1 detik
        userRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "token-basi")
        )
                .andExpectAll(status().isUnauthorized())
                .andExpectAll(jsonPath("$.errors").isNotEmpty());
    }

}