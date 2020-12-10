package pl.tomek.fixthat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "devices")
public class Device implements EntityModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String model;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Company company;



}
