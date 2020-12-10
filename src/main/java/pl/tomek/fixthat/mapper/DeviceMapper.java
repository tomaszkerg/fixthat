package pl.tomek.fixthat.mapper;

import pl.tomek.fixthat.dto.DeviceDto;
import pl.tomek.fixthat.entity.Device;

public class DeviceMapper {

    public static DeviceDto toDto(Device device) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setModel(device.getModel());
        deviceDto.setCategory(device.getCategory().getName());
        deviceDto.setCompany(device.getCompany().getName());
        return deviceDto;
    }
}
