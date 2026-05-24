package rizalamar.contact_management_api.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.RegisterUserRequest;
import rizalamar.contact_management_api.models.UserResponse;
import rizalamar.contact_management_api.repositories.UserRepository;
import rizalamar.contact_management_api.utils.PasswordUtil;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;

    public void register (RegisterUserRequest request){
        validationService.validate(request);

        if(userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(PasswordUtil.hashPassword(request.getPassword()))
                .name(request.getName())
                .build();

        userRepository.save(user);
    }

    public UserResponse get(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }
}
