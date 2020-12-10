package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.Order;
import pl.tomek.fixthat.entity.Proposition;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Long> {

    List<Proposition> findAllByOrder_Id(Long id);

    Optional<Proposition> getFirstByOrderIdAndUserEmail(Long id, String email);
    List<Proposition> findAllByUserUsernameAndActiveTrue(String username);

    Proposition getFirstByOrder(Order order);
    List<Proposition> getFirstByUserUsernameAndActiveFalse(String username);

}