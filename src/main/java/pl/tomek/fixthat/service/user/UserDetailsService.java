package pl.tomek.fixthat.service.user;

import pl.tomek.fixthat.dto.user.UserDetailsDto;


public interface UserDetailsService {
    UserDetailsDto getDetailsForUser(String email);
    void saveDetails(UserDetailsDto userDetailsDto);
}
