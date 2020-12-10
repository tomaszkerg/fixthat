package pl.tomek.fixthat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomek.fixthat.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
