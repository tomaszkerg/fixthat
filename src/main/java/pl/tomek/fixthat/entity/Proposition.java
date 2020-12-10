package pl.tomek.fixthat.entity;

import lombok.Getter;
import lombok.Setter;
import pl.tomek.fixthat.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Proposition implements EntityModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime finishTime;

    private Double price;

    private boolean active;

    @OneToOne
    private User user;

    @ManyToOne
    private Order order;

    private LocalDateTime propositionTime;
}
