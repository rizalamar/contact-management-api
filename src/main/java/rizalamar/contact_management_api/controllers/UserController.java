package rizalamar.contact_management_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.RegisterUserRequest;
import rizalamar.contact_management_api.models.UserResponse;
import rizalamar.contact_management_api.models.WebResponse;
import rizalamar.contact_management_api.repositories.UserRepository;
import rizalamar.contact_management_api.services.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    public WebResponse<String> register (@RequestBody RegisterUserRequest request){
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping("/current")
    public WebResponse<UserResponse> get(@RequestHeader(name = "X-API-TOKEN") String token) {
        User user = userRepository.findFirstByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        long expiredBase = System.currentTimeMillis();
        long userExpired = user.getTokenExpiredAt();
        if (expiredBase > userExpired) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");
        }

        UserResponse userResponse = userService.get(user);
        return  WebResponse.<UserResponse>builder().data(userResponse).build();

    }
}
