package rizalamar.contact_management_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @Column(nullable = false, length = 100)
    private String id;

    @Column(length = 100)
    private String street;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String province;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(name = "postal_code", length = 100)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;
}
