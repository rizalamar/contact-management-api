package rizalamar.contact_management_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.user.RegisterUserRequest;
import rizalamar.contact_management_api.models.user.UpdateUserRequest;
import rizalamar.contact_management_api.models.user.UserResponse;
import rizalamar.contact_management_api.models.WebResponse;
import rizalamar.contact_management_api.services.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public WebResponse<String> register (@RequestBody RegisterUserRequest request){
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping("/current")
    public WebResponse<UserResponse> get(User user) {
        UserResponse userResponse = userService.get(user);
        return  WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @PatchMapping("/current")
    public WebResponse<UserResponse> update(User user,@RequestBody UpdateUserRequest request){
        UserResponse updateResponse = userService.update(user, request);
        return WebResponse.<UserResponse>builder().data(updateResponse).build();
    }
}
