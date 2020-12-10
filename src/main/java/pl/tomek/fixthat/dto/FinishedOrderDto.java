package pl.tomek.fixthat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinishedOrderDto {
    private Long id;
    private String name;
    private String device;
    private String description;
    private LocalDateTime postDate;
    private LocalDateTime fixDate;
    private String fixer;
}
