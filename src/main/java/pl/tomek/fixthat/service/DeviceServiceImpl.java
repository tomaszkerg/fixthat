package pl.tomek.fixthat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.fixthat.dto.DeviceDto;
import pl.tomek.fixthat.entity.Device;
import pl.tomek.fixthat.mapper.DeviceMapper;
import pl.tomek.fixthat.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<DeviceDto> findAllDevices() {
        return deviceRepository.findAll()
                .stream().map(DeviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDto saveDevice(DeviceDto deviceDto) {
        return null;
    }

    @Override
    public Optional<Device> getDeviceById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteDeviceById(Long id) {
        return false;
    }
}
