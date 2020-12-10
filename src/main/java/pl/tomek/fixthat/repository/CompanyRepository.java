package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
