package pl.tomek.fixthat.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements EntityModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    private String password;

    @Size(min = 9,max = 9)
    @NumberFormat
    private String phoneNumber;


    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    private AccountStatus accountStatus;

    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    private UserDetails userDetails;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Order> orders;

}
