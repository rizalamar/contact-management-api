package rizalamar.contact_management_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.LoginUserRequest;
import rizalamar.contact_management_api.models.TokenResponse;
import rizalamar.contact_management_api.models.WebResponse;
import rizalamar.contact_management_api.repositories.UserRepository;
import rizalamar.contact_management_api.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping("/logout")
    public WebResponse<String> logout(User user) {
        authService.logout(user);
        return WebResponse.<String>builder().data("OK").build();
    }
}
