package pl.tomek.fixthat.entity.user;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import pl.tomek.fixthat.entity.EntityModel;
import pl.tomek.fixthat.entity.Order;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(min = 5,max = 20)
    private String username;

    @Email
    private String email;

    private String password;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "auth_provider")
    private String authProvider;

    private Boolean enabled = Boolean.FALSE;



    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private UserDetails userDetails;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User that = (User) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", enabled=" + enabled +
                "} " + super.toString();
    }

}
