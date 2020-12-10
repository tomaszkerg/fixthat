package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);


    @Query(value = "SELECT password FROM users WHERE username = :username",nativeQuery = true)
    String findPasswordForUser(@Param("username")String username);




    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    User getByEmail(String email);
    User getByUsername(String username);
}
