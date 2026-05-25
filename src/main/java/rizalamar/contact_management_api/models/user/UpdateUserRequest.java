package rizalamar.contact_management_api.models.user;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(max = 100)
    private String name;

    @Size(min = 8, max = 100, message = "Password must between in 8 and 100 characters")
    private String password;
}
