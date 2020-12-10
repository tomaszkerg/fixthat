package pl.tomek.fixthat.entity;

import lombok.Getter;
import lombok.Setter;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "account_status")
public class AccountStatus implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private boolean active;

    @OneToOne
    private User user;
}
