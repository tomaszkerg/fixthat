package pl.tomek.fixthat.dto.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private boolean newsLetter;
}
