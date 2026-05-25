package rizalamar.contact_management_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.user.LoginUserRequest;
import rizalamar.contact_management_api.repositories.UserRepository;
import rizalamar.contact_management_api.utils.PasswordUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
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
    void loginFailedUserNotFound() throws Exception {

        LoginUserRequest request = new LoginUserRequest();
        request.setUsername("test");
        request.setPassword("test");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpectAll(status().isUnauthorized())
                .andExpectAll(jsonPath("$.errors").value("Wrong username or password"));
    }

    @Test
    void loginFailedWrongPassword() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(PasswordUtil.hashPassword("amar123"));
        user.setName("test");
        userRepository.save(user);

        LoginUserRequest request = new LoginUserRequest();
        request.setUsername("test");
        request.setPassword("test");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/api/auth/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpectAll(status().isUnauthorized())
                .andExpectAll(jsonPath("$.errors").value("Wrong username or password"));
    }

    @Test
    void loginFailedBadRequest() throws Exception {
        LoginUserRequest request = new LoginUserRequest();
        request.setUsername("");
        request.setPassword("");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/api/auth/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpectAll(status().isBadRequest())
                .andExpectAll(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void loginSuccess() throws Exception {
        User user = new User();
        user.setName("amar");
        user.setUsername("amar123");
        user.setPassword(PasswordUtil.hashPassword("rahasiaku"));
        userRepository.save(user);

        LoginUserRequest request = new LoginUserRequest();
        request.setUsername("amar123");
        request.setPassword("rahasiaku");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/api/auth/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpectAll(jsonPath("$.data.token").isNotEmpty())
                .andExpectAll(jsonPath("$.data.expiredAt").isNotEmpty());
    }

}