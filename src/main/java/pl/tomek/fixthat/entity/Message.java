package pl.tomek.fixthat.entity;

import lombok.*;
import pl.tomek.fixthat.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "messages")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message implements EntityModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String context;
    private LocalDateTime sendDate;
    @ManyToOne
    private User receiver;
}
