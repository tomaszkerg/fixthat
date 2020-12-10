package pl.tomek.fixthat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageListDto {
    private Long id;
    private String title;
    private LocalDateTime sendDate;
}
