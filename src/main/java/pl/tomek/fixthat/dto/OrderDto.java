package pl.tomek.fixthat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto {
    private Long id;
    @NotNull @Size(max = 20)
    private String name;
    @NotNull
    private String description;
    private LocalDateTime postTime;
    private Long deviceId;
}
