package pl.tomek.fixthat.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tomek.fixthat.dto.user.UserDto;


import pl.tomek.fixthat.entity.user.User;
import pl.tomek.fixthat.entity.user.UserDetails;
import pl.tomek.fixthat.entity.user.UserRole;
import pl.tomek.fixthat.exception.DuplicateEmailException;
import pl.tomek.fixthat.exception.DuplicateUsernameException;
import pl.tomek.fixthat.mapper.UserMapper;

import pl.tomek.fixthat.repository.UserDetailsRepository;
import pl.tomek.fixthat.repository.UserRepository;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private java.util.Set<UserRole> Set;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserDetailsRepository userDetailsRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto save(UserDto userDto) {
        return mapAndSaveUser(userDto);
//        if(user.getId() != null){
//            User userFromDb = getById(user.getId())
//                    .orElseThrow(() -> {
//                        throw new UserNotFoundException();
//                    });
//            if(user.getPassword()==null) user.setPassword(userFromDb.getPassword());
//
//        }
//        userRepository.save(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Override
    public UserDto mapAndSaveUser(UserDto userDto) {
        User userEntity = UserMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setAuthProvider("OwnDB");
        userEntity.setEnabled(true);
        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole("ROLE_USER"));
        userEntity.setRoles(roles);
        User savedUser = userRepository.save(userEntity);
        log.info("tworzenie uzytkownika o nazwie {} i id {}",savedUser.getEmail(),savedUser.getId());
        if(!userDetailsRepository.existsByUser(savedUser)) createDetailsForUser(savedUser);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public void createDetailsForUser(User user){
        UserDetails userDetails = new UserDetails();
        userDetails.setNewsLetter(false);
        userDetails.setUser(user);
        log.info("tworzenie szczegółów dla uzytkownika o id {}",user.getId());
        userDetailsRepository.save(userDetails);
    }


    @Override
    public void checkUsernameDuplicate(UserDto userDto) {
        Optional<User> userById = userRepository.findByUsername(userDto.getUsername());
        userById.ifPresent(u -> {
            throw new DuplicateUsernameException();
        });
    }

    @Override
    public void checkEmailDuplicate(UserDto userDto) {
        Optional<User> userById = userRepository.findByEmail(userDto.getEmail());
        userById.ifPresent(u -> {
            throw new DuplicateEmailException();
        });
    }



    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public UserDto getUserInfo(String username) {
        return UserMapper.toDto(userRepository.getByUsername(username));
    }

    @Override
    public void updateEmail(String emailNew) {
        String emailOld = ContextService.getUsername();
        User user = userRepository.getByEmail(emailOld);
        log.info("zmiana emaila dla użytkownika o id {} na {}",user.getId(),emailNew);
        user.setEmail(emailNew);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String password) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        log.info("zmiana hasła dla użytkownika o id {}",user.getId());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }






}
