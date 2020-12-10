package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.user.User;
import pl.tomek.fixthat.entity.user.UserDetails;
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findFirstByUser(User user);
    UserDetails findFirstByUserEmail(String email);
    UserDetails findFirstByUserUsername(String username);
    boolean existsByUserEmail(String email);
    boolean existsByUserUsername(String username);
    boolean existsByUser(User user);
}
