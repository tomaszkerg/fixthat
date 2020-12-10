package pl.tomek.fixthat.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "company")
    private List<Device> devices;
}
