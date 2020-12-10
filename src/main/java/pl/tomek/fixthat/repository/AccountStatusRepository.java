package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.AccountStatus;
import pl.tomek.fixthat.entity.User;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatus, Long> {

    @Query("SELECT a FROM AccountStatus a JOIN a.user u WHERE u = :user")
    AccountStatus findByUser(@Param("user") User user);

    boolean existsByUser(User user);
}
