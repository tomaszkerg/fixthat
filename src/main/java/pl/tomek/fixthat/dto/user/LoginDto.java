package pl.tomek.fixthat.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private Long id;
    private String email;
    private String password;
}
