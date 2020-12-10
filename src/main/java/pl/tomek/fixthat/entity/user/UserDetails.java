package pl.tomek.fixthat.entity.user;

import lombok.*;
import pl.tomek.fixthat.entity.EntityModel;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "details")
@Builder
@ToString(exclude = "user") @NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "pesel")
    private String pesel;
    private String city;

    private String address;


    private boolean newsLetter;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @OneToOne
    private User user;



}
