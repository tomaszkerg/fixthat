package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tomek.fixthat.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findAllByReceiverUsername(String username);
}
