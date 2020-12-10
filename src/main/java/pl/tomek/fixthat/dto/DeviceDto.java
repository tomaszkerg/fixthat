package pl.tomek.fixthat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDto {

    private Long id;
    private String model;
    private String category;
    private String company;
}
