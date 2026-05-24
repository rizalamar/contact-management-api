package rizalamar.contact_management_api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rizalamar.contact_management_api.entities.User;
import rizalamar.contact_management_api.models.LoginUserRequest;
import rizalamar.contact_management_api.models.TokenResponse;
import rizalamar.contact_management_api.models.WebResponse;
import rizalamar.contact_management_api.repositories.UserRepository;
import rizalamar.contact_management_api.utils.PasswordUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ValidationService validationService;

    public TokenResponse login (LoginUserRequest request){
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or password"));

        boolean checkedPassword = PasswordUtil.checkPassword(request.getPassword(), user.getPassword());

        if(checkedPassword){
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30Days());
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or password");
        }
    }

    public void logout (User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    public Long next30Days(){
        return System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30);
    }
}
