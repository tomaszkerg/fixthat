package pl.tomek.fixthat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderShowAllDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime postTime;
    private String device;
}
