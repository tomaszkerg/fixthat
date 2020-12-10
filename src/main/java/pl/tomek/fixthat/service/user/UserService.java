package pl.tomek.fixthat.service.user;

import pl.tomek.fixthat.dto.user.UserDto;
import pl.tomek.fixthat.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();
    UserDto save(UserDto userDto);
    Optional<User> getById(Long id);
    boolean deleteById(Long id);
    UserDto mapAndSaveUser(UserDto userDto);

    void checkUsernameDuplicate(UserDto userDto);
    void checkEmailDuplicate(UserDto userDto);
    User getUserByEmail(String email);
    UserDto getUserInfo(String email);

    void createDetailsForUser(User user);



    void updateEmail(String email);
    void updatePassword(String password);

}
