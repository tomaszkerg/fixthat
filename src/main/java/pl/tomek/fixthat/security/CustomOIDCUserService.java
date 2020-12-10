package pl.tomek.fixthat.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import pl.tomek.fixthat.entity.user.User;
import pl.tomek.fixthat.entity.user.UserDetails;
import pl.tomek.fixthat.entity.user.UserRole;
import pl.tomek.fixthat.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CustomOIDCUserService extends OidcUserService {


    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("Logowanie Open ID Connect");
        OidcUser oidcUser = super.loadUser(userRequest);
        log.debug("OidcUser: {}", oidcUser);
        User user = userRepository.findByUsername(oidcUser.getName())
                .orElseGet(() -> {
                    log.debug("Tworzenie nowego użytkownika w bazie na podstawie użytkownika OIDC");
                    User dbUser = userRepository.save(User.builder()
                            .username(oidcUser.getName())
                            .email(oidcUser.getEmail())
                            .userDetails(UserDetails.builder()
                                    .firstName(oidcUser.getGivenName())
                                    .lastName(oidcUser.getFamilyName())
                                    .build())
                            .enabled(true)
                            .password(userRequest.getAccessToken().getTokenValue())
                            .roles(Set.of(UserRole.builder().roleName("ROLE_USER").build()))
                            .build());
                    dbUser.getUserDetails().setUser(dbUser);
                    log.debug("Utworzony użytkownik w bazie: {}", dbUser);
                    return dbUser;
                });
        log.debug("Zwracam instancję zalogowanego użytkownika w oparciu o użytkownika z bazy {} i użytkownika z logowanie OIDC: {}",
                user, oidcUser);
        return LoggedUser.builder()
                .username(user.getUsername())
                .active(user.getEnabled())
                .password(user.getPassword())
                .attributes(oidcUser.getAttributes())
                .oidcUserInfo(oidcUser.getUserInfo())
                .oidcIdToken(oidcUser.getIdToken())
                .claims(oidcUser.getClaims())
                .authorities(user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
