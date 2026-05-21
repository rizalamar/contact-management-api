package rizalamar.contact_management_api.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.exception.ApiException;
import rizalamar.contact_management_api.models.RegisterUserRequest;
import rizalamar.contact_management_api.repositories.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private Validator validator;

    public void create (RegisterUserRequest request){
        Set<ConstraintViolation<RegisterUserRequest>> constraintViolations = validator.validate(request);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        if(userRepository.existsById(request.getUsername())){
            throw new ApiException("Username already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .build();

        userRepository.save(user);
    }

}
