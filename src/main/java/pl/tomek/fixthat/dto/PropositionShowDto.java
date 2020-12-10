package pl.tomek.fixthat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PropositionShowDto {
    private Long id;
    private Double price;
    private LocalDateTime finishTime;
    private LocalDateTime propositionTime;
    private Long orderId;
    private String username;
}
