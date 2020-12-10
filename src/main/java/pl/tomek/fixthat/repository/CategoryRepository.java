package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
