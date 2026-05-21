package rizalamar.contact_management_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(nullable = false, length = 15)
    private String username;

    @Column(nullable = false, length = 15)
    private String password;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(length = 15, unique = true)
    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;
}
