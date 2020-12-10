package pl.tomek.fixthat.entity;

import lombok.Getter;
import lombok.Setter;
import pl.tomek.fixthat.entity.user.User;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String name;

    private LocalDateTime postTime;

    private String description;

    private boolean active;

    @OneToOne
    private Device device;

    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE)
    private List<Proposition> propositions;
}
