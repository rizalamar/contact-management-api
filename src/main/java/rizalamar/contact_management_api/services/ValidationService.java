package rizalamar.contact_management_api.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rizalamar.contact_management_api.models.RegisterUserRequest;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final Validator validator;

    public void validate(Object request){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        System.out.println("constraintViolations: " + constraintViolations);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
