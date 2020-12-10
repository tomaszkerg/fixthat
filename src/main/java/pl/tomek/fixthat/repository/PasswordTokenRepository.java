package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.user.PasswordResetToken;
@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken,Long> {
}
