package pl.tomek.fixthat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.image.BufferedImage;

@Getter
@Setter
@Entity
@Table(name = "details")
public class UserDetails implements EntityModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String city;

    private String address;


    private boolean newsLetter;

    @OneToOne
    private User user;

}
