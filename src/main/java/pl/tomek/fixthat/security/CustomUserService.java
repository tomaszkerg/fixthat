package pl.tomek.fixthat.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tomek.fixthat.exception.UserNotFoundException;
import pl.tomek.fixthat.repository.UserRepository;
import pl.tomek.fixthat.security.LoggedUser;

import java.util.Collections;
import java.util.stream.Collectors;


@Slf4j
public class CustomUserService implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("searching for user by username '{}'", username);
        return userRepository.findByUsername(username)
                .map(user -> LoggedUser.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .active(user.getEnabled())
                        .authorities(user.getRoles()
                                .stream()
                                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                                .collect(Collectors.toSet()))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika: " + username));
    }
}
