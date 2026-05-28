package rizalamar.contact_management_api.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rizalamar.contact_management_api.models.contact.ContactResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String username;

    private String name;
    private List<ContactResponse> contacts;
}
