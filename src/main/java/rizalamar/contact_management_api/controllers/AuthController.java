package rizalamar.contact_management_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rizalamar.contact_management_api.models.LoginUserRequest;
import rizalamar.contact_management_api.models.TokenResponse;
import rizalamar.contact_management_api.models.WebResponse;
import rizalamar.contact_management_api.services.AuthService;

@RestController
@RequestMapping("/api/auth/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }
}
