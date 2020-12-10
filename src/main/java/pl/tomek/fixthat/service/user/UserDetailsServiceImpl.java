package pl.tomek.fixthat.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.fixthat.dto.user.UserDetailsDto;
import pl.tomek.fixthat.entity.user.UserDetails;
import pl.tomek.fixthat.mapper.UserDetailsMapper;
import pl.tomek.fixthat.repository.UserDetailsRepository;
import pl.tomek.fixthat.repository.UserRepository;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository,
                                  UserRepository userRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsDto getDetailsForUser(String username) {
        return UserDetailsMapper.toDto(userDetailsRepository.findFirstByUserUsername(username));
    }

    @Override
    public void saveDetails(UserDetailsDto userDetailsDto) {
        String username = ContextService.getUsername();
        log.info("edycja szczegółów dla uzytkownika {}",username);
        UserDetails details = UserDetailsMapper.toEntity(userDetailsDto);
        details.setUser(userRepository.getByUsername(username));
        userDetailsRepository.save(details);
    }


}
