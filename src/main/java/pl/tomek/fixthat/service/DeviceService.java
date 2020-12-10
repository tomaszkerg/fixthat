package pl.tomek.fixthat.service;

import pl.tomek.fixthat.dto.DeviceDto;
import pl.tomek.fixthat.entity.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {

    List<DeviceDto> findAllDevices();
    DeviceDto saveDevice(DeviceDto deviceDto);
    Optional<Device> getDeviceById(Long id);
    boolean deleteDeviceById(Long id);
}
