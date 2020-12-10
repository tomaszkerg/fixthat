package pl.tomek.fixthat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.Order;
import pl.tomek.fixthat.entity.user.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserUsernameAndActiveFalse(String username);
    List<Order> findAllByUserUsernameAndActiveTrue(String username);
    @Query("SELECT o FROM Order o")
    List<Order> findAllOrders(Pageable page);
    Page<Order> findAllByActiveTrue(Pageable page);
}
